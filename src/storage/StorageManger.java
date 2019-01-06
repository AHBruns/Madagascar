package storage;

public class StorageManger {

    // data members
    private String path;
    private boolean writePermission = false;
    private String custom;

    // constructor
    public StorageManger(String _path, boolean _write, String _customLoggingKeyword) {
        path = _path;
        writePermission = _write;
        custom = _customLoggingKeyword;
    }
}
