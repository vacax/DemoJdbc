package com.avathartech.demojdbc.main;

import com.avathartech.demojdbc.encapsulacion.Estudiante;
import com.avathartech.demojdbc.services.BootStrapServices;
import com.avathartech.demojdbc.services.DataBaseServices;
import com.avathartech.demojdbc.services.EstudianteServices;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by vacax on 30/04/16.
 */
public class Main {

    public static void main(String[] args) throws SQLException {

        //Iniciando el servicio
        BootStrapServices.startDb();

        //Prueba de Conexión.
        DataBaseServices.getInstancia().testConexion();

        BootStrapServices.crearTablas();
        //
        EstudianteServices estudianteServices = new EstudianteServices();

        //Insertando
        Estudiante insertar = new Estudiante();
        insertar.setMatricula(20011136);
        insertar.setNombre("Carlos");
        insertar.setApellido("Camacho");
        insertar.setTelefono("849-220-6409");
        insertar.setCarrera("ISC");
        if(estudianteServices.getEstudiante(insertar.getMatricula())==null){
            estudianteServices.crearEstudiante(insertar);
        }

        List<Estudiante> listaEstudiantes = estudianteServices.listaEstudiantes();
        System.out.println("La cantidad de estudiantes: "+listaEstudiantes.size());
        for(Estudiante est : listaEstudiantes){
            System.out.println("La matricula: "+est.getMatricula());
        }

        Estudiante estudiante = estudianteServices.getEstudiante(20011136);
        if(estudiante!=null){
            System.out.println("El nombre es: "+estudiante.getNombre());
        }else{
            System.out.println("No exite el usuario consultado");
        }

        //Actualizando
        insertar.setNombre("Cambiando el nombre.....");
        estudianteServices.actualizarEstudiante(insertar);

        //Eleminando...
        estudianteServices.borrarEstudiante(insertar.getMatricula());

        //Parando el servicio
        BootStrapServices.stopDb();
    }
}
