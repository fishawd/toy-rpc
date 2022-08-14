package cn.sq.rpc.core.constant;

/**
 * @author fishawd
 * @date 2022/8/7 21:58
 */
public enum SerializerTypeEnum {

    JDK_SERIALIZER(1),
    JSON_SERIALIZER(2);

    private int code;

    SerializerTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
