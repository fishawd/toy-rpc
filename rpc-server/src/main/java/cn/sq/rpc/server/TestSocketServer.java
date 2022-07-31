package cn.sq.rpc.server;


import cn.sq.rpc.core.provider.ServiceProvider;
import cn.sq.rpc.core.transport.socket.SocketServer;

/**
 * @author fishawd
 * @date 2022/8/1 0:20
 */
public class TestSocketServer {
    public static void main(String[] args) {
        SocketServer socketServer = new SocketServer("127.0.0.1", 8000);
        //暂时手动添加
        ServiceProvider.getInstance().addService("cn.sq.rpc.service.HelloService", new HelloServiceImpl());
        socketServer.startServer();
    }
}
