package com.reuniones.model;
import com.reuniones.model.enums.TipoReunion;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class ReunionPresencial extends Reunion {/** clase que representa una reunión en modalidad presencial
                                                 * añade soporte para especificar la sala física donde se llevará a cabo la reunion */
    private String sala;
  
/** constructor para inicializar una reunión presencial con sus datos y sala */
    public ReunionPresencial(Date fecha, Instant horaPrevista, Duration duracionPrevista, Empleado organizador, TipoReunion tipoReunion, String sala) {
        super(fecha, horaPrevista, duracionPrevista, organizador, tipoReunion);
        this.sala = sala;
    }
  
    /** Getters y Setters */
    public String getSala() {
        return sala;}
    public void setSala(String sala) {
        this.sala = sala;}

    @Override
    public String toString() {
        return super.toString() + " [Modalidad: Presencial, Sala: " + sala + "]";
    }
}
