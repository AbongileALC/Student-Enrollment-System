# 🎓 Student Enrollment System
A Java Swing desktop application for managing student course enrollments with role-based access control.

## Screenshots
| Login Page | Admin Dashboard | Student Enrollment |
|---|---|---|
| *(add screenshot)* | *(add screenshot)* | *(add screenshot)* |

## Features
**Admin Access**
- Add and manage students (student number, name, email, password)
- Add and manage courses (course code, title)
- Full control via tabbed Admin Dashboard

**Student Access**
- Log in with student credentials
- Load personal info by student number
- Enroll in available courses
- Duplicate enrollment prevention

## Technologies Used
| Technology | Version |
|---|---|
| Java (JDK) | 17 |
| Java Swing | Built-in |
| Apache Derby | 10.16.1.1 |
| Maven | Bundled with NetBeans |
| NetBeans | 25 (recommended) |

## Project Structure
src/main/java/za/ac/cput/studentenrollmentapp/
│
├── gui/
│   ├── Welcome.java
│   ├── AdminPage.java
│   └── StudentPage.java
│
├── domain/
│   ├── Student.java
│   ├── Course.java
│   ├── Enrollment.java
│   └── Login.java
│
├── dao/
│   ├── StudentDAO.java
│   ├── CourseDAO.java
│   ├── EnrollmentDAO.java
│   └── LoginDAO.java
│
└── connection/
    └── DBConnection.java
    
## How to Run (NetBeans)
1. Open the project in NetBeans 25
2. Start the Apache Derby network server on localhost port 1527
3. Right-click the project in the Projects panel
4. Select **Run** or press F6
5. The login window will appear

## How to Run (Terminal)
From the project root directory:
mvn clean package
java -jar target/studentenrollmentapp.jar

## Database Setup
The app connects to a Derby network database called `StudentEnrollmentDB`.

Connection details:
- **URL:** `jdbc:derby://localhost:1527/StudentEnrollmentDB`
- **Username:** Administrator
- **Password:** password

The following tables are required:

```sql
CREATE TABLE Login (
    email VARCHAR(100) PRIMARY KEY,
    password VARCHAR(100),
    role VARCHAR(20)
);

CREATE TABLE Student (
    student_number VARCHAR(20) PRIMARY KEY,
    student_name VARCHAR(100),
    email VARCHAR(100),
    password VARCHAR(100),
    role VARCHAR(20)
);

CREATE TABLE Course (
    course_code VARCHAR(20) PRIMARY KEY,
    title VARCHAR(100)
);

CREATE TABLE Enrollment (
    student_number VARCHAR(20),
    name VARCHAR(100),
    course VARCHAR(100)
);
```

## User Roles
| Role | Access |
|---|---|
| Admin | Add students, add courses, manage system |
| Student | View own info, enroll in courses |

## How It Works
1. Launch the app — the Login screen appears
2. Enter email and password
3. System checks role — Admin opens Admin Dashboard, Student opens Student Page
4. Admin can add courses and register new students via tabbed interface
5. Students enter their student number to load their info, then select and enroll in a course
6. Duplicate enrollments are automatically prevented

## My Role
Implemented the `EnrollmentDAO` and `CourseDAO` — full CRUD operations including duplicate enrollment prevention, transaction management, and database rollback handling.

## Contributors
- Athini Ngquke — System architecture, GUI, Login & Student DAO
- Abongile Phandle — Enrollment DAO, Course DAO, database integration
