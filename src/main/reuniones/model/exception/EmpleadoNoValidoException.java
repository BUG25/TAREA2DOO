package reuniones.model.exception;

/**
 * Excepción lanzada cuando los datos de un {@link com.reuniones.model.Empleado}
 * son inválidos (nombre, apellido o correo con caracteres no permitidos).
 */
public class EmpleadoNoValidoException extends RuntimeException {

    /**
     * @param mensaje descripción del campo inválido
     */
    public EmpleadoNoValidoException(String mensaje) {
        super(mensaje);
    }
}