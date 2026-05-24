package reuniones.model;
import reuniones.model.exception.ReunionNoIniciadaException;
import reuniones.model.exception.ReunionYaFinalizadaException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public abstract class Reunion { /** hacemos una clase abstracta que represente una reinion dentro del sistema
                                * mapea los tiempos programados, estado de ejecucion, invitaciones, asistencias y notas asociadas
                                */
  private Date fecha;
  private Instant horaPrevista;
  private Duration duracionPrevista;
  private Instant horaInicio;
  private Instant horaFin;

  private Empleado organizador; /** Relaciones */
  private TipoReunion tipoReunion;
  private List<Nota> notas;
  private List<Invitacion> invitaciones;
  private List<Asistencia> asistencias;

/** creamos un constructor basa para incializar las listas de la reunion*/
  public Reunion(Date fecha, Instant horaPrevista, Duration duracionPrevista, Empleado organizador, TipoReunion tipoReunion){
    this.fecha = fecha;
    this.horaPrevista = horaPrevista;
    this.duracionPrevista = duracionPrevista;
    this.organizador = organizador;
    this.tipoReunion = tipoReunion;
    this.notas = new ArrayList<>();
    this.invitaciones = new ArrayList<>();
    this.asistencias = new ArrayList<>();}

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
