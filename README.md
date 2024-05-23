# CRUD Application with MongoDB

## Descripción

Esta es una aplicación de escritorio CRUD (Crear, Leer, Actualizar, Eliminar) desarrollada en JavaFX, que se conecta a una base de datos MongoDB. La aplicación permite gestionar registros de clientes, incluyendo su ID, nombre y facturación.

## Características

- **Crear:** Permite crear nuevos registros de clientes.
- **Leer:** Permite leer y mostrar todos los registros existentes en una tabla.
- **Actualizar:** Permite actualizar los registros existentes.
- **Eliminar:** Permite eliminar registros específicos.

## Prerrequisitos

- Java Development Kit (JDK) 11 o superior.
- MongoDB instalado y ejecutándose localmente o en un servidor accesible.
- Maven para la gestión de dependencias.

## Instalación

1. **Clonar el repositorio:**

    ```bash
    git clone https://github.com/tu_usuario/tu_repositorio.git
    cd tu_repositorio
    ```

2. **Configurar MongoDB:**

    Asegúrate de tener MongoDB instalado y ejecutándose. Puedes usar la configuración por defecto (localhost:27017).

3. **Compilar y ejecutar la aplicación:**

    Usa Maven para compilar y ejecutar la aplicación:

    ```bash
    mvn clean install
    mvn javafx:run
    ```

## Uso

1. **Crear un Registro:**
    - Ingresa un ID único, un nombre y una facturación en los campos correspondientes.
    - Haz clic en el botón "Crear".

2. **Leer Registros:**
    - Haz clic en el botón "Leer" para cargar y mostrar todos los registros en la tabla.

3. **Actualizar un Registro:**
    - Ingresa el ID del registro que deseas actualizar.
    - Ingresa los nuevos valores para el nombre y la facturación.
    - Haz clic en el botón "Actualizar".

4. **Eliminar un Registro:**
    - Ingresa el ID del registro que deseas eliminar.
    - Haz clic en el botón "Eliminar".

## Arquitectura del Código

### HelloController.java

Este es el controlador principal de la aplicación, encargado de manejar las acciones del usuario y la interacción con la base de datos.

### MongoDBConnection.java

Este archivo contiene la lógica para establecer la conexión con la base de datos MongoDB.

### hello-view.fxml

El archivo FXML define la interfaz gráfica de usuario (GUI) de la aplicación, incluyendo los campos de texto, botones y la tabla.

## Estilo y Diseño

La interfaz de usuario está diseñada para ser coherente y fácil de usar. Todos los componentes tienen el mismo ancho y están alineados para proporcionar una apariencia uniforme. La aplicación también maneja errores de forma efectiva, mostrando mensajes de alerta cuando ocurre un problema.

## Contribuir

Si deseas contribuir a este proyecto, por favor sigue estos pasos:

1. Haz un fork del repositorio.
2. Crea una nueva rama (`git checkout -b feature/nueva-caracteristica`).
3. Realiza tus cambios y haz commit (`git commit -am 'Añadir nueva característica'`).
4. Haz push a la rama (`git push origin feature/nueva-caracteristica`).
5. Abre un Pull Request.

## Licencia

Este proyecto está licenciado bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

## Autor

- Jorge Martin - 

