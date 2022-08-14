package cn.sq.rpc.core.test;


import cn.sq.rpc.core.entity.RequestMessage;
import cn.sq.rpc.core.entity.ResponseMessage;
import cn.sq.rpc.core.transport.netty.NettyClientHandler;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.junit.jupiter.api.Test;

/**
 * @author fishawd
 * @date 2022/8/9 22:59
 */
public class NettyClientTest {

    @Test
    public void testNettyClient(){
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG),
                new NettyClientHandler()
        );
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMsg("666");
        embeddedChannel.writeInbound(responseMessage);
    }

    @Test
    public void testNettyServer(){
        EmbeddedChannel embeddedChannel = new EmbeddedChannel(
                new LoggingHandler(LogLevel.DEBUG)
        );
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setMethodName("666");
        embeddedChannel.writeInbound(requestMessage);
    }
}
