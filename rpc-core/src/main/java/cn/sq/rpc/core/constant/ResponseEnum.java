package cn.sq.rpc.core.constant;

/**
 * @author fishawd
 * @date 2022/7/31 11:05
 */
public enum ResponseEnum {
    SUCCESS(100, "方法调用成功"),
    FAIL(200, "方法调用失败"),

    CLASS_NOT_FOUND(300, "类未找到"),
    METHOD_NOT_FOUND(301, "方法未找到");


    private Integer code;

    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
