package cn.sq.rpc.core.transport.netty.codec;

import cn.sq.rpc.core.serialize.JDKSerializer;
import cn.sq.rpc.core.serialize.Serializer;
import cn.sq.rpc.core.util.RequestUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author fishawd
 * @date 2022/8/7 11:20
 */
public class RpcServiceCodec extends ByteToMessageCodec<Object> {

    private Logger logger = LoggerFactory.getLogger(RpcServiceCodec.class);

    /**
     * 魔数  0x01表示正常请求
     */
    private static final byte MAGIC = 1;
    /**
     * 协议版本 0x01
     */
    private static final byte VERSION = 1;

    private Serializer serializer;

    public RpcServiceCodec() {
        this.serializer = new JDKSerializer();
    }

    public RpcServiceCodec(Serializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        // 1. 魔数
        out.writeByte(MAGIC);
        // 2. 协议版本
        out.writeByte(VERSION);
        out.writeInt(serializer.getCode());
        byte[] bytes = serializer.serialize(msg);
        out.writeInt(bytes.length);
        out.writeBytes(bytes);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        byte magic = in.readByte();
        if (magic != MAGIC) {
            throw new RuntimeException("数据包无效");
        }
        byte version = in.readByte();
        if (version != VERSION) {
            throw new RuntimeException("协议版本无效");
        }
        int decodeSerialize = in.readInt();
        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Serializer decodeSerializer = RequestUtil.getSerializer(decodeSerialize);
        Object obj = decodeSerializer.deSerialize(bytes);
        out.add(obj);
    }
}
