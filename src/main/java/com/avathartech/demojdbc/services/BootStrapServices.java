package com.avathartech.demojdbc.services;

import org.h2.tools.Server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Servicio de arranque/parada de la base de datos H2 en modo servidor.
 * Created by vacax on 27/05/16.
 */
public class BootStrapServices {

    /** Host donde escucha el servidor TCP de H2. */
    private static final String TCP_HOST = "localhost";
    /** Puerto TCP por defecto de H2. */
    private static final int TCP_PORT = 9092;
    /** Tiempo máximo (ms) para comprobar si el puerto ya está ocupado. */
    private static final int CHECK_TIMEOUT_MS = 500;

    private static Server server;

    /**
     * Arranca el servidor H2 en modo TCP.
     * <p>
     * Si ya existe otra instancia (externa) de H2 escuchando en el puerto, no se
     * intenta arrancar una nueva: se reutiliza la instancia ya activa. De este modo
     * la aplicación puede subir en modo servidor aunque otra instancia esté arriba.
     */
    public static void startDb() {
        if (isPortInUse(TCP_HOST, TCP_PORT)) {
            System.out.println("Ya existe una instancia de H2 escuchando en " + TCP_HOST + ":" + TCP_PORT
                    + ". Se reutilizará la instancia externa.");
            return;
        }
        try {
            server = Server.createTcpServer("-tcpPort", String.valueOf(TCP_PORT), "-tcpAllowOthers", "-ifNotExists").start();
            System.out.println("Servidor H2 iniciado en " + TCP_HOST + ":" + TCP_PORT + ".");
        } catch (SQLException ex) {
            // El puerto pudo ocuparse entre la comprobación y el arranque (condición de carrera):
            // se asume que hay una instancia externa válida y se continúa reutilizándola.
            System.out.println("No se pudo iniciar el servidor H2; se reutilizará una instancia externa. Detalle: "
                    + ex.getMessage());
            server = null;
        }
    }

    /**
     * Detiene el servidor H2 únicamente si fue arrancado por esta aplicación.
     * Si se está reutilizando una instancia externa, no se detiene.
     */
    public static void stopDb() {
        if (server != null && server.isRunning(false)) {
            server.stop();
            System.out.println("Servidor H2 detenido.");
        }
    }

    /**
     * Comprueba si el puerto indicado ya está aceptando conexiones, lo que indica
     * que existe otra instancia de H2 (u otro servicio) en ejecución.
     *
     * @param host host a comprobar.
     * @param port puerto a comprobar.
     * @return {@code true} si el puerto está ocupado; {@code false} en caso contrario.
     */
    private static boolean isPortInUse(String host, int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), CHECK_TIMEOUT_MS);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }

    /**
     * Crea las tablas necesarias si no existen.
     *
     * @throws SQLException si ocurre un error al ejecutar la sentencia.
     */
    public static void crearTablas() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS ESTUDIANTE\n" +
                "(\n" +
                "  MATRICULA INTEGER PRIMARY KEY NOT NULL,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  APELLIDO VARCHAR(100) NOT NULL,\n" +
                "  TELEFONO VARCHAR(25) NOT NULL,\n" +
                "  CARRERA VARCHAR(50) NOT NULL\n" +
                ");";
        try (Connection con = DataBaseServices.getInstancia().getConexion();
             Statement statement = con.createStatement()) {
            statement.execute(sql);
        }
    }

}
