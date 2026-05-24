package reuniones.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reuniones.model.Asistencia;
import reuniones.model.Empleado;
import reuniones.model.InvitadoExterno;
import static org.junit.jupiter.api.Assertions.*;


class AsistenciaTest {
    private Empleado empleado;
    private InvitadoExterno invitadoExterno;
    private Asistencia asistenciaEmpleado;
    private Asistencia asistenciaInvitado;

    @BeforeEach
    void setUp() {
        empleado = new Empleado("E001", "Juan", "Pérez", "juan.perez@empresa.com");
        invitadoExterno = new InvitadoExterno("Ana", "Martínez", "ana.martinez@externo.com");
        asistenciaEmpleado = new Asistencia(empleado);
        asistenciaInvitado = new Asistencia(invitadoExterno);
    }
    @Test
    @DisplayName("Crear asistencia con empleado válido")
    void testAsistenciaEmpleado() {
        assertNotNull(asistenciaEmpleado);
        assertEquals(empleado, asistenciaEmpleado.getParticipante());
        assertFalse(asistenciaEmpleado.tieneRetraso());
    }
    @Test
    @DisplayName("Crear asistencia con invitado externo válido")
    void testAsistenciaExterno() {
        assertNotNull(asistenciaInvitado);
        assertEquals(invitadoExterno, asistenciaInvitado.getParticipante());
        assertFalse(asistenciaInvitado.tieneRetraso());
    }
    @Test
    @DisplayName("Crear asistencia con participante nulo")
    void testCrearAsistenciaConParticipanteNulo() {
        Asistencia asistencia = new Asistencia(null);
        assertNotNull(asistencia);
        assertNull(asistencia.getParticipante());
        assertFalse(asistencia.tieneRetraso());
    }
    @Test
    @DisplayName("asistencia normal sin retraso")
    void testTieneRetrasoDevuelveFalse() {
        assertFalse(asistenciaEmpleado.tieneRetraso());
        assertFalse(asistenciaInvitado.tieneRetraso());
    }
    @Test
    @DisplayName("tieneRetraso siempre independientemente del tipo de participante")
    void testTieneRetrasoSiempreFalse() {
        // Probamos con diferentes tipos de participantes
        Asistencia asistencia1 = new Asistencia(empleado);
        Asistencia asistencia2 = new Asistencia(invitadoExterno);
        Asistencia asistencia3 = new Asistencia(null);

        assertFalse(asistencia1.tieneRetraso());
        assertFalse(asistencia2.tieneRetraso());
        assertFalse(asistencia3.tieneRetraso());
    }

}