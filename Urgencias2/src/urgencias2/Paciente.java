package urgencias2;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author johan leon
 */
public class Paciente implements Comparable<Paciente> {
    
    private String nombre;
    private int numeroAsegurado;
    private int gradoEnfermedad;
    private String urgencia;
    private String nombreMedicoHabitual;

    public Paciente(String nombre, int numeroAsegurado, String nombreMedicoHabitual) {
        this.nombre = nombre;
        this.numeroAsegurado = numeroAsegurado;
        this.nombreMedicoHabitual = nombreMedicoHabitual;
    }

    

    public String getNombreMedicoHabitual() {
        return nombreMedicoHabitual;
    }

    public void setNombreMedicoHabitual(String nombreMedicoHabitual) {
        this.nombreMedicoHabitual = nombreMedicoHabitual;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    

    public int getNumeroAsegurado() {
        return numeroAsegurado;
    }

    public void setNumeroAsegurado(int numeroAsegurado) {
        this.numeroAsegurado = numeroAsegurado;
    }

    public int getGradoEnfermedad() {
        return gradoEnfermedad;
    }

    public void setGradoEnfermedad(int gradoEnfermedad) {
        this.gradoEnfermedad = gradoEnfermedad;
    }

    public String getUrgencia() {
        return urgencia;
    }

    public void setUrgencia(String urgencia) {
        this.urgencia = urgencia;
    }

    

    public Paciente() {
    }

    @Override
    public int compareTo(Paciente p) {
       if(p.getGradoEnfermedad() > this.gradoEnfermedad){
           return 1;
       } else if(p.getGradoEnfermedad() < this.gradoEnfermedad){
           return -1;
       } else return 0;
    }
    
    
}
