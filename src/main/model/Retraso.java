package com.reuniones.model;
import java.time.Instants;
public class Retraso extends Asistencia{/** clase que representa una asistencia con retraso
                                        * hereda de asistencia y añade la hora exacta de llegada*/
  private Instant hora;
  
  public Retraso(Invitable partcipante){/** constructor que registra un retraso con hora actaul de llegada
                                        * @param participante que llego tarde (empleado o invitado externo) */
    super(participante);
    this.hora = Instant.now();
  }
  public Retraso(Invitable participante, Instant hora){/** constructor que especifica la hora de llegada exacta del atraso
                                                      * @param participante (empleado o invitado externo)
                                                      * @param hora precisa de llegada */
    super(participante);
    this.hora = hora;
  }
  /** sobreescribe elmetodo para indicar que la asistencia si es un retraso 
  * @return true siempre */
  
  @Override
  public boolean tieneRetraso(){
    return true;}
  /** Getters y Setters */
  public Instant getHora(){
    return hora;}
  public void setHora(Instant hora){
      this.hora = hora;}
  @Override
  public String toString() {
        return "Retraso{" + "participante=" + getParticipante() + ", horaLlegada=" + hora + '}';
    }
}
  
  
  
