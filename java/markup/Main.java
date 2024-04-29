package markup;
import static java.util.List.*;
public class Main {
    public static void main(String[] args) {
        Paragraph paragraph = new Paragraph(of(
                new Strong(of(
                        new Text("1"),
                        new Strikeout(of(
                                new Text("2"),
                                new Emphasis(of(
                                        new Text("3"),
                                        new Text("4")
                                )),
                                new Text("5")
                        )),
                        new Text("6")
                ))
        ));

        StringBuilder sb = new StringBuilder();
        paragraph.toMarkdown(sb);
        System.out.println(sb.toString());
    }

}
