package markup;
import static java.util.List.*;
public class Text implements Element {
    String text;
    public Text(String text){
        this.text = text;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(text);
    }

    @Override
    public void toBBCode(StringBuilder sb) {
        sb.append(text);
    }
}
