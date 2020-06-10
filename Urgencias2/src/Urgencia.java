
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
    int contador = 0;
    List<Medico> medicosEnGuardia =  new LinkedList();
    PriorityQueue<Paciente> pacientesEnEspera = new PriorityQueue();
    SalaDeEspera espera = new SalaDeEspera();
    LinkedList<Paciente> pacientesAsegurados;

    public Urgencia(LinkedList<Medico> medicos, LinkedList<Paciente> pacientesAsegurados) {
        this.pacientesAsegurados = pacientesAsegurados;
        crearTurno(medicos);
    }
    
    
    public void crearTurno(LinkedList<Medico> medicos){
        
        for(Medico m: medicos){
            if(m.isDisponible()){
                medicosEnGuardia.add(m);
            } 
        }
        
        
       for(int i = 0; i < medicosEnGuardia.size(); i++){
           System.out.println("Medicos en turno: ");
           System.out.println(medicosEnGuardia.get(i).getNombre());
       } 
       
       for(Paciente p: pacientesAsegurados){
           
          System.out.println("nombre del asegurado: "+ p.getNombre()
                  + " y su medico es : " + p.getMedicoHabitual().getNombre());
           
       }
       
    }
    
    public boolean insertarPaciente(String urgencias,Paciente paciente, int gradoEnfermedad){
        
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
    public void atenderPaciente(){
       
          boolean esta = false;
            

          Paciente p = pacientesEnEspera.poll();
          Medico d = p.getMedicoHabitual();
          for(Medico m :  medicosEnGuardia){
              if(m.getNombre().equals(d.getNombre())){
                  m.agregarPaciente(p);
                  esta = true;
              }  
          }
          if(!esta){
              medicosEnGuardia.get(0).agregarPaciente(p);
          }
       
                
}         
    public String listarTurno(LinkedList<Medico> medicos){
        String imprimir = "";
         for(Medico m : medicosEnGuardia){
          imprimir += "\nEl medioco " + m.getNombre() + " ha atendido a: " + m.pacientesAtendidos()
           + " numero de pacientes = "+ m.getNumeroPacientesAtendidos() + "\n paciente actual: " + m.pacienteActual();
        }
         return imprimir;
    }
    
}
        

