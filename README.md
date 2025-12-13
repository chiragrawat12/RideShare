# 🚗 RideShare - Car Pooling System

A web-based carpooling platform connecting drivers and passengers to reduce traffic congestion, lower travel costs, and promote sustainable transportation.

---

## 📋 Quick Overview

**RideShare** enables drivers to post ride offers and passengers to search and book rides on shared routes. The system prioritizes **simplicity, security, and extensibility** through proper software architecture and design patterns.

### Key Features
- **User Registration & Verification** - Secure registration for drivers and passengers
- **Ride Management** - Host drivers can post rides with route, time, and seat details
- **Ride Search & Booking** - Passengers can search and book available rides
- **Ratings & Reviews** - Users can rate each other after completing rides
- **Payment Integration** - Digital payment support and receipts
- **Notifications** - Push notifications for booking confirmations and updates

---

## 🛠️ Technology Stack

| Component | Technology |
|-----------|-----------|
| **Backend** | Java, Spring Boot |
| **Database** | MySQL |
| **Server** | Embedded Tomcat |
| **Architecture** | MVC + Microservices |
| **Design Patterns** | Observer, Factory Method, Singleton, Decorator, Strategy, Interceptor, Command |
| **Version Control** | GitHub |

---

## 🏗️ Architecture Overview

### Two-Layer Architecture:
1. **MVC Pattern** - Separates concerns at the application level (Model, View, Controller)
2. **Microservices** - Three independent services for scalability and extensibility:
    - **User Service** - Registration, authentication, profile management
    - **Ride Service** - Ride posting, searching, booking, matching
    - **Payment Service** - Transaction processing, billing, receipts

### API Gateway
Routes all client requests to appropriate microservices, centralizing authentication and security.

---

## 🚀 Getting Started

### Prerequisites
- **Java** 11 or higher
- **MySQL** 8.0 or higher
- **Maven** 3.6+
- **Git**

---

## 📊 Database Schema

Key entities:
- **User** - Stores driver and passenger information
- **HostDriver** - Driver-specific details (license, verification status)
- **Ride** - Ride offers posted by drivers
- **Booking** - Passenger bookings for rides
- **Payment** - Transaction records
- **Rating** - User ratings and reviews

---

## 🔒 Security & Quality

- **Authentication** - Secure user verification via email/phone
- **Authorization** - Role-based access control
- **Data Validation** - Input validation at controller level
- **Extensibility** - Modular design allows easy feature additions
- **Maintainability** - Clear separation of concerns

---

## 📋 Non-Functional Requirements

- **Scalability** - Handles growing users and concurrent requests
- **Availability** - Redundancy ensures minimal downtime
- **Extensibility** - Modular architecture for new features
- **Serviceability** - Simplified debugging and maintenance

---

## 👥 Team

**Project: SOLID Four** (CS6451 - Advanced Software Design)

- **Chirag Singh** (25009885) - Project Manager & Systems Analyst
- **Ganesh Sudhir Kotalwar** (25142682) - Technical Lead & Tester
- **Gladwin Dominic Joseph** (25040758) - Architect & Designer
- **Kiran Kidecha** (25030965) - Business Analyst & DevOps

**Instructor:** Professor J.J Collins

---

## 🚀 Future Enhancements

- Advanced driver-passenger matching algorithm
- Real-time GPS tracking with live map updates
- Referral program and loyalty rewards
- Multiple payment gateway integration
- SOS/Emergency contact system
- Ride prioritization and preferences
- Mobile app (iOS/Android)

---

## 📄 License

This project is a university assignment and is available for educational purposes.

---

## 📧 Contact

For questions or contributions, please reach out through the GitHub repository.

**Repository:** https://github.com/chiragrawat12/RideShare