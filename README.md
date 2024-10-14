# RaahiReserve – Bus Booking Service

**Simplified bus booking and route management system for seamless travel experiences.**

RaahiReserve is a comprehensive Spring Boot application designed to simplify bus booking and route management for a seamless travel experience. The system provides features such as passenger management, seat selection, booking summary breakdown, and payment integration. Designed for scalability and optimized for performance, the app handles real-time bus seat availability with a clean and intuitive interface.

## Features
- **Core Functionality:** Book trips from selected source and destination locations with ease, assign buses and seats based on passengers' preferences, and mark passengers as traveled on their scheduled date (travel simulation).
- **Payment Integration:** In-house payment gateway with multiple methods for a smooth booking payment experience, featuring a timeout mechanism to enhance security.
- **Performance Optimization:** Java multithreading is utilized to reduce server startup time.
- **Security:** Endpoints are secured using Spring Security to prevent unauthorized access.
- **Entity Relationships:** Database schema is thoughtfully designed with strategic entity relationships and mappings.
- **Friendly Features:** Easily view past booking details with just a few clicks.
- **Detailed Booking Summary:** Booking summary provides pricing breakdown with future plug-ins for GST/taxes and enhanced reports.

## Technologies Used
- **Backend:** Java, Spring Boot, Hibernate, JPA
- **Frontend:** Thymeleaf, Bootstrap
- **Database:** MySQL on AWS RDS
- **Hosting:** AWS Elastic Beanstalk
- **Caching (Future):** Redis (session management, object storage)

## Installation
Clone the repository and install dependencies:

```bash
git clone https://github.com/Divya0319/RaahiReserve.git
cd RaahiReserve
mvn clean install
```
Make sure to configure your `.env` or `application.properties` with your AWS and database credentials.

## Usage
Run the application:
```bash
mvn spring-boot:run
```

The app will be available at http://localhost:8092.

* Default branch is main.
* To see the running application with its UI, 
```bash
git clone thymeleaf-integration https://github.com/Divya0319/RaahiReserve.git
``` 
* To check out the thymeleaf-integration branch:
```bash
git checkout thymeleaf-integration
```
* The database is initialised during application startup with some sample data.
* To see the performance gain of this initialisation, clone the multithreading branch:
```bash
git clone multithreading https://github.com/Divya0319/RaahiReserve.git
``` 
* Compare the server startup times with and without multithreading (it’s fascinating!).

## Performance Notes
*Previously deployed on AWS Elastic Beanstalk with a MySQL database hosted on AWS RDS.*

## Contributing
Pull requests are welcome. For major changes, please open an issue to discuss potential updates before making them.
