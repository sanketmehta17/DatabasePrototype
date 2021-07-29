package dbms.logger;

public class GeneralLog {
    private long executionTime;
    private String query;

    public GeneralLog(long executionTime, String query) {
        this.executionTime = executionTime;
        this.query = query;
    }

    @Override
    public String toString() {
        return "GeneralLog{" +
                "executionTime=" + executionTime +
                ", query='" + query + '\'' +
                '}';
    }
}
