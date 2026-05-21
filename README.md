# Sistema de Gestión de Reuniones

## Integrantes del grupo

> ⚠️ **Completar con los nombres completos de los estudiantes del grupo.**

- Estudiante 1 — Nombre Apellido
- Estudiante 2 — Nombre Apellido
- Estudiante 3 — Nombre Apellido

---

## Descripción

Sistema Java orientado a objetos para gestionar reuniones de empresa. Permite crear reuniones virtuales y presenciales, invitar empleados y externos, registrar asistencia y retrasos, añadir notas y generar un informe en formato `.txt`.

---

## Estructura del proyecto

```
reunion-manager/
├── pom.xml
└── src/
    ├── main/java/com/reuniones/
    │   ├── model/
    │   │   ├── enums/TipoReunion.java
    │   │   ├── Invitable.java         (interface)
    │   │   ├── Empleado.java
    │   │   ├── InvitadoExterno.java   (nuevo)
    │   │   ├── Departamento.java
    │   │   ├── Invitacion.java
    │   │   ├── Asistencia.java
    │   │   ├── Retraso.java
    │   │   ├── Nota.java
    │   │   ├── Reunion.java           (abstracta)
    │   │   ├── ReunionVirtual.java
    │   │   └── ReunionPresencial.java
    │   ├── service/
    │   │   └── InformeReunion.java    (nuevo)
    │   └── exception/
    │       ├── ReunionNoIniciadaException.java
    │       └── ReunionYaFinalizadaException.java
    └── test/java/com/reuniones/
        ├── EmpleadoTest.java
        ├── DepartamentoTest.java
        ├── ReunionTest.java
        ├── InvitadoExternoTest.java
        └── InformeReunionTest.java
```

---

## Modelo UML

```
«interface»
Invitable
+ invitar(reunion: Reunion): void
       ▲               ▲
       │               │
  Empleado      InvitadoExterno   ← NUEVO
  - id          - nombreCompleto
  - apellidos   - correo
  - nombre
  - correo
  - departamento

Departamento implements Invitable
  - nombre
  - empleados: List<Empleado>
  + obtenerCantidadEmpleados(): int
  + invitar(reunion): void  → invita a todos sus empleados

Invitacion
  - invitado: Invitable
  - hora: Instant

«abstract»
Reunion
  - fecha: LocalDate
  - horaPrevista: Instant
  - duracionPrevista: Duration
  - horaInicio: Instant
  - horaFin: Instant
  - tipoReunion: TipoReunion
  - organizador: Empleado
  - invitaciones: List<Invitacion>
  - asistencias: List<Asistencia>
  - notas: List<Nota>
  + obtenerAsistencias(): List
  + obtenerAusencias(): List
  + obtenerRetrasos(): List
  + obtenerTotalAsistencia(): int
  + obtenerPorcentajeAsistencia(): float
  + calcularTiempoReal(): float
  + iniciar(): void
  + finalizar(): void
  + agregarNota(nota): void
  + obtenerNotasOrdenadas(): List

       ├── ReunionVirtual    - enlace: String
       └── ReunionPresencial - sala: String

Asistencia
  - participante: Invitable
  - retraso: Retraso (nullable)

Retraso
  - hora: Instant

Nota
  - contenido: String
  - horaCreacion: Instant

«enumeration»
TipoReunion: TECNICA | MARKETING | OTRO

InformeReunion  ← NUEVO (service)
  + generarInforme(reunion): String
  + guardarInforme(reunion, ruta): void
```

---

## Cambios y adiciones al modelo UML original

### 1. `InvitadoExterno` (nuevo)

**Requisito:** el enunciado pide poder invitar personas externas que no son empleados.

**Decisión:** se creó la clase `InvitadoExterno` que implementa `Invitable`, con atributos `nombreCompleto` y `correo`. Al implementar la misma interfaz que `Empleado`, puede ser invitada, registrada en asistencias y ausencias sin cambiar la lógica de `Reunion`. Esto respeta el **principio abierto/cerrado**: añadimos funcionalidad sin modificar las clases existentes.

### 2. `InformeReunion` (nuevo servicio)

**Requisito:** generar un informe con fecha, horas, tipo, sala/enlace, participantes, retrasos y notas, guardado en `.txt`.

**Decisión:** se separó la lógica de generación de informe en una clase de servicio `InformeReunion`, en vez de meter ese método en `Reunion`. Esto sigue el **principio de responsabilidad única (SRP)**: `Reunion` gestiona la lógica de la reunión; `InformeReunion` se encarga únicamente de la presentación y escritura.

### 3. `horaCreacion` en `Nota`

**Motivación:** el enunciado pide notas ordenadas cronológicamente en el informe. Se añadió el campo `horaCreacion: Instant` a `Nota` y el método `obtenerNotasOrdenadas()` en `Reunion`.

### 4. Excepciones propias

Se añadieron `ReunionNoIniciadaException` y `ReunionYaFinalizadaException` (extienden `RuntimeException`) para manejar los estados inválidos del ciclo de vida de la reunión de forma expresiva y comprobable en tests.

---

## Ejecución de tests

```bash
mvn test
```

Los tests cubren:
- Creación de empleados y departamentos
- Invitación individual y de departamentos completos
- Registro de asistencia, retrasos y ausencias
- Porcentaje de asistencia
- Ciclo de vida: iniciar / finalizar / calcular tiempo real
- Excepciones en estados inválidos
- Invitados externos
- Generación y guardado de informe

---

## Cómo compilar

```bash
mvn compile
```

Requiere Java 17+ y Maven 3.6+.
