package com.reuniones.exception;

/**
 * Excepción lanzada cuando se intenta realizar una operación sobre una reunión
 * que aún no ha sido iniciada.
 */
public class ReunionNoIniciadaException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param mensaje descripción del error
     */
    public ReunionNoIniciadaException(String mensaje) {
        super(mensaje);
    }
}
