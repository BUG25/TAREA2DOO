package reuniones.model;
import java.util.ArrayList;
import java.util.List;

public class Departamento implements Invitable {/** clase que representa un departamento de la empresa
                                               * agrupa empleados y se pueden realizar invitaciones masivas implemnetando Invitable */
  private String nombre;
  private List<Empleado> empleados;

  public Departamento(String nombre){/** contructor para crear departamento con su nombre */
    this.nombre = nombre;
    this.empleados = new ArrayList<>();}

  public void agregarEmpleado(Empleado empleado){/** añade un empleado a la lista del departamneto
                                                * @param empleado (empleado a incorporar) */
    this.empleados.add(empleado);}

  public int obtenerCantidadEmpleados(){/** calcula y retorna numero total de pelados pertenecientes al departamento
                                      * @return cantidad de empleados */
    return this.empleados.size();}

/** implementamos la interfaz invitable
* si se invita a un departamento, se crea la invitacion automatica oara cadauno de los empleados del departamento */

  @Override
  public void invitar() {
    System.out.println("Enviando invitación masiva al departamento: " + nombre);
        for (Empleado emp : empleados) {
            emp.invitar();
        }
    }
}
  
