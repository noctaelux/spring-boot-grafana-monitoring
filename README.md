# Spring boot - Microservice monitoring

## Requerimientos
- Java v11+
- Maven 3.8.3+
- Docker v20.10.21+
- Prometheus [Docker Container](https://hub.docker.com/r/prom/prometheus)
- Grafana [Docker Container](https://hub.docker.com/r/grafana/grafana)

## Instalación / Ejecución
Para monitorizar esta aplicación de Spring boot, se deben agregar las siguientes librerías al archivo `pom.xml`:
```
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
```
<dependency>
    <groupId>io.micrometer</groupId>
    <artifactId>micrometer-registry-prometheus</artifactId>
</dependency>
```
Con las siguientes configuraciones en el archivo `application.properties`:
```
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always
```

Adicionalmente, se proporciona el archivo `prometheus.yml` dentro de la carpeta de recursos del proyecto. Se debe
configurar el parámetro `<ip-local>` con la **IP** que registra nuestro equipo, esto para que los contenedores puedan
observarse entre ellos. Se sugiere modificar el nombre del parámetro `job_name` con algún nombre que mejor identifique 
el contexto del proyecto.

Para levantar los contenedores de Prometheus y Grafana, se sugiere ejecutar los siguientes comandos de docker:

### Prometheus
```
docker run \
    -d --name=prometheus \
    -p 9090:9090 \
    -v /path/to/prometheus.yml:/etc/prometheus/prometheus.yml \
    prom/prometheus
```
Donde reemplazaremos `/path/to/prometheus.yml` con el path absoluto del archivo proporcionado para la aplicación.
Accederemos por medio del navegador en la URL: http://localhost:9090.

### Grafana
Luego, deberemos levantar el contenedor de Grafana:
```
docker run -d --name=grafana -p 3000:3000 grafana/grafana
```
Y accedemos por medio del navegador a http://localhost:3000 (usuario y password: admin, admin).

### Spring boot
Finalmente, levantaremos el proyecto de Spring boot:
```
mvn spring-boot:run
```
Si accedemos a la ruta http://localhost:8080/actuator observaremos varias rutas, entre ellas debemos observar la siguiente:
http://localhost:8080/actuator/prometheus, si podemos acceder a ella, veremos varias definiciones de todos los comandos
que podremos manipular posteriormente en Grafana.

## Descripción
En esta _Prueba de Concepto_ se pretende configurar el proyecto para poder ser monitoreado desde una instancia de Grafana
por medio de Prometheus, el cual realizará la ingesta de datos; ambos proyectos son de código abierto. Grafana permitirá
visualizar por medio de gráficas y tablas la información que Prometheus recopila de la aplicación y permitirá monitorear
todo tipo de actividad dentro del ámbito de la aplicación.

Se sugiere revisar la documentación de ambos proyectos (los cuales se anexan en la sección **Fuentes**) para dominar el
uso de ambas herramientas, también se sugiere ver los tutoriales de YouTube que se anexan al final.

Como extra, se sugiere importar el tablero [SpringBoot APM Dashboard](https://grafana.com/grafana/dashboards/12900-springboot-apm-dashboard/)
como punto de partida, ya que éste proporciona gráficas de monitoreo bastante útiles.

Deberemos configurar el origen de los datos desde Grafana proporcionando la instancia de Prometheus cuidando que la URL 
corresponda con la IP que tenemos configurado en nuestro equipo. Posteriormente, podremos agregar paneles a nuestro Dashboard.

Si todo ha funcionado correctamente, si ejecutamos la URL http://localhost:8080/message repetidamente, podremos visualizar
en el Panel **Request Count** del APM en Grafana los llamados por intervalo de tiempo a nuestra aplicación.

### Fuentes
- https://www.youtube.com/watch?v=2wr9njNdywk
- https://www.youtube.com/watch?v=Q_ZMuv_kTe0
- https://grafana.com/docs/grafana/latest/
- https://prometheus.io/docs/prometheus/latest/installation/