package dbms.parser;

import dbms.Datasource;

import java.util.regex.Matcher;

public class SelectTable extends Parser
{

    public SelectTable(Datasource datasource) {
        super(datasource);
    }

    @Override
    protected String getRegex() {
        return null;
    }

    @Override
    protected void execute(Matcher matcher, String query) {

    }
}
