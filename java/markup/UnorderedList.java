package markup;

import java.util.List;

public class UnorderedList extends BBCodeItem{
    public UnorderedList(List<ListItem> items) {
        super(items);
    }

    String toBBCodeElement(){
        return "[list]";
    }
    String toBBCodeElement1(){
        return "[/list]";
    }
}
