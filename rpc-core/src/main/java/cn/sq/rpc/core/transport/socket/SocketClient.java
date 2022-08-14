package cn.sq.rpc.core.transport.socket;

import cn.sq.rpc.core.entity.ResponseMessage;
import cn.sq.rpc.core.transport.Client;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author fishawd
 * @date 2022/7/31 13:27
 */
public class SocketClient implements Client {
    private Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private String ip;
    private int port;

    public SocketClient(String ip, int port){
        this.ip = ip;
        this.port = port;
    }


    public ResponseMessage sendRequest(Object obj){
        try (SocketChannel socketChannel = SocketChannel.open()){
            socketChannel.connect(new InetSocketAddress(ip, port));
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketChannel.socket().getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socketChannel.socket().getInputStream());
            objectOutputStream.writeObject(obj);
            objectOutputStream.flush();
            return (ResponseMessage) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            logger.error("连接失败 ", e);
        }
        return null;
    }






}
