package cn.sq.rpc.core.serialize;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author fishawd
 * @date 2022/8/7 14:09
 */
public class JDKSerializer implements Serializer {

    private Logger logger = LoggerFactory.getLogger(JDKSerializer.class);

    private static final int SERIALIZE_CODE = 1;

    @Override
    public byte[] serialize(Object object){
        try(ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeObject(object);
            return bos.toByteArray();
        }catch (IOException e){
            logger.error("序列化错误", e);
        }
        return null;
    }

    @Override
    public Object deSerialize(byte[] bytes) {
        try(ByteArrayInputStream bos = new ByteArrayInputStream(bytes);
            ObjectInputStream oos = new ObjectInputStream(bos)){
            return oos.readObject();
        }catch (IOException | ClassNotFoundException e){
            logger.error("反序列化错误", e);
        }
        return null;
    }

    @Override
    public int getCode() {
        return SERIALIZE_CODE;
    }
}
