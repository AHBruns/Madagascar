package logging;


import java.util.Deque;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class LoggingManager {

    // data members
    private int verbosity;
    private String custom;
    private Deque<Log> q = new LinkedBlockingDeque<>();

    // constants
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // constructors
    public LoggingManager(int _loggingLevel, String _customLoggingKeyword) {
        verbosity = _loggingLevel;
        custom = _customLoggingKeyword;
    }

    // getters
    public int getVerbosity() {
        return verbosity;
    }

    public String getCustom() {
        return custom;
    }

    // setters
    public void setVerbosity(int verbosity) {
        this.verbosity = verbosity;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    // methods
    private boolean addLog(Log _l) {
        return q.offerLast(_l);
    }

    private boolean addLogToHead(Log _l) {
        return q.offerFirst(_l);
    }

    private boolean removeLog(Log _l) {
        return q.remove(_l);
    }

    private boolean clearQ() {
        q.clear();
        return true;
    }

    private void printLog() {
        Log next = q.pollFirst();
        if (next != null && next.getImportance() < verbosity) { return; }
        switch (next.getType()) {
            case "INFO":
                logInfo(next);
                break;
            case "DEBUG":
                logDebug(next);
                break;
            case "QUERY":
                logQuery(next);
                break;
            default:
                logError(next);
                System.exit(-1);
                break;

        }
    }

    private void printAllLogs() {
        while (!q.isEmpty()) {
            printLog();
        }
    }

    private void logInfo(Log _l) {

    } // used for general logging

    private void logDebug(Log _l) {

    } // used when shit breaks

    private void logQuery(Log _l) {} // used when asking the user for input

    private void logError(Log _l) {

    } // used for when


}
