package com.gzTeleader.screencap.common;

public class Result<T> {

    private String msg;

    private T data;

    private Boolean success;

    public Result() {
    }

    public Result(String msg, T data, Boolean success) {
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Result{" +
                "msg='" + msg + '\'' +
                ", data=" + data +
                ", success='" + success + '\'' +
                '}';
    }
}