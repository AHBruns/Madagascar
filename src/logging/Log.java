package logging;

public class Log {

    // data members
    private String msg;
    private String type;
    private int importance;
    private long nonce; // used to prevent hash collisions of identical logs

    // constructors
    public Log(String _msg, String _type, int _importance, long _nonce) {
        msg = _msg;
        type = _type;
        importance = _importance;
        nonce = _nonce;
    }

    // getters
    String getMsg() {
        return this.msg;
    }

    String getType() {
        return this.type;
    }

    int getImportance() {
        return this.importance;
    }
}
