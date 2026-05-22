package com.reuniones.model;
public class Empleado implements Invitable {/** clase que represneta a un empelado 
                                            * Un emepleado puede ser organizador como invitado a la reunion */
private String id;
private String apellidos;
private String nombre;
private String correo;

public Empleado(String id, String nombre, String apellidos, String correo){ /** constructor para inicializar un empleado */
  this.id = id;
  this.nombre = nombre;
  this.apellidos = apellidos;
  this.correo = correo;
}

/** Implementa el metodo de la interfaz Invitable */
@Overrride
public void invitar() {
  System.out.println("Enviando invitacion a traves de la consola al empleado: " + getNombreCompleto() + " (" + correo + ")");
}
public String getNombreCompleto(){ /** combianmos el nombre con apellido para facilitar la lectura
                                    * @return nombre completo del empleado */
  return this.nombre + " " + this,apellidos;
}
/** Getters y setters */
public String getId(){
  return id;}
public void setId(String id) {
  this.id = id;}
public String getNombre(){
  return nombre;}
public void setNombre(String nombre){
  this.nombre = nombre;}
public String getApellidos(){
  return apellidos;}
public void setApellidos(String apellidos){
  this.apellidos;}
public String getCorreo(){
  return correo;}
public void setCorreo(String correo){
  this.correo = correo;}

@Override
public String toString(){
  return "Empleado{@ + @id='" + id + "'\" + ", nombre completo='" + getNombreCompleto() + "'/" + ", correo='" + correo + "'\" + "}";
}
