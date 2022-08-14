package cn.sq.rpc.core.transport.socket;

import cn.sq.rpc.core.entity.RequestMessage;
import cn.sq.rpc.core.entity.ResponseMessage;
import cn.sq.rpc.core.handler.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.channels.SocketChannel;

/**
 * @author fishawd
 * @date 2022/7/31 21:03
 */
public class Worker implements Runnable {

    private Logger logger = LoggerFactory.getLogger(Worker.class);

    private SocketChannel channel;

    private RequestHandler requestHandler;

    public Worker(SocketChannel channel, RequestHandler requestHandler) {
        this.channel = channel;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(channel.socket().getInputStream());
            ObjectOutputStream outputStream = new ObjectOutputStream(channel.socket().getOutputStream());
            RequestMessage requestMessage = (RequestMessage) inputStream.readObject();
            // 通过反射调用具体的方法
            Object result = requestHandler.handle(requestMessage);
            ResponseMessage responseMessage = ResponseMessage.success(1L, result);
            outputStream.writeObject(responseMessage);
        } catch (IOException | ClassNotFoundException e) {
            logger.error("方法执行出错 ", e);
        }
    }
}
