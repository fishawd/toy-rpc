package cn.sq.rpc.core.transport.netty;

import cn.sq.rpc.core.entity.RequestMessage;
import cn.sq.rpc.core.entity.ResponseMessage;
import cn.sq.rpc.core.handler.RequestHandler;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fishawd
 * @date 2022/8/8 0:29
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<RequestMessage> {

    private Logger logger = LoggerFactory.getLogger(NettyServerHandler.class);

    private RequestHandler requestHandler = new RequestHandler();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        logger.info("新连接 -> {}", channel.remoteAddress());
        super.channelActive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage){
        try {
            if (requestMessage.getHeatBeat() != null && requestMessage.getHeatBeat()){
                logger.info("收到 {} 的心跳包", ctx.channel().remoteAddress());
                return;
            }
            Object result = requestHandler.handle(requestMessage);
            logger.info("服务端发送消息， requestId={}", requestMessage.getRequestId());
            ctx.channel().writeAndFlush(ResponseMessage.success(requestMessage.getRequestId(), result));
        }catch (Exception e){
            logger.error("执行错误", e);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("有异常: {}", cause.getMessage());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
        if (idleStateEvent.state() == IdleState.READER_IDLE){
            logger.info("连接 {} 10s没有读到数据了, 关闭连接", ctx.channel().remoteAddress());
            ctx.channel().close();
        }
    }
}
