package urgencias2;


import java.util.LinkedList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author johan leon
 */
public class Medico {
    private String nombre;
    private boolean disponible;
    private LinkedList<Paciente> pacientesAtendidos;

    public LinkedList<Paciente> getPacientesAtendidos() {
        return pacientesAtendidos;
    }

    public void setPacientesAtendidos(LinkedList<Paciente> pacientesAtendidos) {
        this.pacientesAtendidos = pacientesAtendidos;
    }

    public String pacientesAtendidos(){
        String pacientes = "";
        for(Paciente p : pacientesAtendidos){
            pacientes += p.getNombre() + ", ";
        }
        return pacientes + "\n";
    }
    
    public int getNumeroPacientesAtendidos(){
        return pacientesAtendidos.size();
    }

    public Medico(String nombre, boolean disponible) {
        this.nombre = nombre;
        this.disponible = disponible;
        this.pacientesAtendidos = new LinkedList();
    }  
    
    public void agregarPaciente(Paciente p){
        pacientesAtendidos.add(p);
    }
    
    public String pacienteActual(){
        if(pacientesAtendidos.isEmpty()){
            return "no tiene pacientes atendidos";
        }
        return pacientesAtendidos.get(0).getNombre();
    }

    public Medico() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }
}
