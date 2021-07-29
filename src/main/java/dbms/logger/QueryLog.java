package dbms.logger;

import java.sql.Timestamp;

public class QueryLog {
    private String query;
    private Timestamp submissionTime;

    public QueryLog(String query, Timestamp submissionTime) {
        this.query = query;
        this.submissionTime = submissionTime;
    }

    @Override
    public String toString() {
        return "QueryLog{" +
                "query='" + query + '\'' +
                ", submissionTime=" + submissionTime +
                '}';
    }
}
