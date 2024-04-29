package markup;
import java.util.List;
public class Strikeout extends MarkupElement {
    public Strikeout(List<Element> elements) {
        super(elements);
    }

    public     String toMarkdownElement() {
        return "~";
    }
    public     String getBBCodeOpenTag(){
        return "[s]";
    }
    public     String getBBCodeCloseTag(){
        return "[/s]";
    }


}
