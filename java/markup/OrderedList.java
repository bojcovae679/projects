package markup;

import java.util.List;

public class OrderedList extends BBCodeItem{
    public OrderedList(List<ListItem> items) {
        super(items);
    }

    String toBBCodeElement(){
        return "[list=1]";
    }
    String toBBCodeElement1(){
        return "[/list]";
    }
}
