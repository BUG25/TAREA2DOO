package com.reuniones.model;
import java.time.Instant;
public class Invitacion {/** clase que representa la invitacion enviada a una entidad (empleado, departamento o invitado externo) para asistir a una reunion
                         * almacena al participante y la hora de emision */
  private Instant hora;
  private Invitable invitado;

  public Invitacion(Invitable invitado){/** constructor para generar una invitacion 
                                        * @param invitado (elemento de tipo invitable) */
    this.invitado = invitado;
    this.hora = Instant.now();}

  public Invitacion(Invitable invitado, Instant hora){/** constructor que especifica la hora de invitacion
                                                      * @param invitado (elemento de tipo invitable) 
                                                      * @param hora (instante en que se emite la invitacion) */
    this.invitado = invitado;
    this.hora = hora;}

  /** Getters y Setters */
  public Instant getHora(){
    return hora;}
  public void setHora(Instant hora){
    this.hora = hora;}
  public Invitable getInvitado(){
    return invitado;}
  public void setInvitado(invitable invitado){
    this.invitado = invitado;}

@Override
public String toString() {
        return "Invitacion{" + "hora=" + hora + ", invitado=" + invitado + '}';
    }
}
