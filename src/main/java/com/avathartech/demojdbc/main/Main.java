package com.avathartech.demojdbc.main;

import com.avathartech.demojdbc.encapsulacion.Estudiante;
import com.avathartech.demojdbc.services.EstudianteServices;

import java.util.List;


/**
 * Created by vacax on 30/04/16.
 */
public class Main {

    public static void main(String[] args) {


        EstudianteServices estudianteServices = new EstudianteServices();
        estudianteServices.testConexion();

        List<Estudiante> listaEstudiantes = estudianteServices.listaEstudiantes();
        System.out.println("La cantidad de estudiantes: "+listaEstudiantes.size());
        for(Estudiante est : listaEstudiantes){
            System.out.println("La matricula: "+est.getMatricula());
        }

        Estudiante estudiante = estudianteServices.getEstudiante(200111136);
        if(estudiante!=null){
            System.out.println("El nombre es: "+estudiante.getNombre());
        }else{
            System.out.println("No exite el usuario consultado");
        }

        //Insertando
        Estudiante insertar = new Estudiante();
        insertar.setMatricula(99999);
        insertar.setNombre("Otro");
        insertar.setApellido("Otro");
        insertar.setTelefono("4545");
        insertar.setCarrera("ISC");
        if(estudianteServices.getEstudiante(insertar.getMatricula())==null){
            estudianteServices.crearEstudiante(insertar);
        }

        //Actualizando
        insertar.setNombre("Cambiando el nombre.....");
        estudianteServices.actualizarEstudiante(insertar);

        //Eleminando...
        estudianteServices.borrarEstudiante(insertar.getMatricula());
    }
}
