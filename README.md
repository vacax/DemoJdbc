# Demo JDBC + H2 (modo servidor)

Proyecto de ejemplo que muestra cómo usar **JDBC** contra una base de datos **H2**
arrancada en **modo servidor (TCP)**, aplicando un CRUD sencillo sobre una entidad
`Estudiante` y patrones básicos como *Singleton* para la gestión de la conexión.

## Características

- Arranque de H2 en modo servidor TCP embebido en la propia aplicación.
- **Reutilización de instancias externas**: si ya hay una instancia de H2 escuchando
  en el puerto `9092`, la aplicación no falla; detecta la instancia y la reutiliza.
  Así puedes arrancar la app en modo servidor aunque otra instancia ya esté activa.
- CRUD completo (crear, listar, consultar, actualizar, borrar) sobre `Estudiante`.
- Uso de `PreparedStatement` con parámetros para evitar inyección SQL.

## Requisitos

| Componente | Versión |
|------------|---------|
| JDK        | 25      |
| Gradle     | 9.3 (incluido vía *wrapper*) |
| H2         | 2.3.232 |
| JUnit      | 5.11.4  |

> No necesitas instalar Gradle: el proyecto incluye el *Gradle Wrapper*
> (`gradlew` / `gradlew.bat`), que descarga la versión correcta automáticamente.

## Ejecución

Linux / macOS:

```bash
./gradlew run
```

Windows:

```bat
gradlew.bat run
```

Al ejecutarse, la aplicación:

1. Arranca el servidor H2 en `localhost:9092` (o reutiliza el que ya esté activo).
2. Verifica la conexión.
3. Crea la tabla `ESTUDIANTE` si no existe.
4. Inserta, lista, consulta y actualiza estudiantes de ejemplo.
5. Detiene el servidor H2 **solo si lo arrancó esta aplicación**.

## Conexión a la base de datos

La cadena de conexión utilizada es:

```
jdbc:h2:tcp://localhost/~/mibasedatos
```

El fichero de la base de datos se crea en el directorio del usuario (`~/mibasedatos`).
Usuario por defecto `sa` y contraseña vacía.

## Estructura del proyecto

```
src/main/java/com/avathartech/demojdbc
├── encapsulacion
│   └── Estudiante.java          # Entidad (POJO)
├── services
│   ├── BootStrapServices.java   # Arranque/parada de H2 y creación de tablas
│   ├── DataBaseServices.java    # Gestión de la conexión (Singleton)
│   └── EstudianteServices.java  # Operaciones CRUD
└── main
    └── Main.java                # Punto de entrada
```
