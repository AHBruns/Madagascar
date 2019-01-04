import java.lang.*;
import java.util.*;


import logging.Log;
import logging.LoggingManager;
import repl.Repl;
import api.APIManager;
import networking.NetworkingManger;
import serialization.SerializationManager;
import storage.StorageManger;


public class Madagascar {

    // entry point
    public static void main(String[] args) {
        try {
            Madagascar instance;
            if (args.length == 0) { // no args load
                instance = new Madagascar();
            } else if (args[0].equals("--from-storage") || args[0].equals("-FS")) { // from storage load
                instance = new Madagascar(args);
            } else if (args[0].equals("--from-remote") || args[0].equals("-FR")) { // from remote load
                // TODO | implement from remote load
                instance = new Madagascar();
            } else if (args[0].equals("--clean") || args[0].equals("-C")) { // clean load
                // TODO | implement wipe to allow for clean load
                instance = new Madagascar();
                instance.wipe();
                instance = new Madagascar();
            } else { // args error
                throw new Exception("WTH did you type!");
            }
            instance.run();
        } catch (Exception e) { // crash if you
            e.printStackTrace();
            System.exit(-1);
        }
    }

    // data members
    private ArrayList<String> commandLineArgs = new ArrayList<>();
    private String storagePath = "/"; // defaults to the working directory
    private int loggingLevel = 1; // 0 means no logging, 5 means maximum verbosity
    private boolean storageWrite = true; // toggled to control if Madagascar writes to storage or not
    private String customLoggingKeyword = ""; // keyword used to allow for codebase modification logging statements to trigger via the CL
    private Random rng = new Random();

    // constructors
    private Madagascar() {

    } // non arg start-up

    private Madagascar(String[] _args) { // from storage start-up
        commandLineArgs = new ArrayList<>(Arrays.asList(_args));
        this.storageStartAssertions();
        commandLineArgs.remove(0);
        this.storagePath = commandLineArgs.remove(1);
        this.iterDecoration(commandLineArgs);
    }

    // constructor helpers
    private void storageStartAssertions() {
        assert(commandLineArgs.get(0).equals("--from-remote") || commandLineArgs.get(0).equals("-FR"));
        assert(commandLineArgs.size() >= 2);
    }

    private void iterDecoration(ArrayList<String> _decorations) {
        ArrayList<String> curDec = new ArrayList<>();
        for (String dec : _decorations) {
            if (dec.startsWith("-")) { // indicates the beginning of a new decoration statement
                parseDecoration(curDec);
                curDec.clear();
            }
        }
    }

    private void parseDecoration(ArrayList<String> _decoration) {
        try {
            if (_decoration.size() == 0) {
                return;
            }
            String decType = _decoration.remove(0);
            switch (decType) {
                case "--logging-level": // adjusts logging verbosity
                    this.loggingLevel = Integer.parseInt(_decoration.get(1));
                    break;
                case "-LL":
                    this.loggingLevel = Integer.parseInt(_decoration.get(1));
                    break;
                case "--memory-only": // toggles if Madagascar writes to storage (useful for testing)
                    this.storageWrite = Boolean.parseBoolean(_decoration.get(1));
                    break;
                case "-MO":
                    this.storageWrite = Boolean.parseBoolean(_decoration.get(1));
                    break;
                case "--custom-logging": // allows custom logging statements to be added to the code base and triggered by a keyword arg
                    this.customLoggingKeyword = _decoration.get(1);
                    break;
                case "-CL":
                    this.customLoggingKeyword = _decoration.get(1);
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }


    // methods
    private void wipe() {

    } // TODO | implement wipe (deletes all storage artifacts to ensure clean run)

    private void run() {
//        APIManager API = new APIManager(customLoggingKeyword);
//        NetworkingManger Networking = new NetworkingManger(customLoggingKeyword);
//        SerializationManager Serialization = new SerializationManager(customLoggingKeyword);
//        StorageManger Storage = new StorageManger(storagePath, storageWrite, customLoggingKeyword);
        LoggingManager Logging = new LoggingManager(loggingLevel, customLoggingKeyword);

        Logging.addLog(new Log("this is my message", "INFO", 1, rng.nextLong()));
        Logging.printAllLogs();

        // Repl repl = new Repl(API, Networking, Serialization, Storage, Logging, customLoggingKeyword);
//        boolean brk = !repl.start(); // returns true on successful start & false on faulty start
//        while (!brk) {
//            brk = repl.tick();
//        }
    }
}
