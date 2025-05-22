🧠 Kata - Gestión de Lotes con Descuentos Automáticos

🛒 Contexto:

Este microservicio simula una funcionalidad real de Mercadona para gestionar productos con fecha de caducidad corta (como los del Horno o Listo para Comer). Automatiza la aplicación de descuentos en lotes de productos próximos a vencer, evitando pérdidas y optimizando la rotación de stock en tienda.

🎯 ¿Qué hace este servicio?

Permite consultar, registrar y modificar lotes de productos asignados a tiendas.
Aplica automáticamente descuentos a productos si están cerca de su fecha de caducidad.

Asegura que solo se apliquen descuentos si el lote no está caducado, rebajado o retirado.

Expone estadísticas sobre las rebajas aplicadas en cada tienda.

Implementa validaciones para evitar inconsistencias, como duplicados o fechas incorrectas.
Todo ello siguiendo principios de arquitectura limpia (hexagonal), con separación clara entre capas.

🧱 Tecnologías utilizadas
Java 17+

Spring Boot

Arquitectura hexagonal (puertos y adaptadores)

MapStruct para mapeo de objetos

JPA (con base de datos en memoria o PostgreSQL)

Maven

Lombok

Flyway

📐 Estructura del sistema
El microservicio expone una serie de endpoints REST para gestionar los lotes en distintas tiendas. Incluye operaciones como:

📦 Gestión de Lotes
Acción	Endpoint	Descripción

🔍 Consultar lotes próximos a caducar	GET /stores/{storeCode}/batches/near-expiration	Devuelve lotes no caducados ni rebajados, cuya fecha de caducidad esté a 2 días o menos.

💸 Aplicar descuento	PATCH /stores/{storeCode}/batches/{batchId}/discount	Aplica un descuento si el lote cumple los criterios (no caducado, no rebajado, no retirado).

➕ Crear lote	POST /stores/{storeCode}/batches	Registra un nuevo lote para una tienda. Valida duplicados y fechas.

📋 Listar lotes de tienda	GET /stores/{storeCode}/batches	Devuelve todos los lotes, con filtros por producto, estado y paginación.

🚫 Marcar lote como retirado	PATCH /stores/{storeCode}/batches/{batchId}/remove	Marca el lote como retirado para que no esté disponible.

📊 Estadísticas de rebajas	GET /stores/{storeCode}/batches/discount-stats	Muestra métricas de descuentos aplicados por tienda.

🗑️ Eliminar lote	DELETE /stores/{storeCode}/batches/{batchId}	Elimina completamente un lote si no ha sido usado ni rebajado.

✅ Lógica de negocio clave
Un lote caduca cuando la fecha actual supera su expirationDate.

Se puede aplicar un descuento automático si quedan 2 días o menos para su caducidad.

Un lote no puede ser rebajado más de una vez, ni si está retirado.

Se validan condiciones como fechas futuras, duplicidad de lotes y estados incompatibles.

🧪 Buenas prácticas y calidad
Código organizado por capas: dominio, aplicación, infraestructura, controladores.

Tests unitarios incluidos.

(Opcional) Tests de integración y auditoría.

Datos precargados para facilitar pruebas.

🌱 Posibles evoluciones
Soporte para categorías de productos con lógica de descuento distinta.

Configuración de umbrales de caducidad por tienda, sección o tipo de producto.

Notificaciones automáticas a responsables de tienda.

Panel de administración para gestionar reglas de descuento.
