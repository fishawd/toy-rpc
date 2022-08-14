package cn.sq.rpc.core.entity;

import cn.sq.rpc.core.constant.ResponseEnum;

import java.io.Serializable;

/**
 * @author fishawd
 * @date 2022/7/30 17:03
 */
public class ResponseMessage implements Serializable {

    private Long requestId;

    private Integer code;

    private String msg;


    private Object data;

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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public static ResponseMessage success(Long requestId, Object data){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setRequestId(requestId);
        responseMessage.setCode(ResponseEnum.SUCCESS.getCode());
        responseMessage.setMsg(ResponseEnum.SUCCESS.getMsg());
        responseMessage.setData(data);
        return responseMessage;
    }

    public static ResponseMessage fail(ResponseEnum responseEnum){
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setCode(responseEnum.getCode());
        responseMessage.setMsg(responseEnum.getMsg());
        return responseMessage;
    }

}
