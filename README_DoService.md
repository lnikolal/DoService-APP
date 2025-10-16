# 🛠️ DoService – Service Intervention Management System

**DoService** is a web application developed as a capstone project.  
Its main purpose is to **centralize the management of service interventions**, technicians, users, and contracts.

The application was built in a short timeframe, leaving **significant room for refactoring, optimization, and improvements** — especially in terms of **code organization** and **user interface**.  

Most of the **key features are implemented**, providing a functional system for managing users, technicians, service tasks, contracts, and files.

---

## 📂 Project Contents

- **Database dump with dummy data** — for quick testing.  
- **ERD diagram image** — visual representation of entity relationships.  
- ⚠️ *Note:* There is an additional relation between `Technician` and `User` not explicitly shown in the ERD but reflected in the configuration via:
  ```properties
  spring.jpa.hibernate.ddl-auto=update
  ```

---

## 🚀 Current Functionality

- **Admin** can view and manage all users and data.  
- **Manager** has almost all admin privileges except for user deletion.  
- **Technician** can view only their own tasks and basic data.  
- **Authentication & authorization** handled by Spring Security with role-based route protection.  
- Application structure is ready for further enhancements at all layers.

---

## 🔑 Core Features

### 1. User & Role Management
- Create, edit, and delete users.  
- Authentication & authorization (Spring Security, JWT planned for future versions).  
- Role assignment: `ADMIN`, `MANAGER`, `TECHNICIAN`.  
- Privilege control based on roles.

### 2. Technician Management
- Add, edit, and view technician profiles.  
- Technician status: *active* / *inactive* (soft delete).  
- Assign position and location.  
- Link technicians to `User` accounts.  
- View all tasks and contracts assigned to a technician.

### 3. Service Task Management
- Create, edit, view, and delete tasks.  
- Link tasks to client, service type, status, and multiple technicians.  
- Track status: `OPEN`, `IN_PROGRESS`, `CANCELLED`, `COMPLETED`.  
- Record assignment and completion dates.  
- Upload and manage related images and documents.

### 4. Contract Management
- Create, edit, and view contracts.  
- Link contracts to users or companies.  
- Track contract status.  
- Upload and associate PDF documents with contracts.

### 5. File & Image Management
- Upload, download, and delete files (images, documents).  
- Associate files with tasks, contracts, or technicians.  
- Centralized file storage and management.

### 6. Web Interface & REST API
- **Thymeleaf**: Admin panel & dashboard for easy management.  
- **REST API**: For external integrations (frontend or mobile apps).  
- Dashboard includes task status statistics and cards.

### 7. Security
- Basic login and authentication (*JWT planned for future*).  
- Role-based authorization.  
- Soft delete on all key entities.  
- Simple error handling with entry point redirection.

---

## 🧱 Architecture & Structure

- Modular project structure (`entities`, `services`, `repositories`, `controllers`).  
- Automatic tracking of `createdAt` and `updatedAt` timestamps for all records.  
- Built to support future extensions (e.g., mobile app integration or advanced analytics).  
- Soft delete implemented across all major entities.

---

## 🧭 Future Improvements

- Full JWT authentication & refresh token flow.  
- Better error handling and response structures.  
- Improved UI/UX with more modern frontend.  
- Additional reporting and analytics.  
- Integration with external systems (e.g., CRM, mobile apps).

---

## 🧰 Tech Stack

- **Backend:** Spring Boot, Spring Security, JPA/Hibernate  
- **Frontend:** Thymeleaf, Android App DoService Admin Interface  
- **Database:** MySQL  
- **Build tool:** Maven  
- **Security:** Role-based access control, basic authentication  
- **API:** RESTful endpoints for integration

---

## 📸 Preview

- ERD diagram (entity relationships)  
- Dashboard with task status cards  
- User & technician management interface

---

## 📝 License

This project is released for educational and demonstrational purposes.  
Refactoring and further development are encouraged.
