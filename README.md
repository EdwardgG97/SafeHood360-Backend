# SafeHood360 Backend

## Descripción
SafeHood360 es un sistema backend que proporciona servicios de seguridad comunitaria. Esta aplicación está diseñada para ayudar a las comunidades a gestionar y monitorear aspectos relacionados con la seguridad local.

## Tecnologías Utilizadas

- **Backend**: Spring Boot 3.5.0
- **Base de Datos**: MySQL
- **Lenguaje**: Java 17
- **Dependencias Principales**:
  - Spring Boot Starter Web
  - Spring Boot Starter Data JPA
  - Lombok
  - Spring Boot Starter Test

## Estructura del Proyecto

```
src/main/java/com/safehood/
├── config/          # Configuraciones de la aplicación
├── controller/      # Controladores REST
├── dto/            # Transfer Objects
├── model/          # Entidades del dominio
├── repository/     # Repositorios JPA
└── service/        # Servicios de negocio
```

## Requisitos Previos

Para ejecutar este proyecto necesitas:

1. Java 17 instalado
2. Maven 3.6.0 o superior
3. MySQL Server
4. IDE compatible con Spring Boot (Recomendado: IntelliJ IDEA o Spring Tool Suite)

## Configuración de la Base de Datos

1. Configura las credenciales de MySQL en el archivo `application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/safehood360
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
```

## Instalación

1. Clona el repositorio:
```bash
git clone [url-del-repositorio]
```

2. Navega al directorio del proyecto:
```bash
cd SafeHood360-Backend
```

3. Compila el proyecto:
```bash
mvn clean install
```

## Ejecución

1. Ejecuta la aplicación usando Maven:
```bash
mvn spring-boot:run
```

O bien, desde tu IDE:
1. Abre el proyecto en tu IDE
2. Ejecuta la clase `Safehood360Application.java`

## Documentación de la API

La documentación de la API está disponible en:
```
http://localhost:8080/swagger-ui.html
```

## Contribución

1. Crea un fork del repositorio
2. Crea tu rama de características (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

[Insertar licencia aquí]

## Contacto

[Información de contacto aquí]
