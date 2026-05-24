# Tarea 2 - Sistema de Gestión de Reuniones

## Integrantes
- Diego Alonso Alday Cortés
- Josefa Valentina Arriagada Valiente
- Trinidad Agustina Castro Castro

## Diagrama UML
![UML](./new%20UML.png)

## Explicación y justificación de los cambios en el modelo UML

### Nuevas funcionalidades implementadas

#### 1. Invitados externos
Se añadió la clase `InvitadoExterno` que implementa la interfaz `Invitable`. Esto permite que personas ajenas a la empresa puedan ser invitadas a reuniones, manteniendo el polimorfismo con `Empleado` y `Departamento`. Cada invitado externo tiene nombre, apellidos y correo electrónico.

#### 2. Informe de reunión
Se creó la clase de servicio `InformeReunion` con los métodos:
- `generarInforme(Reunion)` - genera el contenido del informe en texto
- `guardarInforme(Reunion, String)` - guarda el informe en un archivo .txt

El informe incluye: fecha/hora, inicio/fin/duración, tipo de reunión, enlace o sala, lista de participantes (con retrasos), ausentes y notas ordenadas cronológicamente.

#### 3. Excepciones personalizadas
Se añadieron 5 excepciones para manejar casos de error:
- `EmailNoValidoException` - correo inválido o nulo
- `EmpleadoNoValidoException` - nombre o apellido con caracteres no permitidos
- `InvitadoNoValidoException` - asistente no estaba en lista de invitados
- `ReunionNoIniciadaException` - operación antes de iniciar reunión
- `ReunionYaFinalizadaException` - operación después de finalizar reunión

### Cambios respecto al UML original
- Se añadió `InvitadoExterno` como nueva clase
- Se añadió `InformeReunion` como clase de servicio
- Se añadieron las 5 excepciones personalizadas
- `Retraso` ahora hereda de `Asistencia` (herencia)
- Se añadió el método `obtenerAusencias()` a `Reunion`

## Decisiones de diseño

Se implementó interfaz `Invitable` para tratar empleados, departamentos e invitados externos polimórficamente, unificando invitaciones y asistencias sin distinguir tipo concreto.

Clase `InformeReunion` se diseñó como servicio independiente, no como método de `Reunion`, siguiendo SRP. Así generación y persistencia del informe no sobrecargan lógica de reunión.

Excepciones personalizadas extienden `RuntimeException` para evitar propagación excesiva de `throws`, simplificando código y concentrando manejo de errores en tests.

`Retraso` hereda de `Asistencia` para reutilizar estructura base y añadir hora de llegada, respetando Liskov. Permite recorrer única lista de `Asistencia` e identificar retrasos polimórficamente.

Notas almacenan `Instant` de creación automático, garantizando orden cronológico preciso sin timestamps manuales, evitando inconsistencias.

## Estructura del proyecto
```
tarea2/
├── src/main/java/reuniones/
│ ├── model/
│ │ ├── Asistencia.java
│ │ ├── Departamento.java
│ │ ├── Empleado.java
│ │ ├── Invitable.java
│ │ ├── Invitacion.java
│ │ ├── InvitadoExterno.java
│ │ ├── Nota.java
│ │ ├── Retraso.java
│ │ ├── Reunion.java (abstracta)
│ │ ├── ReunionPresencial.java
│ │ ├── ReunionVirtual.java
│ │ ├── TipoReunion.java (enum)
│ │ ├── exception/
│ │ │ ├── EmailNoValidoException.java
│ │ │ ├── EmpleadoNoValidoException.java
│ │ │ ├── InvitadoNoValidoException.java
│ │ │ ├── ReunionNoIniciadaException.java
│ │ │ └── ReunionYaFinalizadaException.java
│ │ └── service/
│ │ └── InformeReunion.java
├── tests/
│ ├── AsistenciaTest.java
│ ├── DepartamentoTest.java
│ ├── InvitacionesTest.java
│ ├── NotasTest.java
│ ├── RetrasoTest.java
│ └── ReunionCompletoTest.java
├── pom.xml
├── new UML.png
└── README.md
```

## Cómo ejecutar los tests
```bash
mvn test
```
sin maven:
```bash
javac -d target/classes src/main/reuniones/model/*.java src/main/reuniones/model/exception/*.java src/main/reuniones/model/service/*.java
javac -cp "target/classes;junit-platform-console-standalone.jar" -d target/test-classes tests/*.java
java -jar junit-platform-console-standalone.jar --class-path "target/classes;target/test-classes" --scan-class-path
```
## Resultados de pruebas
```
Test run finished after 905 ms
[         9 containers found      ]
[         0 containers skipped    ]
[         9 containers started    ]
[         0 containers aborted    ]
[         9 containers successful ]
[         0 containers failed     ]
[        43 tests found           ]
[         0 tests skipped         ]
[        43 tests started         ]
[         0 tests aborted         ]
[        43 tests successful      ]
[         0 tests failed          ]
```

Todas las funcionalidades están probadas: creación de reuniones, invitaciones, asistencias, retrasos, notas, excepciones y generación de informes.

## Cómo guardar:

Crea el archivo `README.md` en la raíz de tu proyecto:

```cmd
cd "D:\Archivos\Batman\Universidad\2026-1\DOO\TAREA 2\tarea2"
notepad README.md
