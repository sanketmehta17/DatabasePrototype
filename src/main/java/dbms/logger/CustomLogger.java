package dbms.logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static dbms.Constants.logFile;
import static java.util.Objects.isNull;

public class CustomLogger {
    private static Handler handler;
    private static final Logger logger = Logger.getLogger("LOGGER");
    public CustomLogger() {}


    public static void setOrGetHandler() {
        if(isNull(handler)) {
            try {
                handler=new FileHandler(logFile, true);
                handler.setFormatter(new SimpleFormatter());
                logger.addHandler(handler);
            } catch (IOException e) {
                System.out.println("Log file error");
            }
        }
    }
    public static Logger getLogger() {
        setOrGetHandler();
        return logger;
    }
}
