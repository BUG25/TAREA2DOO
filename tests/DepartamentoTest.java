package reuniones.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import reuniones.model.Departamento;
import reuniones.model.Empleado;
import static org.junit.jupiter.api.Assertions.*;

class DepartamentoTest {
    private Departamento departamento;
    private Empleado empleado1;
    private Empleado empleado2;
    private Empleado empleado3;
    @BeforeEach
    void setUp() {
        departamento = new Departamento("Finanzas");
        empleado1 = new Empleado("001", "Juan", "Perez", "juanperez@empresa.cl");
        empleado2 = new Empleado("002", "Maria", "Mercedes", "mariamercedes@empresa.cl");
        empleado3 = new Empleado("003", "Felipe", "Aguila", "felipeaguila@empresa.cl");
    }
    @Test
    @DisplayName("Crear un departamento valido")
    void departamentoValido() {
        Departamento newDepto = new Departamento("Marketing");
        assertNotNull(newDepto);
        assertEquals("Marketing", newDepto.getNombre());
        assertEquals(0, newDepto.obtenerCantidadEmpleados());
    }
    @Test
    @DisplayName("Agregar empleado")
    void testAgregarEmpleado() {
        departamento.agregarEmpleado(empleado1);

        assertEquals(1, departamento.obtenerCantidadEmpleados());
    }
    @Test
    @DisplayName("Agregar mas de un empleado")
    void testMultiplesEmpleados() {
        departamento.agregarEmpleado(empleado1);
        departamento.agregarEmpleado(empleado2);
        departamento.agregarEmpleado(empleado3);

        assertEquals(3, departamento.obtenerCantidadEmpleados());
    }
    @Test
    @DisplayName("cantidad de empleados cuando no hay")
    void testDepartamentoVacio() {
        assertEquals(0, departamento.obtenerCantidadEmpleados());
    }
    @Test
    @DisplayName("Agregar el mismo empleado")
    void testRepetidos() {
        departamento.agregarEmpleado(empleado1);
        departamento.agregarEmpleado(empleado1);
        departamento.agregarEmpleado(empleado1);
        assertEquals(1, departamento.obtenerCantidadEmpleados());
    }

}


