package markup;

import java.util.List;

abstract class BBCodeItem implements Item{
    public List<ListItem> list;
    abstract String toBBCodeElement();
    abstract String toBBCodeElement1();
    public BBCodeItem(List<ListItem> list){
        this.list = list;
    }
    public void toBBCode(StringBuilder sb) {
        sb.append(toBBCodeElement());
        for(ListItem item : list){
            item.toBBCode(sb);
        }
        sb.append(toBBCodeElement1());
    }
}
