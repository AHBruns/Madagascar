package storage;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.*;
import java.util.HashSet;
import java.util.Set;

public class StorageManger {

    // data members
    private String path;
    private boolean writePermission = false;
    private String custom;
    private Path db;

    // constructor
    public StorageManger(String _path, boolean _write, String _customLoggingKeyword) {
        path = _path;
        writePermission = _write;
        custom = _customLoggingKeyword;
    }

    // getters
    public String getCustom() {
        return custom;
    }

    // setters
    public void setCustom(String custom) {
        this.custom = custom;
    }

    // methods
    public void openDB() throws IOException {
        this.db = Paths.get(this.path);
        // THIS CLEARS THE DB EVERY TIME
        Files.deleteIfExists(this.db);
        Files.createFile(this.db);
        // DON'T LEAVE THIS HERE IN PROD.
    }

    public void writeBlock(byte [] _block) throws IOException {
        Files.write(db, _block, StandardOpenOption.APPEND);
        Files.write(db, ByteBuffer.allocate(4).putInt(_block.length).array(), StandardOpenOption.APPEND);
    }

    public void rollback(int _amount) throws IOException {
        Set<OpenOption> oo = new HashSet<>();
        oo.add(StandardOpenOption.READ);
        oo.add(StandardOpenOption.WRITE);
        SeekableByteChannel bc = Files.newByteChannel(this.db, oo);
        long s = bc.size();
        bc.position(s);
        ByteBuffer bb = ByteBuffer.allocate(4);
        for (int i = 0; i < _amount; i++) {
            System.out.printf("\t%d\n", s);
            s -= 4;
            bc.position(s);
            System.out.printf("\t%s\n", bb.toString());
            bc.read(bb);
            System.out.printf("\t%s\n", bb.toString());
            int len = bb.getInt(0);
            System.out.printf("\t%d\n", len);
            s -= len;
        }
        bc.truncate(s);
    }

    // helpers
    public void printSize() throws IOException {
        System.out.printf("%d\n", Files.size(this.db));
    }

}
