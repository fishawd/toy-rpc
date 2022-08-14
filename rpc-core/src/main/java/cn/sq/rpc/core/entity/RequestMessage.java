package cn.sq.rpc.core.entity;

import java.io.Serializable;

/**
 * @author fishawd
 * @date 2022/7/30 17:03
 */
public class RequestMessage implements Serializable {
    private Long requestId;

    private Boolean heatBeat;
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

    public Boolean getHeatBeat() {
        return heatBeat;
    }

    public void setHeatBeat(Boolean heatBeat) {
        this.heatBeat = heatBeat;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

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
