package dbms.metadata;

import java.util.List;

public interface Meta {
    public List<String> getOrder();
    public String toDBString();
}
