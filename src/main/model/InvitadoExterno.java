package com.reuniones.model;
public class InvitadoExterno implements Invitable { /** clase que representa a un invitado externo
                                                     *implementa la interfaz Invitable para poder ser agregado a las listas de invitaciones y asistencias de cualquier reunión */
    private String nombre;
    private String apellidos;
    private String correo;

    public InvitadoExterno(String nombre, String apellidos, String correo) {/** constructor para registrar un invitado externo con sus datos */
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.correo = correo;
    }
  
    @Override
    public void invitar() { /** implementacion de la interfaz invitable
                            * simula la notificación imprimiendo en la consola el envío al correo externo */
        System.out.println("Enviando invitación por consola al INVITADO EXTERNO: " + getNombreCompleto() + " (" + correo + ")");
    }

    public String getNombreCompleto() {/** obtiene el nombre completo uniendo nombre y apellidos.
                                      * este metodo es importante para que funcione correctamente la clase de servicio InformeReunion
                                      * @return Nombre completo del invitado externo */
        return this.nombre + " " + this.apellidos;
    }

    /** Getters y Setters */
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    @Override
    public String toString() {
        return "InvitadoExterno{" + "nombreCompleto='" + getNombreCompleto() + '\'' + ", correo='" + correo + '\'' + '}';
    }
}
