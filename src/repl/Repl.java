package repl;


import java.util.Scanner;


import api.APIManager;
import networking.NetworkingManger;
import serialization.SerializationManager;
import storage.StorageManger;


public class Repl {

    private APIManager api;
    private NetworkingManger net;
    private SerializationManager serial;
    private StorageManger store;
    private Scanner s;

    public Repl(APIManager _API, NetworkingManger _Networking, SerializationManager _Serialization, StorageManger _Storage) {
        this.api = _API;
        this.net = _Networking;
        this.serial = _Serialization;
        this.store = _Storage;
        this.s = new Scanner(System.in);
    }

    public boolean start() {
        api.start();
        net.start();
        serial.start();
        store.start();
        return true;
    }

    public boolean tick() {
        System.out.println("");
        return true;
    }
}
