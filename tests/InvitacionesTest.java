package reuniones.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import reuniones.model.Departamento;
import reuniones.model.Empleado;
import reuniones.model.Invitable;
import reuniones.model.InvitadoExterno;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class InvitacionesTest {
    private Departamento departamento;
    private Empleado empleado1;
    private Empleado empleado2;
    private InvitadoExterno invitadoExterno;
    @BeforeEach
    void setUp() {
        departamento = new Departamento("Finanzas");
        empleado1 = new Empleado("001", "Juan", "Perez", "juanperez@empresa.cl");
        empleado2 = new Empleado("002", "Maria", "Mercedes", "mariamercedes@empresa.cl");
        invitadoExterno = new InvitadoExterno("Ana", "Martinez", "anamartinez@externo.cl");
    }

    @Test
    @DisplayName("Invitar departamento vacio")
    void invitarDepartamentoVacio() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        departamento.invitar();
        String output = outputStream.toString();
        assertTrue(output.contains("Enviando invitación masiva al departamento: Finanzas"));
        assertFalse(output.contains("Enviando invitacion a traves de la consola al empleado"));

        System.setOut(System.out);
    }
    @Test
    @DisplayName("Invitar a departamento completo")
    void testInvitarTodos() {
        departamento.agregarEmpleado(empleado1);
        departamento.agregarEmpleado(empleado2);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        departamento.invitar();

        String output = outputStream.toString();
        assertTrue(output.contains("Enviando invitación masiva al departamento: Finanzas"));
        assertTrue(output.contains("Juan Perez"));
        assertTrue(output.contains("Maria Mercedes"));

        System.setOut(System.out);
    }
    @Test
    @DisplayName("Obtener nombre completo")
    void testGetNombreCompletoValido() {
        String nombreCompleto = invitadoExterno.getNombreCompleto();
        assertEquals("Ana Martinez", nombreCompleto);
    }
    @Test
    @DisplayName("Invitar a invitado externo debe imprimir mensaje en consola")
    void testInvitarImprimeMensaje() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        try {
            invitadoExterno.invitar();
            String output = outputStream.toString();
            assertTrue(output.contains("INVITADO EXTERNO"));
            assertTrue(output.contains("Ana Martinez"));
            assertTrue(output.contains("anamartinez@externo.cl"));
            assertTrue(output.contains("Enviando invitación"));

        } finally {
            System.setOut(originalOut);
        }
    }
    @Test
    @DisplayName("InvitadoExterno implementa correctamente la interfaz Invitable")
    void testImplementaInterfazInvitable() {
        assertTrue(invitadoExterno instanceof Invitable);
    }


}
