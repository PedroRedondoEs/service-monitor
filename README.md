# Service Monitor

Aplicación desarrollada en Java 17 y Spring Boot para monitorizar la disponibilidad y el tiempo de respuesta de servicios web.

## Descripción

Service Monitor permite comprobar el estado de diferentes páginas y servicios web mediante peticiones HTTP.

La aplicación registra los resultados de cada comprobación, almacena un histórico en una base de datos H2 y genera estadísticas e informes de monitorización.

## Funcionalidades

- Lectura de direcciones web desde un archivo de configuración.
- Comprobación de disponibilidad mediante peticiones HTTP.
- Registro del código de estado HTTP.
- Medición del tiempo de respuesta en milisegundos.
- Detección de servicios disponibles y no disponibles.
- Almacenamiento del histórico de comprobaciones en H2.
- Cálculo de estadísticas de disponibilidad.
- Detección de períodos de caída de los servicios.
- Generación de informes en formato TXT.
- Configuración del número de rondas y del intervalo entre comprobaciones.
- Pruebas automáticas con JUnit.

## Tecnologías utilizadas

- Java 17
- Spring Boot
- Maven
- Spring Data JPA
- H2 Database
- JUnit 5
- Eclipse IDE

## Estructura del proyecto

El código principal se encuentra en:

`src/main/java/com/pedro/servicemonitor`

Las pruebas automáticas están en:

`src/test/java/com/pedro/servicemonitor`

Los archivos de configuración están en:

`src/main/resources`

## Configuración de servicios

Las direcciones que se desean monitorizar se incluyen en:

`src/main/resources/services.txt`

Ejemplo:

```text
https://www.google.com
https://github.com
https://httpbin.org/status/200
```

## Configuración de la monitorización

El archivo `application.properties` permite configurar el número de rondas y el tiempo de espera entre ellas.

Ejemplo:

```properties
monitor.rounds=1
monitor.interval-seconds=3
```

## Ejecución desde Eclipse

1. Importar el proyecto como proyecto Maven.
2. Comprobar que se utiliza Java 17.
3. Abrir la clase `ServiceMonitorApplication.java`.
4. Ejecutarla mediante **Run As → Java Application**.
5. Consultar los resultados en la consola.

## Resultados e informes

La aplicación muestra los resultados de las comprobaciones en la consola.

Los resultados se almacenan en una base de datos H2 local para mantener un histórico entre ejecuciones.

También se genera un archivo llamado:

`service-monitor-report.txt`

Este informe contiene información sobre los servicios monitorizados y sus estadísticas.

## Pruebas automáticas

El proyecto incluye pruebas para comprobar:

- La lectura de los servicios configurados.
- El tratamiento de URLs incorrectas y vacías.
- El registro de fechas y tiempos de respuesta.
- El almacenamiento y recuperación de resultados en H2.
- El arranque del contexto de Spring Boot.

Para ejecutar las pruebas desde Eclipse:

**Clic derecho sobre el proyecto → Run As → Maven test**

Última comprobación realizada: **7 pruebas superadas, sin fallos ni errores**.

## Autor

Proyecto desarrollado como parte de las prácticas de formación en desarrollo de software.