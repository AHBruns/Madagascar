package repl;


import java.util.Scanner;


import api.APIManager;
import logging.LoggingManager;
import networking.NetworkingManger;
import serialization.SerializationManager;
import storage.StorageManger;


public class Repl {

    private APIManager api;
    private NetworkingManger net;
    private SerializationManager serial;
    private StorageManger store;
    private LoggingManager log;
    private Scanner s;

    public Repl(APIManager _API, NetworkingManger _Networking, SerializationManager _Serialization, StorageManger _Storage, LoggingManager _Logging) {
        this.api = _API;
        this.net = _Networking;
        this.serial = _Serialization;
        this.store = _Storage;
        this.log = _Logging;
        this.s = new Scanner(System.in);
    }

    public boolean start() {
        // TODO | implement manager start methods
//        api.start();
//        net.start();
//        serial.start();
//        store.start();
//        log.start();
        return true;
    }

    public boolean tick() {

        return true;
    }
}
