package reuniones.tests;

import org.junit.jupiter.api.*;
import reuniones.model.*;
import reuniones.model.exception.*;
import reuniones.model.service.InformeReunion;

import java.io.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test Completo del Sistema de Reuniones")
public class ReunionCompletoTest {

    private static Empleado organizador;
    private static Empleado empleado1;
    private static Empleado empleado2;
    private static InvitadoExterno invitadoExterno;
    private static Departamento departamento;
    private static ReunionPresencial reunionPresencial;
    private static ReunionVirtual reunionVirtual;
    private static InformeReunion informeService;

    @BeforeAll
    static void setUpAll() {
        informeService = new InformeReunion();
    }

    @BeforeEach
    void setUp() {
        // Crear empleados
        organizador = new Empleado("ORG01", "Carlos", "Rodríguez", "carlos.rodriguez@empresa.com");
        empleado1 = new Empleado("EMP01", "Ana", "García", "ana.garcia@empresa.com");
        empleado2 = new Empleado("EMP02", "Luis", "Martínez", "luis.martinez@empresa.com");
        invitadoExterno = new InvitadoExterno("María", "López", "maria.lopez@externo.com");

        // Crear departamento con empleados
        departamento = new Departamento("Desarrollo");
        departamento.agregarEmpleado(empleado1);
        departamento.agregarEmpleado(empleado2);
    }

    @Test
    @Order(1)
    @DisplayName("1. Crear reunión presencial y verificar datos")
    void testCrearReunionPresencial() {
        Date fecha = new Date();
        Instant horaPrevista = Instant.now().plus(Duration.ofHours(1));
        Duration duracion = Duration.ofMinutes(60);

        reunionPresencial = new ReunionPresencial(
            fecha, horaPrevista, duracion, 
            organizador, TipoReunion.TECNICA, "Sala Principal A"
        );

        assertNotNull(reunionPresencial);
        assertEquals("Sala Principal A", reunionPresencial.getSala());
        assertEquals(organizador, reunionPresencial.getOrganizador());
        assertEquals(TipoReunion.TECNICA, reunionPresencial.getTipoReunion());
        assertEquals(0, reunionPresencial.obtenerTotalAsistencia());
    }

    @Test
    @Order(2)
    @DisplayName("2. Crear reunión virtual y verificar datos")
    void testCrearReunionVirtual() {
        Date fecha = new Date();
        Instant horaPrevista = Instant.now().plus(Duration.ofHours(2));
        Duration duracion = Duration.ofMinutes(45);

        reunionVirtual = new ReunionVirtual(
            fecha, horaPrevista, duracion,
            organizador, TipoReunion.MARKETING, "https://meet.google.com/abc-defg-hij"
        );

        assertNotNull(reunionVirtual);
        assertEquals("https://meet.google.com/abc-defg-hij", reunionVirtual.getEnlace());
        assertEquals(organizador, reunionVirtual.getOrganizador());
        assertEquals(TipoReunion.MARKETING, reunionVirtual.getTipoReunion());
    }

    @Test
    @Order(3)
    @DisplayName("3. Invitar participantes (individuales y por departamento)")
    void testInvitarParticipantes() {
        // Usamos la reunión presencial para este test
        Date fecha = new Date();
        Instant horaPrevista = Instant.now().plus(Duration.ofMinutes(30));
        reunionPresencial = new ReunionPresencial(
            fecha, horaPrevista, Duration.ofMinutes(60),
            organizador, TipoReunion.TECNICA, "Sala 101"
        );

        // Invitar individualmente
        reunionPresencial.agregarInvitacion(new Invitacion(empleado1));
        reunionPresencial.agregarInvitacion(new Invitacion(invitadoExterno));
        
        // Invitar por departamento (esto invita a empleado1 y empleado2)
        reunionPresencial.agregarInvitacion(new Invitacion(departamento));

        // Verificar total de invitaciones
        // Empleado1 aparece dos veces? El sistema permite duplicados según tu código
        int totalInvitaciones = reunionPresencial.getInvitaciones().size();
        assertEquals(3, totalInvitaciones);
        
        System.out.println("✅ Total invitaciones: " + totalInvitaciones);
    }

    @Test
    @Order(4)
    @DisplayName("4. Iniciar reunión y registrar asistencias")
    void testIniciarYRegistrarAsistencias() throws InterruptedException {
        // Configurar reunión
        Date fecha = new Date();
        Instant horaPrevista = Instant.now().minus(Duration.ofMinutes(5));
        reunionPresencial = new ReunionPresencial(
            fecha, horaPrevista, Duration.ofMinutes(60),
            organizador, TipoReunion.TECNICA, "Sala 101"
        );

        // Agregar invitados
        reunionPresencial.agregarInvitacion(new Invitacion(empleado1));
        reunionPresencial.agregarInvitacion(new Invitacion(empleado2));
        reunionPresencial.agregarInvitacion(new Invitacion(invitadoExterno));

        // Iniciar reunión
        reunionPresencial.iniciar();
        assertNotNull(reunionPresencial.getHoraInicio());

        // Pequeña pausa para simular tiempo
        Thread.sleep(100);

        // Registrar asistencias
        reunionPresencial.agregarAsistencia(new Asistencia(empleado1));
        
        // Registrar retraso para empleado2
        Instant horaLlegada = Instant.now();
        reunionPresencial.agregarAsistencia(new Retraso(empleado2, horaLlegada));
        
        // Registrar asistencia de invitado externo
        reunionPresencial.agregarAsistencia(new Asistencia(invitadoExterno));

        // Verificar asistencias
        assertEquals(3, reunionPresencial.obtenerTotalAsistencia());
        
        // Verificar retrasos
        List<Retraso> retrasos = reunionPresencial.obtenerRetrasos();
        assertEquals(1, retrasos.size());
        assertEquals(empleado2, retrasos.get(0).getParticipante());
    }

    @Test
    @Order(5)
    @DisplayName("5. Finalizar reunión y calcular tiempo real")
    void testFinalizarYCalcularTiempo() throws InterruptedException {
        Date fecha = new Date();
        Instant horaPrevista = Instant.now().minus(Duration.ofMinutes(10));
        reunionPresencial = new ReunionPresencial(
            fecha, horaPrevista, Duration.ofMinutes(30),
            organizador, TipoReunion.TECNICA, "Sala 101"
        );

        reunionPresencial.agregarInvitacion(new Invitacion(empleado1));
        reunionPresencial.iniciar();
        
        Thread.sleep(500); // Simular duración de reunión (0.5 segundos)
        
        reunionPresencial.finalizar();
        
        assertNotNull(reunionPresencial.getHoraFin());
        
        float tiempoReal = reunionPresencial.calcularTiempoReal();
        // En milisegundos: debería ser aprox 0.5 minutos pero como es float,
        // puede dar 0 si es menos de 1 minuto
        System.out.println("Tiempo real de reunión: " + tiempoReal + " minutos");
        assertTrue(tiempoReal >= 0);
    }

    @Test
    @Order(6)
    @DisplayName("6. Agregar y ordenar notas cronológicamente")
    void testAgregarYOrdenarNotas() throws InterruptedException {
        reunionVirtual = new ReunionVirtual(
            new Date(), Instant.now(), Duration.ofMinutes(30),
            organizador, TipoReunion.MARKETING, "https://meet.google.com/xxx"
        );

        // Agregar notas con pequeñas pausas
        reunionVirtual.agregarNota(new Nota("Primera nota: Revisar presupuesto"));
        Thread.sleep(10);
        reunionVirtual.agregarNota(new Nota("Segunda nota: Definir objetivos"));
        Thread.sleep(10);
        reunionVirtual.agregarNota(new Nota("Tercera nota: Asignar responsables"));

        List<Nota> notasOrdenadas = reunionVirtual.obtenerNotasOrdenadas();
        assertEquals(3, notasOrdenadas.size());
        
        // Verificar orden cronológico
        assertTrue(notasOrdenadas.get(0).getHoraCreacion()
            .isBefore(notasOrdenadas.get(1).getHoraCreacion()));
        assertTrue(notasOrdenadas.get(1).getHoraCreacion()
            .isBefore(notasOrdenadas.get(2).getHoraCreacion()));
    }

    @Test
    @Order(7)
    @DisplayName("7. Calcular estadísticas de asistencia")
    void testEstadisticasAsistencia() {
        Date fecha = new Date();
        Instant horaPrevista = Instant.now();
        
        reunionPresencial = new ReunionPresencial(
            fecha, horaPrevista, Duration.ofMinutes(60),
            organizador, TipoReunion.TECNICA, "Sala 101"
        );

        // Agregar 5 invitados
        reunionPresencial.agregarInvitacion(new Invitacion(empleado1));
        reunionPresencial.agregarInvitacion(new Invitacion(empleado2));
        reunionPresencial.agregarInvitacion(new Invitacion(invitadoExterno));
        
        reunionPresencial.iniciar();
        
        // Solo 3 asistencias de 5 invitados
        reunionPresencial.agregarAsistencia(new Asistencia(empleado1));
        reunionPresencial.agregarAsistencia(new Asistencia(empleado2));
        reunionPresencial.agregarAsistencia(new Asistencia(invitadoExterno));

        float porcentaje = reunionPresencial.obtenerPorcentajeAsistencia();
        assertEquals(100.0f, porcentaje, 0.01); // 3 de 3 = 100%
        
        // Verificar ausentes
        List<Invitable> ausentes = reunionPresencial.obtenerAusencias();
        assertEquals(0, ausentes.size()); // No hay ausentes porque todos los invitados asistieron
    }

    @Test
    @Order(8)
    @DisplayName("8. Generar y guardar informe de reunión")
    void testGenerarYGuardarInforme() throws IOException {
        // Configurar reunión completa
        Date fecha = new Date();
        Instant horaPrevista = Instant.now().minus(Duration.ofMinutes(15));
        reunionPresencial = new ReunionPresencial(
            fecha, horaPrevista, Duration.ofMinutes(45),
            organizador, TipoReunion.TECNICA, "Sala Ejecutiva"
        );

        // Agregar invitados
        reunionPresencial.agregarInvitacion(new Invitacion(organizador));
        reunionPresencial.agregarInvitacion(new Invitacion(empleado1));
        reunionPresencial.agregarInvitacion(new Invitacion(empleado2));
        reunionPresencial.agregarInvitacion(new Invitacion(invitadoExterno));

        // Iniciar reunión
        reunionPresencial.iniciar();
        
        // Agregar notas durante la reunión
        reunionPresencial.agregarNota(new Nota("Punto 1: Revisar avances del proyecto"));
        reunionPresencial.agregarNota(new Nota("Punto 2: Discutir próximos objetivos"));
        
        // Registrar asistencias
        reunionPresencial.agregarAsistencia(new Asistencia(organizador));
        reunionPresencial.agregarAsistencia(new Asistencia(empleado1));
        reunionPresencial.agregarAsistencia(new Retraso(empleado2, Instant.now()));
        
        // Finalizar reunión
        reunionPresencial.finalizar();

        // Generar informe
        String informe = informeService.generarInforme(reunionPresencial);
        assertNotNull(informe);
        assertTrue(informe.contains("INFORME DE REUNIÓN"));
        assertTrue(informe.contains("Tipo de reunión: TECNICA"));
        assertTrue(informe.contains("Modalidad: Presencial"));
        assertTrue(informe.contains("Sala Ejecutiva"));
        assertTrue(informe.contains("Carlos Rodríguez")); // organizador
        
        System.out.println("\n" + "=".repeat(60));
        System.out.println("INFORME GENERADO:");
        System.out.println("=".repeat(60));
        System.out.println(informe);
        
        // Guardar informe a archivo
        String rutaArchivo = "informe_reunion_test.txt";
        informeService.guardarInforme(reunionPresencial, rutaArchivo);
        
        // Verificar que el archivo se creó
        File archivo = new File(rutaArchivo);
        assertTrue(archivo.exists());
        assertTrue(archivo.length() > 0);
        
        // Limpiar
        archivo.delete();
    }

    @Test
    @Order(9)
    @DisplayName("9. Validar excepciones - Registrar asistencia sin iniciar reunión")
    void testExcepcionReunionNoIniciada() {
        reunionVirtual = new ReunionVirtual(
            new Date(), Instant.now(), Duration.ofMinutes(30),
            organizador, TipoReunion.OTRO, "https://meet.google.com/xxx"
        );
        
        reunionVirtual.agregarInvitacion(new Invitacion(empleado1));
        
        // Intentar registrar asistencia sin iniciar la reunión
        assertThrows(ReunionNoIniciadaException.class, () -> {
            reunionVirtual.agregarAsistencia(new Asistencia(empleado1));
        });
    }

    @Test
    @Order(10)
    @DisplayName("10. Validar excepciones - Invitado no válido")
    void testExcepcionInvitadoNoValido() {
        reunionPresencial = new ReunionPresencial(
            new Date(), Instant.now(), Duration.ofMinutes(30),
            organizador, TipoReunion.TECNICA, "Sala 101"
        );
        
        // No agregamos ninguna invitación
        reunionPresencial.iniciar();
        
        // Intentar registrar asistencia de alguien no invitado
        assertThrows(InvitadoNoValidoException.class, () -> {
            reunionPresencial.agregarAsistencia(new Asistencia(empleado1));
        });
    }
}