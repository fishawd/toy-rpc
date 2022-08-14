package cn.sq.rpc.core.transport.netty;

import cn.sq.rpc.core.handler.RequestHandler;
import cn.sq.rpc.core.transport.Server;
import cn.sq.rpc.core.transport.netty.codec.RpcServiceCodec;
import cn.sq.rpc.core.util.RequestUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.InetSocketAddress;

/**
 * @author fishawd
 * @date 2022/8/7 10:38
 * Netty服务器创建要点:
 * 1. 编解码协议
 * 2. 处理器handler
 */
public class NettyServer implements Server {


    private int serialize;

    private String host;
    private int port;

    private RequestHandler requestHandler = new RequestHandler();

    public NettyServer(String host, int port){
        this(host, port, 1);
    }
    public NettyServer(String host, int port, int serialize){
        this.host = host;
        this.port = port;
        this.serialize = serialize;
    }

    @Override
    public void startServer() {
        new ServerBootstrap()
                .group(new NioEventLoopGroup(2), new NioEventLoopGroup(4))
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        //心跳包
                        pipeline.addLast(new IdleStateHandler(10, 0, 5));
                        // 编解码
                        pipeline.addLast(new RpcServiceCodec(RequestUtil.getSerializer(serialize)));
                        pipeline.addLast(new NettyServerHandler());

                    }
                })
                .bind(new InetSocketAddress(host, port));
    }
}
