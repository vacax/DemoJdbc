package com.avathartech.demojdbc.services;

import org.h2.tools.Server;

import java.sql.SQLException;

/**
 * Created by vacax on 27/05/16.
 */
public class BootStrapServices {


    public static void startDb() throws SQLException {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    public static void stopDb() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }

}
