
package com.reuniones.model;

import com.reuniones.exception.ReunionNoIniciadaException;
import com.reuniones.exception.ReunionYaFinalizadaException;
import com.reuniones.exception.InvitadoNoValidoException;
import com.reuniones.model.enums.TipoReunion;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


/**
 * hacemos una clase abstracta que represente una reunión dentro del sistema. Maneja los tiempos programados,
 * estado de ejecución, invitaciones, asistencias y notas asociadas.
 */
public abstract class Reunion {

  private LocalDate fecha;
  private Instant horaPrevista;

  private Duration duracionPrevista;

  private Instant horaInicio;

  private Instant horaFin;

  private Empleado organizador; /** Relaciones */
  private TipoReunion tipoReunion;
  private List<Nota> notas;
  private List<Invitacion> invitaciones;
  private List<Asistencia> asistencias;

  private List<Nota> notas;

  /**
   * Construye una reunión con los datos básicos.
   *
   * @param fecha            fecha de la reunión
   * @param horaPrevista     hora prevista de inicio
   * @param duracionPrevista duración estimada
   * @param tipoReunion      tipo de reunión
   * @param organizador      empleado que organiza la reunión
   */
  public Reunion(LocalDate fecha, Instant horaPrevista, Duration duracionPrevista,
           TipoReunion tipoReunion, Empleado organizador) {
    this.fecha = fecha;
    this.horaPrevista = horaPrevista;
    this.duracionPrevista = duracionPrevista;
    this.tipoReunion = tipoReunion;
    this.organizador = organizador;
    this.invitaciones = new ArrayList<>();
    this.asistencias = new ArrayList<>();
    this.notas = new ArrayList<>();
  }

  // GESTIÓN INVITACIONES

  public void agregarInvitacion(Invitacion invitacion) {
    invitaciones.add(invitacion);
  }

  // GESTIÓN ASISTENIA

  /**
   * Comprueba si un participante figura en la lista de invitados.
   *
   * @param participante el participante a comprobar
   * @return {@code true} si está invitado
   */
  private boolean estaInvitado(Invitable participante) {
    return invitaciones.stream()
        .anyMatch(inv -> inv.getInvitado().equals(participante));
  }

  /**
   * Registra la asistencia de un participante sin retraso.
   *
   * @param participante el participante que asiste puntualmente
   * @throws InvitadoNoValidoException si el participante no está en la lista de invitados
   */
  public void registrarAsistencia(Invitable participante) {
    if (!estaInvitado(participante)) {
      throw new InvitadoNoValidoException(
        "El participante '" + participante + "' no está en la lista de invitados.");
    }
    asistencias.add(new Asistencia(participante));
  }

  /**
   * Registra la asistencia de un participante con retraso.
   *
   * @param participante  el participante que llegó tarde
   * @param horaLlegada   hora real de llegada
   * @throws InvitadoNoValidoException si el participante no está en la lista de invitados
   */
  public void registrarRetraso(Invitable participante, Instant horaLlegada) {
    if (!estaInvitado(participante)) {
      throw new InvitadoNoValidoException(
        "El participante '" + participante + "' no está en la lista de invitados.");
    }
    asistencias.add(new Asistencia(participante, new Retraso(horaLlegada)));
  }

  /**
   * Devuelve la lista de participantes que asistieron (con o sin retraso).
   *
   * @return lista de asistencias
   */
  public List<Asistencia> obtenerAsistencias() {
    return new ArrayList<>(asistencias);
  }

  /**
   * Devuelve la lista de invitados que no asistieron a la reunión.
   *
   * @return lista de invitables ausentes
   */
  public List<Invitable> obtenerAusencias() {
    List<Invitable> ausentes = new ArrayList<>();
    for (Invitacion inv : invitaciones) {
      Invitable invitado = inv.getInvitado();
      boolean asistio = asistencias.stream()
          .anyMatch(a -> a.getParticipante().equals(invitado));
      if (!asistio) {
        ausentes.add(invitado);
      }
    }
    return ausentes;
  }

  /**
   * Devuelve la lista de asistencias con retraso registrado.
   *
   * @return lista de asistencias tardías
   */
  public List<Asistencia> obtenerRetrasos() {
    List<Asistencia> retrasos = new ArrayList<>();
    for (Asistencia a : asistencias) {
      if (a.tieneretraso()) {
        retrasos.add(a);
      }
    }
    return retrasos;
  }

  /**
   * Devuelve el número total de participantes que asistieron.
   *
   * @return número de asistentes
   */
  public int obtenerTotalAsistencia() {
    return asistencias.size();
  }

  /**
   * Calcula el porcentaje de asistentes respecto al total de invitados.
   *
   * @return porcentaje de asistencia (0.0 si no hay invitados)
   */
  public float obtenerPorcentajeAsistencia() {
    if (invitaciones.isEmpty()) return 0.0f;
    return (float) asistencias.size() / invitaciones.size() * 100;
  }

  // ---- Ciclo de vida ----

  /**
   * Inicia la reunión registrando la hora actual como hora de inicio.
   *
   * @throws ReunionYaFinalizadaException si la reunión ya fue finalizada
   */
  public void iniciar() {
    if (horaFin != null) {
      throw new ReunionYaFinalizadaException("La reunión ya fue finalizada.");
    }
    this.horaInicio = Instant.now();
  }

  /**
   * Finaliza la reunión registrando la hora actual como hora de fin.
   *
   * @throws ReunionNoIniciadaException   si la reunión no ha sido iniciada
   * @throws ReunionYaFinalizadaException si la reunión ya fue finalizada
   */
  public void finalizar() {
    if (horaInicio == null) {
      throw new ReunionNoIniciadaException("La reunión no ha sido iniciada.");
    }
    if (horaFin != null) {
      throw new ReunionYaFinalizadaException("La reunión ya fue finalizada.");
    }
    this.horaFin = Instant.now();
  }

  /**
   * Calcula la duración real de la reunión (entre inicio y fin).
   *
   * @return duración real en minutos como float
   * @throws ReunionNoIniciadaException si la reunión no fue iniciada
   * @throws ReunionYaFinalizadaException si la reunión no ha terminado aún
   */
  public float calcularTiempoReal() {
    if (horaInicio == null) {
      throw new ReunionNoIniciadaException("La reunión no ha sido iniciada.");
    }
    if (horaFin == null) {
      throw new ReunionYaFinalizadaException("La reunión no ha sido finalizada todavía.");
    }
    return Duration.between(horaInicio, horaFin).toSeconds() / 60.0f;
  }

  // ---- Notas ----

  /**
   * Añade una nota a la reunión.
   *
   * @param nota nota a añadir
   */
  public void agregarNota(Nota nota) {
    notas.add(nota);
  }

  /**
   * Devuelve las notas ordenadas cronológicamente por hora de creación.
   *
   * @return lista de notas ordenada
   */
  public List<Nota> obtenerNotasOrdenadas() {
    List<Nota> ordenadas = new ArrayList<>(notas);
    ordenadas.sort(Comparator.comparing(Nota::getHoraCreacion));
    return ordenadas;
  }

  // ---- Getters y Setters ----

  /** @return fecha de la reunión */
  public LocalDate getFecha() { return fecha; }

  /** @param fecha nueva fecha */
  public void setFecha(LocalDate fecha) { this.fecha = fecha; }

  /** @return hora prevista de inicio */
  public Instant getHoraPrevista() { return horaPrevista; }

  /** @param horaPrevista nueva hora prevista */
  public void setHoraPrevista(Instant horaPrevista) { this.horaPrevista = horaPrevista; }

  /** @return duración prevista */
  public Duration getDuracionPrevista() { return duracionPrevista; }

  /** @param duracionPrevista nueva duración prevista */
  public void setDuracionPrevista(Duration duracionPrevista) { this.duracionPrevista = duracionPrevista; }

  /** @return hora real de inicio */
  public Instant getHoraInicio() { return horaInicio; }

  /** @param horaInicio nueva hora de inicio */
  public void setHoraInicio(Instant horaInicio) { this.horaInicio = horaInicio; }

  /** @return hora real de fin */
  public Instant getHoraFin() { return horaFin; }

  /** @param horaFin nueva hora de fin */
  public void setHoraFin(Instant horaFin) { this.horaFin = horaFin; }

  /** @return tipo de reunión */
  public TipoReunion getTipoReunion() { return tipoReunion; }

  /** @param tipoReunion nuevo tipo de reunión */
  public void setTipoReunion(TipoReunion tipoReunion) { this.tipoReunion = tipoReunion; }

  /** @return organizador de la reunión */
  public Empleado getOrganizador() { return organizador; }

  /** @param organizador nuevo organizador */
  public void setOrganizador(Empleado organizador) { this.organizador = organizador; }

  /** @return lista de invitaciones */
  public List<Invitacion> getInvitaciones() { return invitaciones; }

  /** @return lista de notas */
  public List<Nota> getNotas() { return notas; }

  /**
   * Representación textual base de la reunión.
   *
   * @return descripción con fecha, tipo y organizador
   */
  @Override
  public String toString() {
    return "Reunion{fecha=" + fecha + ", tipo=" + tipoReunion
        + ", organizador=" + organizador.getNombreCompleto() + "}";
  }
}

  public void iniciar(){ /** inicia la reunion y la hora de inicio se registra como el instante actual */
    this.horaInicio = Instant.now();}

/** se finaliza la reunion registrando la hora final como el instante actual
* @throws ReunionNoIniciadaException si la reunion no ha sido iniciada aun */

  public void finalizar(){
    if(this.horaInicio == null){
      throw new ReunionNoIniciadaException("No se puede finalizar una reunion que no ha iniciado");
    }
    this.horaFin = Instant.now();
  }

  public float calcularTiempoReal(){ /**calcula el tiempo real transcurrido entre el incio y fin de la reunion en minutos
                                      *@return duracion en minutos, o 0 si es que no se ha terminado la reunion */
    if(horaInicio != null && horaFin != null){
      return Duration.between(horaInicio,horaFin).toMinutes();
    }
    return 0;
  }

  public List<Asistencia> obtenerAsistencias(){ /**obtiene lista de asitencias*/
    return this.asistencias;
  }

  public List<Invitable> obtenerAusencias(){ /** obtiene lista de invitados que no registraron asistencia*/
    List<Invitable> ausentes = new ArrayList<>();
    for(Invitacion inv : invitaciones) {
      Invitable invitado = inv.getInvitado();
      boolean presente = false;
      for (Asistencia asis: asistencias){
        if(asis.getParticipante().equals(invitado)){
          presente = true;
          break;
        }
      }
      if (!presente){
        ausentes.add(invitado);
      }
    }
    return ausentes;
  }
  public List<Retraso> obtenerRetrasos(){ /** Obtiene lista con asistencias que sean retrasos*/
    List<Retraso> retrasos = new ArrayList<>();
    for(Asistencia asis: asistencias){
      if (asis instanceof Retraso) {
      retrasos.add((Retraso) asis);
    }
  }
    return retrasos;
}
  public int obtenerTotalAsistencia(){ /** Obtiene la cant total de personas asistentes*/
    return this.asistencias.size();
}
  public float obtenerPorcentajeAsistencia(){ /** Calculamos el procentaje de asistencia segun el total de invitaciones */
    if(invitaciones.isEmpty()) return 0;
    return ((float) obtenerTotalAsistencia() / invitaciones.size())*100;
}
  public List<Nota> obtenerNotasOrdenadas(){ /** Obtiene las notas ordenadas cronologicamente*/
    List<Nota> notasOrdenadas = new ArrayList<>(this.notas);
    notasOrdenadas.sort(Comparator.comparing(Nota::getHoraCreacion));
    return notasOrdenadas;
}
  public void agregarNota(Nota nota){
    this.notas.add(nota);
}
  public void agregarInvitacion(Invitacion invitacion){
    this.invitaciones.add(invitacion);
}
  public void agregarAsistencia(Asistencia asistencia){
    if(this.horaInicio == null){
      throw new ReunionNoIniciadaException("No se puede registrar asistencia de una reunion que no ha comenzado");
  }
    if(this.horaFin != null){
      throw new ReunionYaFinalizadaException("No se puede registrar la asistencia de una reunion que ya ha terminado");
  }
    this.asistencias.add(asistencia);
}

/** Getters y setters */
  public Date getFecha(){
    return fecha; }
  public void setFecha(Date fecha){
    this.fecha = fecha; }
  public Instant getHoraPrevista(){
    return horaPrevista;}
  public void setHoraPrevista(Instant horaPrevista){
    this.horaPrevista = horaPrevista; }
  public Duration getDuracionPrevista(){
    return duracionPrevista;}
  public void setDuracionPrevista(Duration duracionPrevista){
    this.duracionPrevista = duracionPrevista;}
  public Instant getHoraInicio(){
    return horaInicio;}
  public Instant getHoraFin(){
    return horaFin;}
  public Empleado getOrganizador(){
    return organizador;}
  public void setOrganizador(Empleado organizador){
    this.organizador = organizador;}
  public TipoReunion getTipoReunion(){
    return tipoReunion;}
  public void setTipoReunion(TipoReunion tipoReunion){
    this.tipoReunion = tipoReunion;}
  public List<Invitacion> getInvitaciones(){
    return invitaciones;}
  
@Override
  public String toString(){
    return "Reunion{" + "fecha=" + fecha + ", tipo=" + tipoReunion + ", organizador=" + organizador.getNombre() + " " + organizador.getApellidos() + ", totalInvitados=" + invitaciones.size() + "}";
  }
}
