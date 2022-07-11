package org.knowyourdenfender.enhelper.pojo;

public class ServerResult {
    private int state;
    private String message;
    private Object payload;

    public ServerResult() {
    }

    public ServerResult(int state, String message, Object payload) {
        this.state = state;
        this.message = message;
        this.payload = payload;
    }


    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }
}
