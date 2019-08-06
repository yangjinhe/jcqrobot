package com.yjh.cqa.vo;

import com.yjh.cqa.util.JsonUtil;

import java.io.Serializable;
import java.util.List;

public final class RspResult implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean success;
    private String code;
    private String msg;
    private Object data;
    private List errors;

    public static RspResult getSuccessResult() {
        RspResult result = new RspResult();
        result.setSuccess(true);
        return result;
    }

    public static RspResult getFailtResult(String errMsg) {
        RspResult result = new RspResult();
        result.setSuccess(false).setMsg(errMsg);
        return result;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public RspResult setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public String getCode() {
        return this.code;
    }

    public RspResult setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return this.msg;
    }

    public RspResult setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return this.data;
    }

    public RspResult setData(Object data) {
        this.data = data;
        return this;
    }

    public List getErrors() {
        return this.errors;
    }

    public RspResult setErrors(List errors) {
        this.errors = errors;
        return this;
    }

    public String toString() {
        return JsonUtil.getJsonString(this);
    }
}
