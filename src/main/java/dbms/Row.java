package dbms;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static dbms.Constants.delimiter;

public class Row {
    private List<Column> values;
    public Row() {
        values = new LinkedList<>();
    }
    public String toString() {
        List<String> strings= new ArrayList<>();
        for(Column column: values) {
            strings.add((String) column.getValue());
        }
        return String.join(delimiter, strings);
    }
}
