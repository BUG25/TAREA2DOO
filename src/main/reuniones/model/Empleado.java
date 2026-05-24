package com.reuniones.model;

import com.reuniones.exception.EmailNoValidoException;

public class Empleado implements Invitable {

    private String id;

    private String apellidos;

    private String nombre;

    private String correo;

    private Departamento departamento;

    /**
     * Construye un empleado con sus datos básicos.
     *
     * @param id        identificador único
     * @param apellidos apellidos del empleado
     * @param nombre    nombre del empleado
     * @param correo    dirección de correo electrónico
     * @throws EmailNoValidoException si el correo no tiene formato válido
     */
    public Empleado(String id, String apellidos, String nombre, String correo) {
        this.id = id;
        this.apellidos = apellidos;
        this.nombre = nombre;
        setCorreo(correo);
    }

    /**
     * Invita a este empleado a la reunión indicada añadiéndolo como invitado.
     *
     * @param reunion la reunión a la que se invita al empleado
     */
    @Override
    public void invitar(Reunion reunion) {
        Invitacion invitacion = new Invitacion(this);
        reunion.agregarInvitacion(invitacion);
        System.out.println("Invitación enviada a: " + correo + " para la reunión del " + reunion.getFecha());
    }

    /**
     * Devuelve el nombre completo del empleado (nombre + apellidos).
     *
     * @return nombre completo
     */
    public String getNombreCompleto() {
        return nombre + " " + apellidos;
    }

    /** @return identificador del empleado */
    public String getId() { return id; }

    /** @param id nuevo identificador */
    public void setId(String id) { this.id = id; }

    /** @return apellidos del empleado */
    public String getApellidos() { return apellidos; }

    /** @param apellidos nuevos apellidos */
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    /** @return nombre del empleado */
    public String getNombre() { return nombre; }

    /** @param nombre nuevo nombre */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return correo electrónico */
    public String getCorreo() { return correo; }

    /**
     * Establece el correo electrónico validando su formato.
     *
     * @param correo nuevo correo electrónico
     * @throws EmailNoValidoException si el correo no contiene '@' o no tiene dominio válido
     */
    public void setCorreo(String correo) {
        if (!esEmailValido(correo)) {
            throw new EmailNoValidoException("Correo inválido para el empleado '" + nombre + "': " + correo);
        }
        this.correo = correo;
    }

    /** @return departamento al que pertenece */
    public Departamento getDepartamento() { return departamento; }

    /** @param departamento departamento al que asignar */
    public void setDepartamento(Departamento departamento) { this.departamento = departamento; }

    /**
     * Valida que el correo tenga el formato básico usuario@dominio.ext
     *
     * @param correo cadena a validar
     * @return {@code true} si el formato es válido
     */
    private boolean esEmailValido(String correo) {
        if (correo == null || correo.isBlank()) return false;
        return correo.matches("^[\\w.+\\-]+@[\\w\\-]+\\.[a-zA-Z]{2,}$");
    }

    @Override
    public String toString() {
        return "Empleado{id='" + id + "', nombre='" + getNombreCompleto() + "', correo='" + correo + "'}";
    }
}
