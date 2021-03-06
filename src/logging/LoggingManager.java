package logging;


import java.util.Deque;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingDeque;

public class LoggingManager {

    // data members
    private int verbosity;
    private String custom;
    private Scanner s;
    private Deque<Log> q = new LinkedBlockingDeque<>();
    private HashMap<Log, String> inputs = new HashMap<>();
    private Random rng = new Random();

    // constants
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";

    // constructors
    public LoggingManager(int _loggingLevel, String _customLoggingKeyword) {
        verbosity = _loggingLevel;
        custom = _customLoggingKeyword;
        s = new Scanner(System.in);
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
    public String retreiveInput(Log _l) {
        if (inputs.containsKey(_l)) {
            return inputs.remove(_l);
        }
        return null;
    }

    public boolean addLog(Log _l) {
        return q.offerLast(_l);
    }

    public boolean addLogToHead(Log _l) {
        return q.offerFirst(_l);
    }

    public boolean removeLog(Log _l) {
        return q.remove(_l);
    }

    public boolean clearQ() {
        q.clear();
        return true;
    }

    public void printLog() {
        Log next = q.pollFirst();
        if (next == null || next.getImportance() < verbosity) { return; }
        // TODO | implement log timestamping
        // TODO | implement log file write functionality
        switch (next.getType()) {
            case "INFO":
                logInfo(next);
                break;
            case "GOOD":
                logGood(next);
                break;
            case "DEBUG":
                logDebug(next);
                break;
            case "QUERY":
                logQuery(next);
                break;
            case "ERROR":
                logError(next);
                break;
            default:
                logMisc(next);
                break;
        }
    }

    public void printAllLogs() {
        while (!q.isEmpty()) {
            printLog();
        }
    }

    private void logInfo(Log _l) {
        System.out.println(ANSI_YELLOW + "[INFO][" + _l.getImportance() +  "]  " + _l.getMsg() + ANSI_RESET);
        // EX: "[INFO][#]  this is the message"
    }

    private void logGood(Log _l) {
        System.out.println(ANSI_GREEN +  "[GOOD][" + _l.getImportance() +  "]  " + _l.getMsg() + ANSI_RESET);
        // EX: "[GOOD][#]  this is the message"
    }

    private void logDebug(Log _l) {
        System.out.println(ANSI_PURPLE + "[DEBUG][" + _l.getImportance() + "] " + _l.getMsg() + ANSI_RESET);
        // EX: "[DEBUG][#] this is the message"
    }

    private void logQuery(Log _l) {
        clearScanner();
        System.out.println(ANSI_CYAN +   "[QUERY][" + _l.getImportance() + "] " + _l.getMsg() + ANSI_RESET);
        System.out.print(": ");
        inputs.put(_l, s.nextLine());
        // EX: "[QUERY][#] this is the message"
        //     ": "
    }

    private void logError(Log _l) {
        System.out.println(ANSI_RED +    "[ERROR][" + _l.getImportance() + "] " + _l.getMsg() + ANSI_RESET);
        // EX: "[ERROR][#] this is the message"
    }

    private void logMisc(Log _l) {
        if (_l != null) {
            System.out.println(ANSI_RED + "[Misc][" + _l.getImportance() + "]  " + _l.getMsg() + ANSI_RESET);
            // EX: "[MISC][#]  this is the message"
        } else {
            logError(new Log("null log print attempt.", "ERROR", 0, rng.nextLong()));
        }
    }

    // helpers
    private void clearScanner() {
        while (s.hasNextLine()) {
            s.nextLine();
        }
    }


}
