package cn.sq.rpc.core.transport.netty;

import cn.sq.rpc.core.entity.RequestMessage;
import cn.sq.rpc.core.entity.ResponseMessage;
import cn.sq.rpc.core.transport.Client;
import cn.sq.rpc.core.transport.netty.codec.RpcServiceCodec;
import cn.sq.rpc.core.transport.netty.store.ChannelStore;
import cn.sq.rpc.core.transport.netty.store.ResponseStore;
import cn.sq.rpc.core.transport.socket.SocketClient;
import cn.sq.rpc.core.util.RequestUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author fishawd
 * @date 2022/8/8 1:01
 */
public class NettyClient implements Client<RequestMessage> {

    private Logger logger = LoggerFactory.getLogger(SocketClient.class);

    private String ip;
    private int port;

    private int serialize;

    private Bootstrap bootstrap = new Bootstrap();

    private NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(1);

    private Channel channel;


    public NettyClient(String ip, int port) {
        this(ip, port, 1);
    }
    public NettyClient(String ip, int port, int serialize) {
        this.ip = ip;
        this.port = port;
        this.serialize = serialize;
    }

    private void connect(InetSocketAddress address){
        try {
            channel = bootstrap.channel(NioSocketChannel.class)
                .group(nioEventLoopGroup)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new IdleStateHandler(0, 5, 0));
                        pipeline.addLast(new RpcServiceCodec(RequestUtil.getSerializer(serialize)));
                        pipeline.addLast(new NettyClientHandler());
                    }
                }).connect(address)
                .sync()
                .channel();
            logger.info("????????????, ????????????={}", channel.remoteAddress());
            ChannelStore.put(address.toString(), channel);
        }catch (InterruptedException e){
            logger.error("????????????", e);
        }
    }


    @Override
    public Object sendRequest(RequestMessage t) {
        InetSocketAddress address = new InetSocketAddress(ip, port);
        // channel??????
        if (Objects.isNull(channel)){
            if (ChannelStore.containsChannel(address.toString())){
                logger.info("channel?????????ChannelStore key={}", address);
                channel = ChannelStore.getChannel(address.toString());
            }else{
                logger.info("???channel, key={}", address);
                connect(address);
            }
        }
        // ????????????????????????????????????
        CompletableFuture<ResponseMessage> completeFuture = new CompletableFuture<>();
        ResponseStore.put(t.getRequestId(), completeFuture);
        ChannelFuture channelFuture = channel.writeAndFlush(t);
        channelFuture.addListener(future -> {
            if (future.isSuccess()){
                logger.info("???????????????????????????");
            }else {
                logger.info("???????????????????????????");
            }
        });
        return completeFuture;
    }

    public void close(){
        if (Objects.nonNull(channel)){
            ChannelFuture future =  channel.close();
            try {
                future.sync();
                nioEventLoopGroup.shutdownGracefully();
            } catch (InterruptedException e) {
                logger.error("????????????", e);
            }
        }
    }
}
