package com.itheima.storage.fastdfs.handler.pool;
import com.itheima.storage.fastdfs.handler.ClientGlobal;
import com.itheima.storage.fastdfs.util.MyException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ConnectionFactory {
    /**
     * create from InetSocketAddress
     *
     * @param socketAddress
     * @return
     * @throws IOException
     */
    public static Connection create(InetSocketAddress socketAddress,ClientGlobal global) throws MyException {
        try {
            Socket sock = new Socket();
            sock.setReuseAddress(true);
            sock.setSoTimeout(global.g_network_timeout);
            sock.connect(socketAddress, global.g_connect_timeout);
            return new Connection(sock, socketAddress);
        } catch (Exception e) {
            throw new MyException("connect to server " + socketAddress.getAddress().getHostAddress() + ":" + socketAddress.getPort() + " fail, emsg:" + e.getMessage());
        }
    }
}
