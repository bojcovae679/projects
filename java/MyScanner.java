import java.io.IOException;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
public class MyScanner {
    public InputStreamReader reader;
    private char[] buffer = new char[1024];
    int read = 0;
    int pointerNext = 0;
    int i = 0;
    String linesep = System.lineSeparator();
    int flag = 0;

    MyScanner(InputStream stream)  {
        reader = new InputStreamReader(stream, StandardCharsets.UTF_8);
    }
    MyScanner(String s)  {
        try{reader = new InputStreamReader(new ByteArrayInputStream(s.getBytes(StandardCharsets.UTF_8)), "utf-8");
        } catch (IOException e) {
            System.err.println("mistake during reading the string" + e.getMessage());
            System.exit(-1);
        }
    }

    public boolean isMeaningCharacter(char c){
        if (flag == 1){
            return (Character.isLetter(c) || Character.getType(c) == Character.DASH_PUNCTUATION || c == '\'');
        } 
        if (flag == 2){
            return (Character.isDigit(c) || c == '-');
        }
        return !Character.isWhitespace(c);
    }
    public char[] biggerSize(char[] arr){
        arr = Arrays.copyOf(arr, arr.length * 2);
        return arr;
    }
    public char[] smallerSize(char[] arr){
        int shift = Math.min(pointerNext, i);
        for (int j = shift; j < read; j++){
            arr[j - shift] = arr[j];
        }
        i = i - shift;
        pointerNext = pointerNext - shift;
        read = read - shift;
        return arr;
    }
    public void reading(){
        if (i == buffer.length) buffer = biggerSize(buffer);
        if (i >= buffer.length/2 && pointerNext >= buffer.length/2) buffer = smallerSize(buffer);
        if (i == read) {
            try {
                read = reader.read(buffer, i, buffer.length - i) + read;
            } catch (IOException e) {
                System.err.println("mistake in reader" + e.getMessage());
                System.exit(-1);
            }  
        }
    }
   
    public boolean hasNext() {
        i = pointerNext;
        reading();
        while (!isMeaningCharacter(buffer[i]) && read != -1 && i < read) {
            i = i + 1;
            reading();
        }
        return (read != -1 && i < read);
    }

    public boolean hasNextWord(){
        flag = 1;
        return hasNext();
    }

    public boolean hasNextInt(){
        flag = 2;
        return hasNext();
    }

    public String next(){
        if (!hasNext()){
            System.err.println("there is no next string");
            System.exit(-1);
        }
        while(!isMeaningCharacter(buffer[pointerNext])){
            pointerNext = pointerNext + 1;
        }
        StringBuilder word = new StringBuilder();
        while(hasNext() && isMeaningCharacter(buffer[pointerNext])){
            word.append(buffer[pointerNext]);
            pointerNext = pointerNext + 1;
        }
        return word.toString();
    }

    public String nextWord(){
        flag = 1;
        return next();
    }

    public int nextInt(){
        flag = 2;
        return Integer.parseInt(next());
    }

    public boolean hasNextLine() {
        i = pointerNext - 1;
        int t = 0;
        StringBuilder word = new StringBuilder();
        while (t == 0 && read != -1 && i < read) {
            i = i + 1;
            reading();
            if(Character.isWhitespace(buffer[i])){
                word.append(buffer[i]);
                if (word.toString().contains(linesep)){
                    t = 1;
                }
            } else{
                word.setLength(0);
            }
        }
        return ((t == 1 && read != -1 && i < read));  
    }

    public String nextLine() {
        if (!hasNextLine()) {
            System.err.println("there is no next line");
            System.exit(-1);
        }
        StringBuilder line = new StringBuilder();
        i = pointerNext - 1;
        int t = 0;
        StringBuilder word = new StringBuilder();
        while (t == 0 && read != -1 && i < read) {
            i = i + 1;
            if (Character.isWhitespace(buffer[i])) {
                word.append(buffer[i]);
                if (word.toString().contains(linesep)) {
                    t = 1;
                    pointerNext = i + 1;
                } else {
                    line.append(buffer[i]);
                }
            } else {
                word.setLength(0);
                line.append(buffer[i]);
            }
        }
        return line.toString();
    }
}