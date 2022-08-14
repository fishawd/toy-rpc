package cn.sq.rpc.core.transport;

/**
 * @author fishawd
 * @date 2022/8/8 0:55
 */
public interface Client<T> {
    Object sendRequest(T t);
}
