package dbms.metadata;

import java.util.Arrays;
import java.util.List;

import static dbms.Constants.secondaryDelimiter;
import static dbms.Constants.ternaryDelimiter;

public class ColumnMeta implements Meta{
    private String name;
    private DataType type;
    private Integer order;
    private Object defaultValue;
    private Boolean allowNull;
    private Boolean autoIncrement;
    private Integer currentIndex;

    @Override
    public List<String> getOrder() {
        return Arrays.asList("name", "type", "order", "defaultValue",
                "allowNull", "autoIncrement", "currentIndex");
    }

    public String toString() {
        List<String> columnMetaFieldValues = Arrays.asList(name, type.toString(), order.toString(),
                defaultValue.toString(), allowNull.toString(), autoIncrement.toString(), currentIndex.toString());
        return String.join(ternaryDelimiter, columnMetaFieldValues);
    }
}
