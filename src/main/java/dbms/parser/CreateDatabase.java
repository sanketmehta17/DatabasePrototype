package dbms.parser;

import dbms.Datasource;

import java.util.regex.Matcher;

public class CreateDatabase extends Parser{

    public CreateDatabase(Datasource datasource) {
        super(datasource);
    }

    @Override
    protected String getRegex() {
        return "create *database *(.*)";
    }

    @Override
    protected void execute(Matcher matcher, String query) {
        String databaseName = matcher.group(1).trim();
        datasource.createDB(databaseName);
    }
}
