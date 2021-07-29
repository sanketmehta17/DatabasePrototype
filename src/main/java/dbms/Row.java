package dbms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import static dbms.Constants.delimiter;
import static dbms.Constants.escapeSequence;

public class Row {
    private List<Column> values;
    public Row() {
        values = new LinkedList<>();
    }

    public Row(List<Column> columns) {
        values = columns;
    }

    public void addValue(Column column) {
        values.add(column);
    }

    public String toDBString() {
        List<String> strings= new ArrayList<>();
        for(Column column: values) {
            strings.add((String) column.getValue());
        }
        return String.join(delimiter, strings);
    }

    public Row fromString(String string) {
        values = Arrays.stream(string.split(escapeSequence + delimiter))
                .map(s -> new Column(s)).collect(Collectors.toList());
        return this;
    }

    public List<Column> getValues() {
        return values;
    }
}
