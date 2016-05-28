package com.avathartech.demojdbc.services;

import org.h2.tools.Server;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by vacax on 27/05/16.
 */
public class BootStrapServices {

    /**
     *
     * @throws SQLException
     */
    public static void startDb() throws SQLException {
        Server.createTcpServer("-tcpPort", "9092", "-tcpAllowOthers").start();
    }

    /**
     *
     * @throws SQLException
     */
    public static void stopDb() throws SQLException {
        Server.shutdownTcpServer("tcp://localhost:9092", "", true, true);
    }


    /**
     * Metodo para recrear las tablas necesarios
     * @throws SQLException
     */
    public static void crearTablas() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS ESTUDIANTE\n" +
                "(\n" +
                "  MATRICULA INTEGER PRIMARY KEY NOT NULL,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  APELLIDO VARCHAR(100) NOT NULL,\n" +
                "  TELEFONO VARCHAR(25) NOT NULL,\n" +
                "  CARRERA VARCHAR(50) NOT NULL\n" +
                ");";
        Connection con = DataBaseServices.getInstancia().getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }

}
