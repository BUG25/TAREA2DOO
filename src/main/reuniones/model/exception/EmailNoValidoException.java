package reuniones.model.exception;

/**
 * Excepción lanzada cuando se proporciona una dirección de correo electrónico
 * con formato inválido al crear un {@link reuniones.model.Empleado}
 * o un {@link reuniones.model.InvitadoExterno}.
 */
public class EmailNoValidoException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param mensaje descripción del error, incluyendo el correo inválido
     */
    public EmailNoValidoException(String mensaje) {
        super(mensaje);
    }
}
