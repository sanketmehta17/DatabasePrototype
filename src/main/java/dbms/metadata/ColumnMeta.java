package dbms.metadata;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static dbms.Constants.escapeSequence;
import static dbms.Constants.ternaryDelimiter;
import static java.util.Arrays.stream;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toList;

public class ColumnMeta implements Meta{
    private String name;
    private DataType type;
    private Integer order;
    private Object defaultValue;
    private Boolean allowNull=false;
    private Boolean autoIncrement=false;
    private Integer currentIndex=0;

    public ColumnMeta() {
    }

    public ColumnMeta(String name, DataType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public DataType getType() {
        return type;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    @Override
    public List<String> getOrder() {
        return Arrays.asList("name", "type", "order", "defaultValue",
                "allowNull", "autoIncrement", "currentIndex");
    }

    public String getDefaultStringValue() {
        return isNull(defaultValue) ? null: defaultValue.toString();
    }

    public String toDBString() {
        List<String> columnMetaFieldValues = Arrays.asList(name, type.toString(), order.toString(),
                getDefaultStringValue(), allowNull.toString(), autoIncrement.toString(), currentIndex.toString());
        return String.join(ternaryDelimiter, columnMetaFieldValues);
    }

    public ColumnMeta fromString(String string) {
        final List<String> values = stream(string.split(escapeSequence + ternaryDelimiter)).collect(toList());
        this.name = values.get(0);
        this.type = DataType.valueOf(values.get(1));
        this.order = Integer.valueOf(values.get(2));
        this.defaultValue = values.get(3).equals("null") ? null : values.get(3);
        this.allowNull = Boolean.valueOf(values.get(4));
        this.autoIncrement = Boolean.valueOf(values.get(5));
        this.currentIndex = Integer.valueOf(values.get(6));
        return this;
    }
}
