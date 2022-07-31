import cn.sq.rpc.core.transport.ClientProxy;
import cn.sq.rpc.core.transport.socket.SocketClient;
import cn.sq.rpc.service.HelloService;
import cn.sq.rpc.service.model.User;

/**
 * @author fishawd
 * @date 2022/8/1 0:19
 */
public class TestSocketClient {
    public static void main(String[] args) {
        SocketClient socketClient = new SocketClient("127.0.0.1", 8000);
        ClientProxy clientProxy = new ClientProxy(socketClient);
        HelloService helloService = clientProxy.getProxy(HelloService.class);
        System.out.println(helloService.sayHello(new User("张三", 22)));
    }
}
