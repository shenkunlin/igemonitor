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
public class Response implements Serializable {
    private String reqId;
    private Object result;
    private Throwable cause;
    public boolean isError() {
        return cause != null;
    }
}