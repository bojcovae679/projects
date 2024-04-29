#define _CRT_SECURE_NO_WARNINGS
#include <stdio.h>
#include <math.h>
#include <omp.h>

#include <string>
#include <iostream>

using namespace std;

struct Point{
    double x = 0, y = 0, z = 0;

    Point(double x, double y, double z) {
        this->x = x, this->y = y, this->z = z;
    }

    Point() {}

    Point operator-(Point&other) {
        return Point(this->x - other.x, this->y - other.y, this->z - other.z);
    }

    double getSquare() {
        return (x * x + y * y + z * z);
    }
};

#define Vector Point

double calcSide(Point&a, Point&b, Point&c) {
    Vector x = b - a, y = c - b;
    return sqrt(min(x.getSquare(), y.getSquare()));
}

struct xorshift {
    uint64_t a, b, c;
    xorshift() {};
    xorshift(uint64_t x, uint64_t y, uint64_t z) {
        this->a = x;
        this->b = y;
        this->c = z;
    }

    void gen(uint64_t&x) {
        x ^= x << 13;
        x ^= x >> 7;
        x ^= x << 17;
        return;
    }

    uint64_t nextshift() {
        gen(this->a);
        gen(this->b);
        gen(this->c);
        return this->a;
    }
};

double mathCalc(double x) {
    return (x * x * x * sqrt(2) / 3);
}

uint64_t base[16][3] = {{2220645850ull, 8190356440509831028ull, 1832306954090ull}, {46848890ull, 14691269094174522886ull, 6881473239004664801ull}, {3562593617ull, 16897209630451326700ull, 15348175947879600204ull}, {588566162ull, 9693974754748146778ull, 14293154083068647595ull},
        {1602013948ull, 14057026321528216136ull, 7422914573888193423ull}, {357584873ull, 8227234410611067953ull, 13202032629919845481ull}, {2192345933ull, 18143907752927971548ull, 5602025219665966390ull}, {3111924868ull, 18286630423193595545ull, 488853514011058459ull},
        {2304817086ull, 14704192974840341622ull, 17317372449008479725ull}, {366008592ull, 80231163216804259ull, 14574575917088790865ull}, {1016112799ull, 11786217706178019931ull, 3664868227079158946ull}, {3213970500ull, 16370984365348481810ull, 16966529748731150354ull},
        {3677873861ull, 3502830170848269091ull, 11529689012093452252ull}, {458305198ull, 8316913250345020280ull, 17718973999510231112ull}, {2480203765ull, 14378513831206741117ull, 14141742563561826163ull}, {3354114759ull, 1607218074356813276ull, 17205058752084972410ull}};

int withOMP(int thread_count, int n) {
    int sum = 0;

    #pragma omp parallel num_threads(thread_count)
    {
        int num = omp_get_thread_num();

        xorshift gen = xorshift(base[num][0], base[num][1], base[num][2]);

        int count = 0;

        #pragma omp for schedule(static)
        for (int i = 0; i < n; i++) {
            gen.nextshift();
            double x = gen.a / (double) 0xFFFFFFFFFFFFFFFF;
            double y = gen.b / (double) 0xFFFFFFFFFFFFFFFF;
            double z = gen.c / (double) 0xFFFFFFFFFFFFFFFF;
            count += (x + y + z <= 1.0);
        }

        #pragma omp atomic
        sum += count;
    }

    return sum;
}

int withoutOMP(int n) {
    int sum = 0;
    xorshift gen = xorshift(base[0][0], base[0][1], base[0][2]);

    for (int i = 0; i < n; i++) {
        gen.nextshift();
        double x = gen.a / (double) 0xFFFFFFFFFFFFFFFF;
        double y = gen.b / (double) 0xFFFFFFFFFFFFFFFF;
        double z = gen.c / (double) 0xFFFFFFFFFFFFFFFF;
        sum += (x + y + z <= 1.0);
    }

    return sum;
}

int main(int argc, char *argv[])
{
    if (argc != 4) {
        cerr << "Not enough arguments";
        cerr << "expected: " << 4 << " received: " << argc << '\n';
        exit(-1);
    }

    int thread_count = stoi(argv[1]);
    if (thread_count == 0) thread_count = omp_get_num_procs();
    if (thread_count == -1) thread_count = -1;

    int n;
    Point p1, p2, p3;

    try {
        FILE *fin = fopen(argv[2], "rb");
        fscanf(fin, "%i\n(%lf %lf %lf)\n(%lf %lf %lf)\n(%lf %lf %lf)", &n, &p1.x, &p1.y, &p1.z, &p2.x, &p2.y, &p2.z, &p3.x, &p3.y, &p3.z);
        fclose(fin);
    } catch(std::exception&e) {
        cerr << "Сan not open the read-file. The format may not be supported.";
        exit(2);
    }

    double side = calcSide(p1, p2, p3);


    double tstart = omp_get_wtime();

    int sum = (thread_count == -1 ? withoutOMP(n) : withOMP(thread_count, n));

    double tend = omp_get_wtime();

    try {
        FILE *fout = fopen(argv[3], "wb");
        double res = side * side * side * sum / n * 8 / (2 * sqrt(2));
        fprintf(fout, "%lf %lf\n", mathCalc(side), res);
        fclose(fout);
    } catch(std::exception&e) {
        cerr << "Сan not open the write-file. The format may not be supported.";
        exit(2);
    }

    printf("Time (%d thread(s)): %dms", thread_count, (int) ((tend - tstart) * 1000));

    return 0;
}