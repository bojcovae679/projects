package markup;

import java.util.List;

abstract class MarkupElement implements Element {
    public List<Element> list;

    public abstract String toMarkdownElement();

    public abstract String getBBCodeOpenTag();

    public abstract String getBBCodeCloseTag();

    public MarkupElement(List<Element> list) {
        this.list = list;
    }

    public void toMarkdown(StringBuilder sb) {
        sb.append(toMarkdownElement());
        for (Element element : list) {
            element.toMarkdown(sb);
        }
        sb.append(toMarkdownElement());
    }

    public void toBBCode(StringBuilder sb) {
        sb.append(getBBCodeOpenTag());
        for (Element element : list) {
            element.toBBCode(sb);
        }
        sb.append(getBBCodeCloseTag());
    }
}
