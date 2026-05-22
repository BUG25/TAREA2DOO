package com.reuniones.model;
import com.reuniones.model.enums.TipoReunion;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

public class ReunionVirtual extends Reunion {/** clase que representa una reunión en modalidad virtual.
                                             * añade soporte para manejar el enlace para la conexion a la pagina de la reunion virtual */
    private String enlace;
    /** constructor para inicializar una reunión virtual con sus datos y enlace */
    public ReunionVirtual(Date fecha, Instant horaPrevista, Duration duracionPrevista, Empleado organizador, TipoReunion tipoReunion, String enlace) {
        super(fecha, horaPrevista, duracionPrevista, organizador, tipoReunion);
        this.enlace = enlace;
    }

    /** Getters y Setters */
    public String getEnlace() {
        return enlace;}
    public void setEnlace(String enlace) {
        this.enlace = enlace;}

    @Override
    public String toString() {
        return super.toString() + " [Modalidad: Virtual, Enlace: " + enlace + "]";
    }
}
