package com.itheima.ige.rpc.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request implements Serializable {

    private String reqId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    // type(RPC=1,Heartbeat=2,ReloadNodeList=3)
    //public static final Integer TYPE_RPC=1,TYPE_HEARTBEAT=2,TYPE_RELOADLIST=3;
    //private Integer type=1;
}
