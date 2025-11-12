# ChallengeTektonApp

Aplicación que recibe una petición con dos números para calcular un resultado (y un porcentaje asociado) y guarda / expone un historial de las operaciones.

Requisitos
---------
- Java 21
- Maven 3.8+
Opcional:
- PostgreSQL (si desea persistir historial en DB)
- Redis (si está usando caching configurado)

Build y ejecución
-----------------
1) Compilar y empaquetar:

```bash
mvn clean package -DskipTests
```

2) Ejecutar el JAR:

```bash
java -jar target/ChallengeTektonApp-1.0.0.jar
```

Alternativa con Docker Compose (si quiere levantar dependencias como Postgres/Redis):

```bash
# Revisa el archivo docker-compose.yml y ajústalo según tus vars de entorno
docker-compose up -d
```

Endpoints
---------
1) POST /api/calculate
- URL: `http://localhost:8080/api/calculate`
- Método: POST
- Body (JSON):

```json
{
  "firstNumber": 12.5,
  "secondNumber": 7.5
}
```

- Respuesta (JSON) — ejemplo de la forma esperada (los valores dependen de la implementación del servicio):

```json
{
  "result": 20.0,
  "percentage": 37.5
}
```

Curl de ejemplo:

```bash
curl -s -X POST http://localhost:8080/api/calculate \
  -H "Content-Type: application/json" \
  -d '{"firstNumber":12.5,"secondNumber":7.5}'
```

2) GET /api/history
- URL: `http://localhost:8080/api/history`
- Método: GET
- Parámetros query (opcionales):
  - `page` (default `0`)
  - `size` (default `10`)

Ejemplo:

```bash
curl -s "http://localhost:8080/api/history?page=0&size=5"
```

Respuesta: Spring Data `Page<HistoryRecord>` con elementos que incluyen:
- `id` (Long)
- `date` (fecha/hora)
- `endpoint` (String)
- `params` (String)
- `result` (String)
- `error` (String)
