package cn.sq.rpc.core.entity;

import cn.sq.rpc.core.constant.ResponseEnum;

import java.io.Serializable;

/**
 * @author fishawd
 * @date 2022/7/30 17:03
 */
public class RpcResponse<T> implements Serializable {

    private Integer code;

    private String msg;

    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
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

    public static <T> RpcResponse<T> success(T data){
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setCode(ResponseEnum.SUCCESS.getCode());
        rpcResponse.setMsg(ResponseEnum.SUCCESS.getMsg());
        rpcResponse.setData(data);
        return rpcResponse;
    }

    public static <T> RpcResponse<T> fail(ResponseEnum responseEnum){
        RpcResponse<T> rpcResponse = new RpcResponse<>();
        rpcResponse.setCode(responseEnum.getCode());
        rpcResponse.setMsg(responseEnum.getMsg());
        return rpcResponse;
    }
}
