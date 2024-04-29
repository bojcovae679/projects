package markup;

import java.util.List;

public class ListItem {
    public List<Item> list;
    public ListItem(List<Item> items) {
        this.list = items;
    }


    public void toBBCode(StringBuilder sb) {
        sb.append("[*]");
        for(Item item : list){
            item.toBBCode(sb);
        }
    }
}
