package markup;

import java.util.List;

public class Paragraph implements Item {
    public List<Element> list;

    public Paragraph(List<Element> list) {
        this.list = list;
    }

    public void toMarkdown(StringBuilder sb) {
        for (Element element : list) {
            element.toMarkdown(sb);
        }
    }

    public void toBBCode(StringBuilder sb) {
        for (Element element : list) {
            element.toBBCode(sb);
        }
    }

}
