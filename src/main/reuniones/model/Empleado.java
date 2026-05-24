package reuniones.model;

import reuniones.model.exception.EmailNoValidoException;
import reuniones.model.exception.EmpleadoNoValidoException;

/**
 * Clase que representa a un empleado.
 * Un empleado puede ser organizador o invitado a una reunión.
 */
public class Empleado implements Invitable {

    /** Identificador único del empleado. */
    private String id;
    /** Apellidos del empleado. */
    private String apellidos;
    /** Nombre del empleado. */
    private String nombre;
    /** Correo electrónico del empleado. */
    private String correo;

    /**
     * Construye un empleado con los datos básicos.
     *
     * @param id        identificador único
     * @param nombre    nombre del empleado (solo letras)
     * @param apellidos apellidos del empleado (solo letras)
     * @param correo    correo electrónico
     * @throws EmpleadoNoValidoException si nombre o apellidos son nulos o inválidos
     * @throws EmailNoValidoException    si el correo es nulo o inválido
     */
    public Empleado(String id, String nombre, String apellidos, String correo) {
        this.id = id;
        // Validación de nombre y apellidos
        if (nombre == null) {
            throw new EmpleadoNoValidoException("El nombre no puede ser nulo");
        }
        if (!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {
            throw new EmpleadoNoValidoException("Nombre inválido: '" + nombre + "'. Solo se permiten letras.");
        }
        if (apellidos == null) {
            throw new EmpleadoNoValidoException("El apellido no puede ser nulo");
        }
        if (!apellidos.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ ]+")) {
            throw new EmpleadoNoValidoException("Apellido inválido: '" + apellidos + "'. Solo se permiten letras.");
        }
        this.nombre = nombre;
        this.apellidos = apellidos;
        setCorreo(correo);
    }

    /**
     * Implementa el método de la interfaz Invitable.
     * Envía una invitación al empleado (simulado por consola).
     */
    @Override
    public void invitar() {
        System.out.println("Enviando invitación a través de la consola al empleado: " + getNombreCompleto() + " (" + correo + ")");
    }

    /**
     * Devuelve el nombre completo del empleado.
     * @return nombre y apellidos concatenados
     */
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellidos;
    }

    // ---- Getters y Setters ----

    /** @return identificador del empleado */
    public String getId() { return id; }
    /** @param id nuevo identificador */
    public void setId(String id) { this.id = id; }

    /** @return nombre del empleado */
    public String getNombre() { return nombre; }
    /** @param nombre nuevo nombre */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** @return apellidos del empleado */
    public String getApellidos() { return apellidos; }
    /** @param apellidos nuevos apellidos */
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    /** @return correo electrónico */
    public String getCorreo() { return correo; }
    /**
     * Asigna el correo electrónico del empleado.
     * @param correo nuevo correo
     * @throws EmailNoValidoException si el correo es nulo
     */
    public void setCorreo(String correo) {
        if (correo == null) {
            throw new EmailNoValidoException("El correo no puede ser nulo");
        }
        this.correo = correo;
    }

    /**
     * Representación textual del empleado.
     * @return descripción con id, nombre completo y correo
     */
    @Override
    public String toString() {
        return "Empleado{id='" + id + '\'' + ", nombreCompleto='" + getNombreCompleto() + "', correo='" + correo + "'}";
    }
}
