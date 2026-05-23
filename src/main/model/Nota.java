package com.reuniones.model;
import java.time.Instant;

public class Nota {/** clase que representa nota o apunte tomado durante una reunion
                   * guarda contenido del texto y momento exacto en que fue creaada para permitir su ordenamiento cronologico */
    private String contenido;
    private Instant horaCreacion;

    public Nota(String contenido) {/** constructor para crear una nueva nota
                                  * registra automáticamente el instante actual como hora de creacion
                                  * @param contenido el texto o apunte de la nota */
        this.contenido = contenido;
        this.horaCreacion = Instant.now();
    }
  
    /** Getters y Setters */
    public String getContenido() {
        return contenido;}
    public void setContenido(String contenido) {
        this.contenido = contenido;}

    public Instant getHoraCreacion() {/** obtiene el momento exacto en cual se crea la nota
                                     * este método es importante para el orden en InformeReunion
                                     * @return Instant de la creacion */
        return horaCreacion;}

    public void setHoraCreacion(Instant horaCreacion) {
        this.horaCreacion = horaCreacion;}

    @Override
    public String toString() {
        return "Nota{" + "contenido='" + contenido + '\'' + ", horaCreacion=" + horaCreacion + '}';
    }
}
