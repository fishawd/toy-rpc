package cn.sq.rpc.core.serialize;

/**
 * @author fishawd
 * @date 2022/8/7 13:58
 */
public interface Serializer {
    byte[] serialize(Object object);
    Object deSerialize(byte[] bytes);

    int getCode();
}
