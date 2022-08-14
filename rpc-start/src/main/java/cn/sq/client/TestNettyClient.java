package cn.sq.client;

import cn.sq.rpc.core.transport.ClientProxy;
import cn.sq.rpc.core.transport.netty.NettyClient;
import cn.sq.rpc.core.transport.socket.SocketClient;
import cn.sq.rpc.service.HelloService;
import cn.sq.rpc.service.model.User;

import java.util.concurrent.CountDownLatch;

/**
 * @author fishawd
 * @date 2022/8/1 0:19
 */
public class TestNettyClient {
    public static void main(String[] args) throws InterruptedException {
        NettyClient nettyClient = new NettyClient("127.0.0.1", 8001);
        ClientProxy clientProxy = new ClientProxy(nettyClient);
        HelloService helloService = clientProxy.getProxy(HelloService.class);
        System.out.println(helloService.sayHello(new User("张三", 22)));
        System.out.println(helloService.sayHello(new User("李四", 34)));
//        nettyClient.close();

        NettyClient nettyClient2 = new NettyClient("127.0.0.1", 8001);
        ClientProxy clientProxy2 = new ClientProxy(nettyClient2);
        HelloService helloService2 = clientProxy2.getProxy(HelloService.class);
        System.out.println(helloService2.sayHello(new User("张三2", 222)));
        System.out.println(helloService2.sayHello(new User("李四2", 342)));
    }
}
