package reuniones.model.exception;

/**
 * Excepción lanzada cuando se intenta registrar la asistencia o el retraso
 * de un participante que no estaba en la lista de invitados de la reunión.
 */
public class InvitadoNoValidoException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param mensaje descripción del error
     */
    public InvitadoNoValidoException(String mensaje) {
        super(mensaje);
    }
}
