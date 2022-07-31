package cn.sq.rpc.core.transport.socket;

import cn.sq.rpc.core.entity.RpcRequest;
import cn.sq.rpc.core.entity.RpcResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author fishawd
 * @date 2022/7/31 13:27
 */
public class SocketClient {
    private Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private String ip;
    private int port;

    public SocketClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }


    public RpcResponse sendRequest(RpcRequest rpcRequest){
        try (SocketChannel socketChannel = SocketChannel.open()){
            socketChannel.connect(new InetSocketAddress(ip, port));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socketChannel.socket().getInputStream());
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();
            return (RpcResponse) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("连接失败 ", e);
        }
        return null;
    }






}
