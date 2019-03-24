package Blocks;

import Transactions.Transaction;
import Transactions.TransactionSerializer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.security.*;
import java.util.Base64;
import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Block {

    // data members
    //// header
    private byte[] parent_id;
    private String merkle_root;
    private Long height;
    private byte[] id;
    //// body
    private HashSet<Transaction> transactions = new HashSet<>();

    // constructors
    public Block(long _height, HashSet<Transaction> _transactions, byte[] _parent_id, PrivateKey _priv) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException {
        this.height = _height;
        this.transactions = _transactions;
        this.parent_id = _parent_id;
        make_merkle_tree();
        sign(_priv);
    }

    // getters
    public byte[] getParent_id() {
        return this.parent_id;
    }

    public String getMerkle_root() {
        return this.merkle_root;
    }

    public long getHeight() {
        return this.height;
    }

    public byte[] getId() {
        return this.id;
    }

    public HashSet<Transaction> getTransactions() {
        return this.transactions;
    }

    // internal helpers
    private void sign(PrivateKey _priv) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, IOException{
        Signature dsa = Signature.getInstance("SHA256withECDSA");
        dsa.initSign(_priv);
        ByteArrayOutputStream signing_msg = new ByteArrayOutputStream();
        signing_msg.write(this.parent_id);
        signing_msg.write(this.merkle_root.getBytes());
        signing_msg.write(ByteBuffer.allocate(Long.BYTES).putLong(this.height).array());
        dsa.update(signing_msg.toByteArray());
        this.id = dsa.sign();
    }

    private void make_merkle_tree() throws NoSuchAlgorithmException {
        int i = 1;
        int j = 0;
        while (Math.pow(i, 2) <= this.transactions.size()) {
            i++;
            j = (int) Math.pow(i, 2) - this.transactions.size();
        }
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        Queue<String> hashes = new ConcurrentLinkedQueue<>();
        for (Transaction t : this.transactions) {
            hashes.offer(Base64.getEncoder().encodeToString(digest.digest(TransactionSerializer.serialize(t)))); // hash the transaction
        }
        for (int idx = 0; idx < j; idx++) {
            hashes.offer("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
        }
        while (hashes.size() > 1) {
            hashes.offer(Base64.getEncoder().encodeToString(digest.digest((hashes.poll() + hashes.poll()).getBytes())));
        }
        this.merkle_root = hashes.poll();
    }

}
