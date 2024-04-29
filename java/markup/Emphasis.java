package markup;

import java.util.List;

public class Emphasis extends MarkupElement {

    public Emphasis(List<Element> elements) {
        super(elements);
    }

    public String toMarkdownElement() {
        return "*";
    }

    public String getBBCodeOpenTag() {
        return "[i]";
    }

    public String getBBCodeCloseTag() {
        return "[/i]";
    }
}

