/**
 * Copyright (C) 2008 Happy Fish / YuQing
 * <p>
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page https://github.com/happyfish100/fastdfs for more detail.
 */

package com.itheima.storage.fastdfs.handler;

import com.itheima.storage.fastdfs.handler.pool.Connection;
import com.itheima.storage.fastdfs.handler.pool.ConnectionFactory;
import com.itheima.storage.fastdfs.handler.pool.ConnectionPool;
import com.itheima.storage.fastdfs.util.MyException;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Tracker Server Info
 *
 * @author Happy Fish / YuQing
 * @version Version 1.11
 */
public class TrackerServer {
    protected InetSocketAddress inetSockAddr;


    public TrackerServer(InetSocketAddress inetSockAddr) throws IOException {
        this.inetSockAddr = inetSockAddr;
    }

    public Connection getConnection(ClientGlobal clientGlobal) throws MyException, IOException {
        Connection connection;
        if (clientGlobal.g_connection_pool_enabled) {
            connection = ConnectionPool.getConnection(this.inetSockAddr,clientGlobal);
        } else {
            connection = ConnectionFactory.create(this.inetSockAddr,clientGlobal);
        }
        return connection;
    }
    /**
     * get the server info
     *
     * @return the server info
     */
    public InetSocketAddress getInetSocketAddress() {
        return this.inetSockAddr;
    }

}
