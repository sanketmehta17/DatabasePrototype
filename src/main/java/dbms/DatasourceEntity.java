package dbms;

import java.io.IOException;

public interface DatasourceEntity {
    void create(String name, String path) throws IOException;
    void delete();
}
