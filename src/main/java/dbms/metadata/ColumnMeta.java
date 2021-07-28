package dbms.metadata;

import java.util.Arrays;
import java.util.List;

import static dbms.Constants.ternaryDelimiter;
import static java.util.Objects.isNull;

public class ColumnMeta implements Meta{
    private String name;
    private DataType type;
    private Integer order;
    private Object defaultValue;
    private Boolean allowNull=false;
    private Boolean autoIncrement=false;
    private Integer currentIndex=0;

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

    public String toString() {
        List<String> columnMetaFieldValues = Arrays.asList(name, type.toString(), order.toString(),
                getDefaultStringValue(), allowNull.toString(), autoIncrement.toString(), currentIndex.toString());
        return String.join(ternaryDelimiter, columnMetaFieldValues);
    }
}
