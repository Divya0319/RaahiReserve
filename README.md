# RaahiReserve – Bus Booking Service

**Simplified bus booking and route management system for seamless travel experiences.**

RaahiReserve is a bus booking system built with Spring Boot, providing features such as passenger management, seat selection, booking summary breakdown, and payment integration. Designed for scalability and optimized for performance, the app handles real-time bus seat availability with a clean and intuitive interface.

## Features
- Passenger booking and seat selection
- Real-time bus availability updates
- Payment integration with multiple methods
- Detailed booking summary and pricing breakdown
- Multithreaded data initialization for faster performance
- Future plug-ins for GST/taxes and enhanced reports

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
* ``` 
* and `git checkout` to it.
* Database is initialised during application startup with some sample data.
* To see the performance gain of this initialisation,
```bash
git clone multithreading https://github.com/Divya0319/RaahiReserve.git
 ``` 
* and `git checkout` to it, and compare the server startup times with and without multithreading (its fascinating!!).

## Contributing
Pull requests are welcome. For major changes, please open an issue to discuss potential updates before making them.
