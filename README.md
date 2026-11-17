# Backend de Bitácora Escolar

API REST en Spring Boot para la app Android. Requiere Java 21 y MySQL.

## Ejecutar localmente

Configura MySQL en `localhost:3306` y luego ejecuta `./mvnw spring-boot:run` (Windows: `mvnw.cmd spring-boot:run`). Por defecto usa la base `viktor`, usuario `root` y contraseña vacía; se puede cambiar con `DB_URL`, `DB_USERNAME` y `DB_PASSWORD`. El catálogo inicial incluye cuatro cursos y cuatro asignaturas.

Para Android Emulator usa `http://10.0.2.2:8080/` como base URL. En un teléfono físico, usa la IP local del computador. Android debe permitir HTTP sin TLS en desarrollo local.

## Autenticación

Registro e inicio de sesión devuelven `user` y `token`. Las contraseñas se guardan con BCrypt. Para las rutas privadas envía `Authorization: Bearer <token>`. Los tokens son de sesión en memoria y dejan de servir cuando se reinicia el backend.

Roles: `PROFESOR`, `ALUMNO`, `APODERADO`.

## Rutas

| Método | Ruta | Acceso | Uso |
|---|---|---|---|
| POST | `/api/auth/register` | Público | Crear cuenta |
| POST | `/api/auth/login` | Público | Iniciar sesión |
| GET | `/api/courses` | Público | Listar cursos |
| GET | `/api/subjects` | Público | Listar asignaturas |
| GET | `/api/logbooks?courseId=1&subjectId=1` | Autenticado | Listar bitácoras, opcionalmente filtradas |
| GET | `/api/logbooks/{id}` | Autenticado | Ver bitácora |
| POST | `/api/logbooks` | Docente | Crear bitácora |
| PATCH | `/api/logbooks/{id}/status?closed=true` | Docente propietario | Cerrar o reabrir bitácora |
| GET | `/api/logbooks/{id}/entries` | Docente propietario | Ver entregas de esa bitácora |
| POST | `/api/logbooks/{id}/entries` | Estudiante | Enviar una entrega |
| PATCH | `/api/entries/{id}/review` | Docente propietario | Guardar comentario y estado de revisión |
| GET | `/api/guardian/entries?email=alumno@ejemplo.cl` | Apoderado | Buscar entregas por correo estudiantil; también acepta `name` |

Ejemplo de registro:

```json
{
  "firstName": "Ana",
  "lastName": "Pérez",
  "email": "ana@ejemplo.cl",
  "password": "clave123",
  "role": "ALUMNO",
  "courseId": 1
}
```

Las entregas usan los campos `contents`, `activities`, `learnings`, `doubts`, `photoUri`, `latitude` y `longitude`. `photoUri` se almacena como texto; esta API aún no sube archivos de imagen.

