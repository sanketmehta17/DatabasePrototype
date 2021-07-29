package dbms.parser;

import dbms.Database;
import dbms.Datasource;

import java.util.regex.Matcher;

import static java.util.Objects.isNull;

public class UseDatabase extends Parser{

    public UseDatabase(Datasource datasource) {
        super(datasource);
    }

    @Override
    protected String getRegex() {
        return "use +(.+)";
    }

    @Override
    protected void execute(Matcher matcher, String query) {
        String dbName = matcher.group(1).trim();
        Database database = datasource.getDatabase(dbName);
        if (isNull(database)) {
            System.out.println("Database Not present");
            return;
        }
        datasource.setCurrentDatabase(dbName);
    }
}
