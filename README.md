This repository contains a backend system for a sample company, built using a microservices architecture with Spring Boot. The system is composed of three main services: Order, Inventory, and Product, which work together to manage a simple e-commerce workflow.

## Architecture

The project is a multi-module Maven application designed around a microservices pattern. Each service is a standalone Spring Boot application with its own database.

*   **Order Service**: Handles all order-related operations. It communicates with the Inventory and Product services via RESTful API calls to validate and process new orders. It is also configured as a Kafka producer to publish order events.
*   **Inventory Service**: Manages the stock levels of all items. It provides endpoints for creating, updating, deleting, and querying inventory data.
*   **Product Service**: Manages the product catalog. It stores product information like name, description, and whether an item is available for sale.
*   **Base Module**: A shared module containing common Data Transfer Objects (DTOs) used across different services, such as `OrderEventDTO`.

## Modules

### 1. Order Service (`order`)
*   **Port**: `8081`
*   **Database**: `orderDb` on MySQL
*   **Description**: This service is the entry point for creating customer orders. When a new order is placed, it orchestrates calls to the Product and Inventory services to check product availability and stock quantity before persisting the order. It uses Spring WebFlux `WebClient` for inter-service communication.

### 2. Inventory Service (`inventory`)
*   **Port**: `8080`
*   **Database**: `inventoryDb` on MySQL
*   **Description**: This service is responsible for all inventory management. It maintains the quantity of each item available in stock.

### 3. Product Service (`product`)
*   **Port**: `8082`
*   **Database**: `productDb` on MySQL
*   **Description**: This service manages all product-related information. It provides details about products and their sale status. Note that the application name in `application.properties` is `user`, but the artifact ID is `product`.

## Technologies Used

*   **Framework**: Spring Boot 3
*   **Language**: Java 17+
*   **Build Tool**: Maven
*   **Database**: MySQL
*   **Data Access**: Spring Data JPA
*   **Inter-service Communication**: REST (Spring WebFlux `WebClient`)
*   **Messaging**: Apache Kafka (for order events)
*   **Utilities**: Lombok, ModelMapper

## Prerequisites

*   **Java JDK 17** or newer
*   **Apache Maven**
*   **MySQL Server**
*   **Apache Kafka & ZooKeeper** running on `localhost:9092`

## Getting Started

### 1. Clone the Repository
```sh
git clone https://github.com/chamma08/ABCCompany.git
cd ABCCompany
```

### 2. Configure Databases
Each service requires its own MySQL database. Create the following three databases:
*   `orderDb`
*   `inventoryDb`
*   `productDb`

You will also need to update the database credentials (`spring.datasource.username` and `spring.datasource.password`) in the `application.properties` file for each service:
*   `order/src/main/resources/application.properties`
*   `inventory/src/main/resources/application.properties`
*   `product/src/main/resources/application.properties`

### 3. Build the Project
From the root directory of the project, run the following Maven command to build all modules:
```sh
mvn clean install
```

### 4. Run the Services
Start each microservice in a separate terminal. It is recommended to start the `product` and `inventory` services before the `order` service due to dependencies.

*   **Start Product Service (Port 8082):**
    ```sh
    java -jar product/target/product-1.0-SNAPSHOT.jar
    ```

*   **Start Inventory Service (Port 8080):**
    ```sh
    java -jar inventory/target/inventory-1.0-SNAPSHOT.jar
    ```

*   **Start Order Service (Port 8081):**
    ```sh
    java -jar order/target/order-1.0-SNAPSHOT.jar
    ```

The services will now be running on their respective ports.

## API Endpoints

All endpoints are prefixed with `/api/v1/`.

### Product Service (`http://localhost:8082`)
| Method | Endpoint                    | Description                     |
|--------|-----------------------------|---------------------------------|
| `GET`  | `/getproducts`              | Get all products                |
| `GET`  | `/getproduct/{productId}`   | Get a product by its ID         |
| `POST` | `/createproduct`            | Create a new product            |
| `PUT`  | `/updateProduct`            | Update an existing product      |
| `DELETE`| `/deleteproduct/{productId}`| Delete a product by its ID      |

### Inventory Service (`http://localhost:8080`)
| Method | Endpoint                | Description                       |
|--------|-------------------------|-----------------------------------|
| `GET`  | `/getinventories`       | Get all inventory records         |
| `GET`  | `/item/{itemId}`        | Get inventory details for an item |
| `POST` | `/createinventory`      | Create a new inventory record     |
| `PUT`  | `/updateinventory`      | Update an existing inventory record|
| `DELETE`| `/delete/{id}`          | Delete an inventory record by ID  |

### Order Service (`http://localhost:8081`)
| Method | Endpoint                  | Description                   |
|--------|---------------------------|-------------------------------|
| `GET`  | `/getorders`              | Get all orders                |
| `POST` | `/createorder`            | Create a new order            |
| `PUT`  | `/updateorder`            | Update an existing order      |
| `DELETE`| `/deleteorder/{orderId}`  | Delete an order by its ID     |
