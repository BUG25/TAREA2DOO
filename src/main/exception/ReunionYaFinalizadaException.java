package com.reuniones.exception;

/**
 * Excepción lanzada cuando se intenta realizar una operación sobre una reunión
 * que ya ha sido finalizada.
 */
public class ReunionYaFinalizadaException extends RuntimeException {

    /**
     * Construye la excepción con un mensaje descriptivo.
     *
     * @param mensaje descripción del error
     */
    public ReunionYaFinalizadaException(String mensaje) {
        super(mensaje);
    }
}
