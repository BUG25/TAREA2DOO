package reuniones.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reuniones.model.Asistencia;
import reuniones.model.Empleado;
import reuniones.model.InvitadoExterno;
import reuniones.model.Retraso;

import java.time.Instant;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class RetrasoTest {
    private Empleado empleado;
    private InvitadoExterno invitadoExterno;
    private Instant horaFija;
    private Retraso retrasoEmpleado;
    private Retraso retrasoInvitado;
    @BeforeEach
    void setUp() {
        empleado = new Empleado("001", "Juan", "Perez", "juanperez@empresa.cl");
        invitadoExterno = new InvitadoExterno("Ana", "Martínez", "anamartinez@externo.cl");
        horaFija = Instant.parse("2025-05-23T10:15:00Z");
        retrasoEmpleado = new Retraso(empleado, horaFija);
        retrasoInvitado = new Retraso(invitadoExterno, horaFija);
    }
    @Test
    @DisplayName("retraso con hora para externo")
    void testRetrasoInvitado() {
        Instant antes = Instant.now();
        Retraso retraso = new Retraso(invitadoExterno);
        Instant despues = Instant.now();

        assertNotNull(retraso);
        assertEquals(invitadoExterno, retraso.getParticipante());
        assertTrue(retraso.tieneRetraso());
        assertNotNull(retraso.getHora());
    }
    @Test
    @DisplayName("retraso con hora específica para empleado")
    void testRetrasoEspecifica() {
        assertNotNull(retrasoEmpleado);
        assertEquals(empleado, retrasoEmpleado.getParticipante());
        assertEquals(horaFija, retrasoEmpleado.getHora());
        assertTrue(retrasoEmpleado.tieneRetraso());
    }
    @Test
    @DisplayName("tieneRetraso devuelve true para retraso")
    void testTieneRetrasoDevuelveTrue() {
        assertTrue(retrasoEmpleado.tieneRetraso());
        assertTrue(retrasoInvitado.tieneRetraso());
    }
    @Test
    @DisplayName(" información del retraso")
    void testToStringConEmpleado() {
        String toString = retrasoEmpleado.toString();

        assertTrue(toString.contains("Retraso"));
        assertTrue(toString.contains("participante=" + empleado.toString()));
        assertTrue(toString.contains("horaLlegada=" + horaFija.toString()));
    }
    @Test
    @DisplayName("Mezclar Asistencia y Retraso en una lista")
    void testListaMixtaAsistencias() {
        java.util.List<Asistencia> asistencias = new java.util.ArrayList<>();

        asistencias.add(new Asistencia(empleado));
        asistencias.add(new Retraso(empleado, horaFija));
        asistencias.add(new Asistencia(invitadoExterno));
        asistencias.add(new Retraso(invitadoExterno, horaFija));

        assertEquals(4, asistencias.size());

        assertFalse(asistencias.get(0).tieneRetraso());
        assertTrue(asistencias.get(1).tieneRetraso());
        assertFalse(asistencias.get(2).tieneRetraso());
        assertTrue(asistencias.get(3).tieneRetraso());
    }
}