package logging;

public class Log {

    // data members
    private String msg;
    private String type;
    private int importance;

    // constructors
    public Log(String _msg, String _type, int _importance) {
        assert(_msg != null);
        assert(_type != null);
        msg = _msg;
        type = _type;
        importance = _importance;
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
