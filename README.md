🧠 Kata - Batch Management with Automatic Discounts
🛒 Context
This microservice simulates a real functionality used at Mercadona to manage products with short expiration dates (such as Bakery or Ready-to-Eat items). It automates the application of discounts to product batches that are close to expiration, helping reduce waste and optimize stock rotation in stores.

🎯 What does this service do?
Allows querying, registering, and updating product batches assigned to specific stores.

Automatically applies discounts to products close to their expiration date.

Ensures that discounts are only applied if the batch is not expired, already discounted, or marked as removed.

Exposes discount statistics per store.

Implements validations to prevent inconsistencies, such as duplicate entries or invalid dates.

Follows clean architecture principles (hexagonal) with clear separation of concerns between layers.

🧱 Technologies Used
Java 17+

Spring Boot

Hexagonal architecture (Ports and Adapters)

MapStruct for object mapping

JPA (with in-memory database or PostgreSQL)

Maven

Lombok

Flyway

📐 System Structure
The microservice exposes a set of REST endpoints to manage product batches across different stores. It includes the following operations:

📦 Batch Management
Action	Endpoint	Description
🔍 Get near-expiring batches	GET /stores/{storeCode}/batches/near-expiration	Returns batches that are not expired or discounted and expire in 2 days or less.
💸 Apply discount	PATCH /stores/{storeCode}/batches/{batchId}/discount	Applies a discount if the batch meets all conditions (not expired, not discounted, not removed).
➕ Create batch	POST /stores/{storeCode}/batches	Registers a new batch for a store. Validates duplicate entries and expiration dates.
📋 List store batches	GET /stores/{storeCode}/batches	Returns all batches, with filters by product, status, and pagination support.
🚫 Mark batch as removed	PATCH /stores/{storeCode}/batches/{batchId}/remove	Marks a batch as removed so it’s no longer available in-store.
📊 Discount statistics	GET /stores/{storeCode}/batches/discount-stats	Provides metrics about the discounts applied by store.
🗑️ Delete batch	DELETE /stores/{storeCode}/batches/{batchId}	Completely deletes a batch if it hasn’t been used or discounted.

✅ Key Business Logic
A batch is considered expired when the current date is past its expirationDate.

A batch is eligible for automatic discount if it will expire in 2 days or less.

A batch cannot be discounted more than once or if it is already marked as removed.

Validations include ensuring future expiration dates, avoiding duplicates, and checking valid status transitions.

🧪 Best Practices and Code Quality
Clean separation of layers: domain, application, infrastructure, controllers.

Includes unit tests.

(Optional) Integration testing and auditing.

Preloaded demo data for testing and development.

🌱 Potential Evolutions
Batch management by product categories with custom discount rules.

Configurable expiration thresholds per store, section, or product type.

Automated notifications to store managers.

Admin panel to manage discount rules.
