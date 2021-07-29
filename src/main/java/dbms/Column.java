package dbms;

public class Column{
    private Object value;

    public Column(Object v) {
        this.value=v;
    }

    public Object getValue() {
        return value;
    }
}
