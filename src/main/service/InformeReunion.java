package com.reuniones.service;

import com.reuniones.model.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Servicio encargado de generar el informe de una reunión y guardarlo en un archivo .txt.
 *
 * <p>El informe incluye: fecha y hora, horas de inicio/fin, tipo de reunión,
 * enlace o sala, lista de participantes con retrasos, y notas ordenadas cronológicamente.</p>
 */
public class InformeReunion {

    /** Formato de fecha y hora utilizado en el informe. */
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").withZone(ZoneId.systemDefault());

    /**
     * Genera el contenido textual del informe para la reunión dada.
     *
     * @param reunion la reunión de la que se genera el informe
     * @return contenido del informe como cadena de texto
     */
    public String generarInforme(Reunion reunion) {
        StringBuilder sb = new StringBuilder();
        sb.append("=================================================\n");
        sb.append("           INFORME DE REUNIÓN\n");
        sb.append("=================================================\n\n");

        // Fecha y hora prevista
        sb.append("Fecha: ").append(reunion.getFecha()).append("\n");
        sb.append("Hora prevista: ").append(FORMATTER.format(reunion.getHoraPrevista())).append("\n");
        sb.append("Duración prevista: ").append(reunion.getDuracionPrevista().toMinutes()).append(" minutos\n\n");

        // Inicio y fin reales
        if (reunion.getHoraInicio() != null) {
            sb.append("Hora de inicio: ").append(FORMATTER.format(reunion.getHoraInicio())).append("\n");
        } else {
            sb.append("Hora de inicio: No registrada\n");
        }
        if (reunion.getHoraFin() != null) {
            sb.append("Hora de fin: ").append(FORMATTER.format(reunion.getHoraFin())).append("\n");
            long minutos = Duration.between(reunion.getHoraInicio(), reunion.getHoraFin()).toMinutes();
            sb.append("Duración total: ").append(minutos).append(" minutos\n\n");
        } else {
            sb.append("Hora de fin: No registrada\n\n");
        }

        // Tipo de reunión
        sb.append("Tipo de reunión: ").append(reunion.getTipoReunion()).append("\n");

        // Enlace o sala
        if (reunion instanceof ReunionVirtual) {
            sb.append("Modalidad: Virtual\n");
            sb.append("Enlace: ").append(((ReunionVirtual) reunion).getEnlace()).append("\n\n");
        } else if (reunion instanceof ReunionPresencial) {
            sb.append("Modalidad: Presencial\n");
            sb.append("Sala: ").append(((ReunionPresencial) reunion).getSala()).append("\n\n");
        }

        // Organizador
        sb.append("Organizador: ").append(reunion.getOrganizador().getNombreCompleto()).append("\n\n");

        // Estadísticas de asistencia
        sb.append("-------------------------------------------------\n");
        sb.append("ASISTENCIA\n");
        sb.append("-------------------------------------------------\n");
        sb.append("Total invitados: ").append(reunion.getInvitaciones().size()).append("\n");
        sb.append("Total asistentes: ").append(reunion.obtenerTotalAsistencia()).append("\n");
        sb.append(String.format("Porcentaje de asistencia: %.1f%%\n\n", reunion.obtenerPorcentajeAsistencia()));

        // Lista de participantes
        sb.append("Participantes presentes:\n");
        for (Asistencia a : reunion.obtenerAsistencias()) {
            String nombre = obtenerNombre(a.getParticipante());
            if (a.tieneretraso()) {
                sb.append("  - ").append(nombre)
                  .append(" [TARDE - llegó a las ")
                  .append(FORMATTER.format(a.getRetraso().getHora())).append("]\n");
            } else {
                sb.append("  - ").append(nombre).append("\n");
            }
        }

        // Ausentes
        List<Invitable> ausentes = reunion.obtenerAusencias();
        if (!ausentes.isEmpty()) {
            sb.append("\nAusentes:\n");
            for (Invitable ausente : ausentes) {
                sb.append("  - ").append(obtenerNombre(ausente)).append("\n");
            }
        }
        sb.append("\n");

        // Notas (ordenadas cronológicamente)
        sb.append("-------------------------------------------------\n");
        sb.append("NOTAS DE LA REUNIÓN\n");
        sb.append("-------------------------------------------------\n");
        List<Nota> notas = reunion.obtenerNotasOrdenadas();
        if (notas.isEmpty()) {
            sb.append("Sin notas registradas.\n");
        } else {
            for (Nota nota : notas) {
                sb.append("[").append(FORMATTER.format(nota.getHoraCreacion())).append("] ")
                  .append(nota.getContenido()).append("\n");
            }
        }

        sb.append("\n=================================================\n");
        return sb.toString();
    }

    /**
     * Guarda el informe de la reunión en un archivo de texto.
     *
     * @param reunion         la reunión de la que generar el informe
     * @param rutaArchivo     ruta del archivo donde se guardará el informe
     * @throws IOException    si ocurre un error al escribir el archivo
     */
    public void guardarInforme(Reunion reunion, String rutaArchivo) throws IOException {
        String contenido = generarInforme(reunion);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(contenido);
        }
        System.out.println("Informe guardado en: " + rutaArchivo);
    }

    /**
     * Obtiene el nombre descriptivo de un {@link Invitable}.
     *
     * @param invitable la entidad invitable
     * @return nombre completo o identificador del invitable
     */
    private String obtenerNombre(Invitable invitable) {
        if (invitable instanceof Empleado) {
            return ((Empleado) invitable).getNombreCompleto() + " (Empleado)";
        } else if (invitable instanceof InvitadoExterno) {
            return ((InvitadoExterno) invitable).getNombreCompleto() + " (Externo)";
        }
        return invitable.toString();
    }
}
