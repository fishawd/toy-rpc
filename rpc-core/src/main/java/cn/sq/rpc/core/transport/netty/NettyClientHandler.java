package cn.sq.rpc.core.transport.netty;


import cn.sq.rpc.core.entity.RequestMessage;
import cn.sq.rpc.core.entity.ResponseMessage;
import cn.sq.rpc.core.transport.netty.store.ResponseStore;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author fishawd
 * @date 2022/8/9 0:26
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ResponseMessage> {
    private Logger logger = LoggerFactory.getLogger(NettyClientHandler.class);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ResponseMessage responseMessage) throws Exception {
        logger.info("客户端收到消息:{}", responseMessage);
        ResponseStore.complete(responseMessage);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE){
                logger.info("5s没有写数据，发送心跳包");
                sendHeatBeat(ctx.channel());
            }
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("出现错误 ", cause);
        ctx.channel().close();
    }

    private void sendHeatBeat(Channel channel){
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.setHeatBeat(true);
        channel.writeAndFlush(requestMessage);
    }
}
