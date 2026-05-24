import reuniones.model.exception.EmpleadoNoValidoException;
import reuniones.model.Empleado;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmpleadoNoValidoExceptionTest {

    @Test
    void testNombreConNumeros() {
        assertThrows(EmpleadoNoValidoException.class, () ->
            new Empleado("E001", "García", "Ana123", "ana@empresa.com"));
    }

    @Test
    void testNombreConCaracteresEspeciales() {
        assertThrows(EmpleadoNoValidoException.class, () ->
            new Empleado("E001", "García", "Ana@#!", "ana@empresa.com"));
    }

    @Test
    void testApellidoConNumeros() {
        assertThrows(EmpleadoNoValidoException.class, () ->
            new Empleado("E001", "Garc1a", "Ana", "ana@empresa.com"));
    }

    @Test
    void testApellidoConCaracteresEspeciales() {
        assertThrows(EmpleadoNoValidoException.class, () ->
            new Empleado("E001", "García!!!", "Ana", "ana@empresa.com"));
    }

    @Test
    void testEmpleadoValidoConTildes() {
        assertDoesNotThrow(() ->
            new Empleado("E001", "García", "José", "jose@empresa.com"));
    }

    @Test
    void testEmpleadoValidoConEnie() {
        assertDoesNotThrow(() ->
            new Empleado("E001", "Muñoz", "Ana", "ana@empresa.com"));
    }
}