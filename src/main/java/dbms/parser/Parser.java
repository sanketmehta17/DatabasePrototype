package dbms.parser;

import dbms.Datasource;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Objects.isNull;

public abstract class Parser {
    protected Datasource datasource;
    public Parser(Datasource datasource) {
        this.datasource = datasource;
    }
    abstract protected String getRegex();
    public Boolean isValid(String query) {
        Pattern r = Pattern.compile(getRegex());
        Matcher matcher = r.matcher(query);
        if(matcher.find()) {
            execute(matcher, query);
            return true;
        }
        return false;
    }

    void validateCurrentDatabase() throws Exception{
        if (isNull(datasource.getCurrentDatabase())) {
            throw new Exception();
        }
    }
    abstract protected void execute(Matcher matcher, String query);
}
