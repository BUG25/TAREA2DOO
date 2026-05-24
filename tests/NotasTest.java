package reuniones.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reuniones.model.Nota;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class NotasTest {

    private Nota nota;
    private final String CONTENIDO_PRUEBA = "Esta es una nota de prueba";
    private final String CONTENIDO_VACIO = "";
    private final String CONTENIDO_LARGO = "El volcán de Parangaricutirimicuaro lo quieren desparangaricutimirizar. " +
            "El que logre desparangaricutimirizarlo un gran desparangaricutirimizador será.";

    @BeforeEach
    void setUp() {
        nota = new Nota(CONTENIDO_PRUEBA);
    }
    @Test
    @DisplayName("Crear nota con contenido válido")
    void testCrearNotaValida() {
        assertNotNull(nota);
        assertEquals(CONTENIDO_PRUEBA, nota.getContenido());
        assertNotNull(nota.getHoraCreacion());
    }

    @Test
    @DisplayName("Crear nota con contenido vacío")
    void testCrearNotaVacia() {
        Nota notaVacia = new Nota(CONTENIDO_VACIO);

        assertNotNull(notaVacia);
        assertEquals(CONTENIDO_VACIO, notaVacia.getContenido());
        assertNotNull(notaVacia.getHoraCreacion());
    }

    @Test
    @DisplayName("Crear nota con contenido largo")
    void testCrearNotaLarga() {
        Nota notaLarga = new Nota(CONTENIDO_LARGO);

        assertNotNull(notaLarga);
        assertEquals(CONTENIDO_LARGO, notaLarga.getContenido());
        assertNotNull(notaLarga.getHoraCreacion());
    }

    @Test
    @DisplayName("Crear nota con contenido null")
    void testCrearNotaNull() {
        Nota notaNull = new Nota(null);

        assertNotNull(notaNull);
        assertNull(notaNull.getContenido());
        assertNotNull(notaNull.getHoraCreacion());
    }

    @Test
    @DisplayName("La hora de creación se establece automáticamente al crear la nota")
    void testHoraCreacionAutomatica() {
        Instant antes = Instant.now();
        Nota nuevaNota = new Nota("Nota con hora automática");
        Instant despues = Instant.now();
        Instant horaNota = nuevaNota.getHoraCreacion();
        assertNotNull(horaNota);
        assertTrue(horaNota.isAfter(antes) || horaNota.equals(antes));
        assertTrue(horaNota.isBefore(despues) || horaNota.equals(despues));
    }

    @Test
    @DisplayName("Múltiples notas tienen diferentes horas de creación")
    void testMultiplesNotasDiferentesHoras() throws InterruptedException {
        Nota nota1 = new Nota("Primera nota");
        Thread.sleep(10); // Pequeña pausa para asegurar diferencia
        Nota nota2 = new Nota("Segunda nota");
        Thread.sleep(10);
        Nota nota3 = new Nota("Tercera nota");
        assertNotEquals(nota1.getHoraCreacion(), nota2.getHoraCreacion());
        assertNotEquals(nota2.getHoraCreacion(), nota3.getHoraCreacion());
        assertTrue(nota1.getHoraCreacion().isBefore(nota2.getHoraCreacion()));
        assertTrue(nota2.getHoraCreacion().isBefore(nota3.getHoraCreacion()));
    }
    @Test
    @DisplayName("toString muestra contenido y hora de creación")
    void testToString() {
        String resultado = nota.toString();
        assertTrue(resultado.contains("Nota"));
        assertTrue(resultado.contains(CONTENIDO_PRUEBA));
        assertTrue(resultado.contains(nota.getHoraCreacion().toString()));
    }

    @Test
    @DisplayName("toString con contenido null")
    void testToStringNull() {
        Nota notaNull = new Nota(null);
        String resultado = notaNull.toString();

        assertTrue(resultado.contains("Nota"));
        assertTrue(resultado.contains("null"));
        assertTrue(resultado.contains(notaNull.getHoraCreacion().toString()));
    }
    @Test
    @DisplayName("toString con contenido vacío")
    void testToStringVacio() {
        Nota notaVacia = new Nota("");
        String resultado = notaVacia.toString();

        assertTrue(resultado.contains("Nota"));
        assertTrue(resultado.contains("contenido=''"));
    }
    @Test
    @DisplayName("Las notas se pueden ordenar por hora")
    void testOrdenCronologico() throws InterruptedException {
        List<Nota> notas = new ArrayList<>();

        notas.add(new Nota("Nota 1 - primera"));
        Thread.sleep(10);
        notas.add(new Nota("Nota 2 - segunda"));
        Thread.sleep(10);
        notas.add(new Nota("Nota 3 - tercera"));
        notas.sort((n1, n2) -> n1.getHoraCreacion().compareTo(n2.getHoraCreacion()));
        assertTrue(notas.get(0).getHoraCreacion().isBefore(notas.get(1).getHoraCreacion()));
        assertTrue(notas.get(1).getHoraCreacion().isBefore(notas.get(2).getHoraCreacion()));
    }
    @Test
    @DisplayName("Crear múltiples notas independientes")
    void testMultiplesNotas() {
        Nota nota1 = new Nota("Primera nota");
        Nota nota2 = new Nota("Segunda nota");
        Nota nota3 = new Nota("Tercera nota");
        nota1.setContenido("Contenido modificado");

        assertEquals("Contenido modificado", nota1.getContenido());
        assertEquals("Segunda nota", nota2.getContenido());
        assertEquals("Tercera nota", nota3.getContenido());
    }

    @Test
    @DisplayName("La hora de creación no cambia al modificar el contenido")
    void testHoraNoCambia() {
        Instant horaOriginal = nota.getHoraCreacion();
        nota.setContenido("Nuevo contenido");
        assertEquals(horaOriginal, nota.getHoraCreacion());
        assertNotEquals(CONTENIDO_PRUEBA, nota.getContenido());
    }
    @Test
    @DisplayName("El contenido no cambia al modificar la hora")
    void testContenidoNoCambia() {
        String contenidoOriginal = nota.getContenido();
        Instant nuevaHora = Instant.parse("2025-05-23T15:00:00Z");

        nota.setHoraCreacion(nuevaHora);

        assertEquals(contenidoOriginal, nota.getContenido());
        assertEquals(nuevaHora, nota.getHoraCreacion());
    }
}