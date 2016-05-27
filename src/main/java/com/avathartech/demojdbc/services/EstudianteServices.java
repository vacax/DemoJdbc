package com.avathartech.demojdbc.services;

import com.avathartech.demojdbc.encapsulacion.Estudiante;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase para encapsular los metodos de CRUD
 * Created by vacax on 30/04/16.
 */
public class EstudianteServices {

    private String URL = "jdbc:h2:tcp://localhost/~/pruebaTep";

    public EstudianteServices() {
        registrarDriver();
    }

    /**
     * Metodo para el registro de driver de conexión.
     */
    private void registrarDriver() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Connection getConexion() {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL, "sa", "");
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        }
        return con;
    }

    public void testConexion() {
        try {
            getConexion().close();
            System.out.println("Conexión realizado con exito...");
        } catch (SQLException ex) {
            Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *
     * @throws SQLException
     */
    public void crearTabla() throws  SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS ESTUDIANTE\n" +
                "(\n" +
                "  MATRICULA INTEGER PRIMARY KEY NOT NULL,\n" +
                "  NOMBRE VARCHAR(100) NOT NULL,\n" +
                "  APELLIDO VARCHAR(100) NOT NULL,\n" +
                "  TELEFONO VARCHAR(25) NOT NULL,\n" +
                "  CARRERA VARCHAR(50) NOT NULL\n" +
                ");";
        Connection con = getConexion();
        Statement statement = con.createStatement();
        statement.execute(sql);
        statement.close();
        con.close();
    }

    public List<Estudiante> listaEstudiantes() {
        List<Estudiante> lista = new ArrayList<>();
        Connection con = null;
        try {

            String query = "select * from estudiante";
            con = getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                Estudiante est = new Estudiante();
                est.setMatricula(rs.getInt("matricula"));
                est.setNombre(rs.getString("nombre"));
                est.setApellido(rs.getString("apellido"));
                est.setCarrera(rs.getString("carrera"));
                est.setTelefono(rs.getString("telefono"));

                lista.add(est);
            }

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return lista;
    }

    /**
     *
     * @param matricula
     * @return
     */
    public Estudiante getEstudiante(int matricula) {
        Estudiante est = null;
        Connection con = null;
        try {

            String query = "select * from estudiante where matricula = ?";
            con = getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, matricula);
            //Ejecuto...
            ResultSet rs = prepareStatement.executeQuery();
            while(rs.next()){
                est = new Estudiante();
                est.setMatricula(rs.getInt("matricula"));
                est.setNombre(rs.getString("nombre"));
                est.setApellido(rs.getString("apellido"));
                est.setCarrera(rs.getString("carrera"));
                est.setTelefono(rs.getString("telefono"));

            }

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return est;
    }

    public boolean crearEstudiante(Estudiante est){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "insert into estudiante(matricula, nombre, apellido, telefono, carrera) values(?,?,?,?,?)";
            con = getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setInt(1, est.getMatricula());
            prepareStatement.setString(2, est.getNombre());
            prepareStatement.setString(3, est.getApellido());
            prepareStatement.setString(4, est.getTelefono());
            prepareStatement.setString(5, est.getCarrera());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public boolean actualizarEstudiante(Estudiante est){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "update estudiante set nombre=?, apellido=?, carrera=?, telefono=? where matricula = ?";
            con = getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);
            //Antes de ejecutar seteo los parametros.
            prepareStatement.setString(1, est.getNombre());
            prepareStatement.setString(2, est.getApellido());
            prepareStatement.setString(4, est.getTelefono());
            prepareStatement.setString(3, est.getCarrera());
            //Indica el where...
            prepareStatement.setInt(5, est.getMatricula());
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

    public boolean borrarEstudiante(int matricula){
        boolean ok =false;

        Connection con = null;
        try {

            String query = "delete from estudiante where matricula = ?";
            con = getConexion();
            //
            PreparedStatement prepareStatement = con.prepareStatement(query);

            //Indica el where...
            prepareStatement.setInt(1, matricula);
            //
            int fila = prepareStatement.executeUpdate();
            ok = fila > 0 ;

        } catch (SQLException ex) {
            Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                con.close();
            } catch (SQLException ex) {
                Logger.getLogger(EstudianteServices.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return ok;
    }

}
