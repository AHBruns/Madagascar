package Blocks;

import Transactions.Transaction;
import Transactions.TransactionSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BlockSerializer {

    // static methods
    public static byte[] serialize(Block _block) throws IOException {
        ByteArrayOutputStream ba = new ByteArrayOutputStream();
        ba.write(_block.getParent_id()); // fixed size
        ba.write(_block.getMerkle_root().getBytes()); // fixed size
        ba.write(ByteBuffer.allocate(Long.BYTES).putLong(_block.getHeight()).array()); // fixed size
        ba.write(_block.getId()); // fixed size
        for (Transaction t : _block.getTransactions()) {
            byte[] btx = TransactionSerializer.serialize(t);
            ba.write(ByteBuffer.allocate(Long.BYTES).putLong(btx.length).array());
            ba.write(btx);
        }
        return ba.toByteArray();
    }

}
