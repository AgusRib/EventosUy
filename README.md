

EventosUY es una plataforma para la gestión y organización de eventos en Uruguay. El proyecto permite a los usuarios registrarse, crear eventos, seguir a otros organizadores, patrocinar eventos y registrarse como asistentes.

## Características Principales
* **Gestión de Eventos:** Crear, editar y consultar eventos.
* **Categorías de Eventos:** Organización por categorías personalizables.
* **Usuarios y Perfiles:** Sistema de usuarios con roles de organizadores y asistentes.
* **Seguimiento:** Seguir a otros organizadores y eventos.
* **Instituciones:** Gestión de instituciones relacionadas con los eventos.
* **Patrocinios:** Sistema de gestión de patrocinadores.
* **Aplicación Móvil:** Acceso optimizado para dispositivos móviles.

##  Arquitectura del Proyecto
El proyecto está dividido en tres módulos principales:

### 1. Servidor Central (Backend)
* **Ubicación:** `/serv central`
* **Tipo:** Servidor + Web Services (JAX-WS)
* **Tecnología:** Java 21 + JPA + Hibernate
* **Interfaz:** JavaX Swing (Administración)
* **Descripción:** Servidor central que encapsula la lógica de negocio, acceso a base de datos y expone servicios web para los clientes (Web y Mobile).

### 2. Web (Frontend)
* **Ubicación:** `/web`
* **Tipo:** Aplicación WAR (Jakarta EE)
* **Tecnología:** Java 21 + JSP/Servlets
* **Servidor:** Apache Tomcat 11.0.13
* **Descripción:** Interfaz web principal que consume los servicios SOAP del backend.

### 3. Mobile (Frontend Móvil)
* **Ubicación:** `/mobile`
* **Tipo:** Aplicación WAR
* **Tecnología:** Java 21
* **Servidor:** Apache Tomcat 11.0.13
* **Descripción:** Interfaz responsiva enfocada en la experiencia de uso desde dispositivos móviles.

## 🛠️ Requisitos Previos
* **Java:** JDK 21 o superior
* **Maven:** 3.8.x o superior
* **Git:** Para clonar el repositorio

**Verificar instalación:**
```bash
java -version
mvn -version
```

##  Build (Compilación)
El proyecto incluye scripts en el directorio raíz para compilar todos los módulos automáticamente:

**En Windows:**
```cmd
build.bat
```

**En Linux/macOS:**
```bash
./build.sh
```

**Qué hace el build:**
1. Compila el módulo `serv central` y genera su respectivo compilado.
2. Compila el módulo `web` y genera el archivo WAR.
3. Compila el módulo `mobile` y genera el archivo WAR.
4. Mueve los archivos estáticos y configuraciones necesarias.

##  Deploy (Despliegue)
Para desplegar las aplicaciones web y móvil, sus empaquetados (`.war`) deben ir al servidor de aplicaciones incluido:

1. Inicia el Servidor Central (ejecutando la clase principal desde tu IDE en `/serv central` o su empaquetado).
2. Copia los archivos `web.war` y `mobile.war` a la carpeta `apache-tomcat-11.0.13/webapps/`.
3. Inicia Tomcat:
   * **Windows:** `apache-tomcat-11.0.13/bin/startup.bat`
   * **Linux/macOS:** `apache-tomcat-11.0.13/bin/startup.sh`

##  Configuración
Archivo principal: `application.properties` (ubicado en la raíz y/o módulos).
Allí se definen las propiedades del sistema como URLs, puertos y credenciales de base de datos.
Ten en cuenta también las reglas de calidad métrica configuradas de forma global en checkstyle.xml y `pmd-rules.xml`.

##  Datos de Prueba
Los datos de prueba precargados del sistema se encuentran bajo `/serv central/datosPrueba/`:
* `2025Usuarios.csv`
* `2025Eventos.csv`
* `2025Instituciones.csv`
* `2025Categorias.csv`
* `2025Patrocinios.csv`
* `2025Registros.csv`

##  Carga de Datos
Se pueden cargar los masivamente los datos de prueba desde la interfaz Swing de la API del Servidor Central accionando el caso de uso correspondiente para ello.
