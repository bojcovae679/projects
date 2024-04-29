package markup;

import java.util.List;

public class Strong extends MarkupElement {
    public Strong(List<Element> elements) {
        super(elements);
    }

    public String toMarkdownElement() {
        return "__";
    }

    public String getBBCodeOpenTag() {
        return "[b]";
    }

    public String getBBCodeCloseTag() {
        return "[/b]";
    }

}
