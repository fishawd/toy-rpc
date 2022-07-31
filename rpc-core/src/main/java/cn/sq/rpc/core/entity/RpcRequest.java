package cn.sq.rpc.core.entity;

import java.io.Serializable;

/**
 * 将一些调用信息发送给被调用方，用于明确调用哪个方法
 * @author fishawd
 * @date 2022/7/30 17:03
 */
public class RpcRequest implements Serializable {
    /**
     * 接口名
     */
    private String interfaceName;

    /**
     * 方法名
     */
    private String methodName;

    /**
     * 方法参数列表
     */
    private Object[] parameters;

    /**
     * 方法参数类型
     */
    private Class<?>[] parameterTypes;

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}
