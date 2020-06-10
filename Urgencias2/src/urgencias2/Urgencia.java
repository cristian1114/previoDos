package urgencias2;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import urgencias2.Paciente;
import urgencias2.Medico;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author johan leon
 * 
 */
public class Urgencia {
    
    private LinkedList<Medico> medicosEnGuardia =  new LinkedList();
    private PriorityQueue<Paciente> pacientesEnEspera = new PriorityQueue();
    private LinkedList<Paciente> pacientesAsegurados;

    public Urgencia() {
        this.pacientesAsegurados = new LinkedList<>();
    }
    
    public boolean cargarMedicos(File file){
        try{
            FileReader f = new FileReader(file);
            BufferedReader br = new BufferedReader(f);
            LinkedList<Medico> lista = new LinkedList<>();
            String linea = br.readLine();
            while(linea != null){
                String [] datos = linea.split(",");
                lista.add(new Medico(datos[0], Boolean.valueOf(datos[1])));
                linea = br.readLine();
            }
            crearTurno(lista);
                return true;
            }catch(IOException e){
                return false;
            }
    }

    public LinkedList<Medico> getMedicosEnGuardia() {
        return medicosEnGuardia;
    }

    public void setMedicosEnGuardia(LinkedList<Medico> medicosEnGuardia) {
        this.medicosEnGuardia = medicosEnGuardia;
    }

    public PriorityQueue<Paciente> getPacientesEnEspera() {
        return pacientesEnEspera;
    }

    public void setPacientesEnEspera(PriorityQueue<Paciente> pacientesEnEspera) {
        this.pacientesEnEspera = pacientesEnEspera;
    }

    public LinkedList<Paciente> getPacientesAsegurados() {
        return pacientesAsegurados;
    }

    public void setPacientesAsegurados(LinkedList<Paciente> pacientesAsegurados) {
        this.pacientesAsegurados = pacientesAsegurados;
    }
    
    
    public boolean cargarAsegurados(File file){
        try{
            FileReader f = new FileReader(file);
            BufferedReader br = new BufferedReader(f);
            String linea = br.readLine();
            while(linea != null){
                String [] datos = linea.split(",");
                this.pacientesAsegurados.add(new Paciente(datos[0],Integer.parseInt(datos[1]),datos[2]));
                linea = br.readLine();
            }return true;
            }catch(IOException e){
                return false;
            }
       
    }
    
    public void crearTurno(LinkedList<Medico> medicos){
        for(Medico m: medicos){
            if(m.isDisponible()){
                medicosEnGuardia.add(m);
            } 
        }
    }
    
    public boolean insertarPaciente(String urgencias, Paciente paciente, int gradoEnfermedad){
        
        for(Paciente p : pacientesAsegurados){
            if(p.getNumeroAsegurado() == paciente.getNumeroAsegurado()){
                 paciente.setGradoEnfermedad(gradoEnfermedad);
            paciente.setUrgencia(urgencias);
            pacientesEnEspera.add(paciente);
            return true;
            } 
        }
        return false;
        
}       
    public boolean atenderPaciente(){
          boolean esta = false;
          Paciente p = null;
          if(!pacientesEnEspera.isEmpty()){
            p = pacientesEnEspera.poll();
            for(Medico m :  medicosEnGuardia){
                if(m.getNombre().equals(p.getNombreMedicoHabitual())){
                    m.agregarPaciente(p);
                    esta = true;
                }  
            }
          }
          if(!esta){
              medicosEnGuardia.get(0).agregarPaciente(p);
          }
          return p != null;
}         
    public boolean idRepetida(int numeroAsegurado){
        
        for(Paciente p : pacientesEnEspera){
            if(p.getNumeroAsegurado() == numeroAsegurado){
             return true;   
            }
        }
        return false;
    }
    public String listarTurno(int pos){
        String imprimir = "";
        Medico m = this.getMedicosEnGuardia().get(pos);
          imprimir += "\nEl medico " + m.getNombre() + " ha atendido a: " + m.pacientesAtendidos()
           + " numero de pacientes = "+ m.getNumeroPacientesAtendidos() + "\n paciente actual: " + m.pacienteActual();
         return imprimir;
    }
    
}
        

