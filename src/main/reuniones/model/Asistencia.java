package reuniones.model;
public class Asistencia {/** Clase que representa la asistencia de un participante en una reunión
                         * sirve como clase base para registrar asistencias comunes */
    private Invitable participante; // Usamos Invitable para permitir tanto a empleados como invitados externos
  
    public Asistencia(Invitable participante) {/** Constructor para registrar una asistencia comun a tiempo
                                               * @param participante (empleado o invitado externo) presente */
        this.participante = participante;
    }

    public boolean tieneRetraso() {/** Metodo para comprobar si esta asistencia es un retraso
                                   * @return false por defecto para una asistencia comun */
        return false;}

    /** Getters y Setters */
    public Invitable getParticipante() {
        return participante;}
    public void setParticipante(Invitable participante) {
        this.participante = participante;}
    public boolean getRetraso() {
        return tieneRetraso();}

    @Override
    public String toString() {
        return "Asistencia{" + "participante=" + participante + ", tipo=A tiempo" + '}';
    }


}
