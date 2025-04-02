# COSC3506-001XE
This repository is for developing a Project Software Engineering 


# **Project Guidelines**

## **Project Scope**
- Your project should focus on implementing the **core functionalities** of the assigned system.
- Ensure you understand the **problem statement** thoroughly and plan your approach accordingly.
- The system must incorporate **object-oriented design principles**, including:
  - **Classes & Objects**
  - **Inheritance**
  - **Encapsulation**
  - **Polymorphism**
  - **Abstraction**

## **User Roles & Access Control**
- Each project should include **at least three distinct user roles** (e.g., **Admin, Staff, Customer**) with different levels of access and permissions.
- Implement a **user authentication and authorization system** to ensure that users can log in and access only the features relevant to their roles.

## **Code Structure**
- Organize your code using **packages and classes** in a modular fashion.
- Separate functionality into different layers:
  - **Business Logic Layer**
  - **Data Access Layer**
  - **Presentation Layer**
- Follow **SOLID principles** and use **design patterns** where applicable to maintain **clean and scalable code**.

## **Database Integration**
- Your project should integrate with a **relational database** to store persistent data (e.g., users, transactions, inventory).
- Design and implement appropriate tables for different entities, ensuring proper relationships:
  - **One-to-Many**
  - **Many-to-Many**
- Use **JDBC (or an ORM framework)** to interact with the database for **CRUD operations**.

## **UI & User Interaction**
- Implement a **simple graphical user interface (GUI)** for user interaction.
- Ensure the **UI is intuitive and user-friendly**.
- Users should be able to **navigate between functionalities easily**, and actions (e.g., viewing data, updating records) should be clear.

## **Exception Handling & Validation**
- Implement **exception handling** to manage errors (**e.g., invalid input, database connectivity issues**) and prevent application crashes.
- Display **graceful error messages** to users.
- Validate all **user inputs** to ensure **data integrity**, such as:
  - **Required fields**
  - **Correct formats (e.g., email, phone numbers, dates)**

## **Testing**
- Write **unit tests** for core components of the application to ensure correctness.
- Test each **user role separately** to verify that permissions are correctly enforced.

## **Version Control**
- Use **Git** for version control.
- Regularly commit your code and write **meaningful commit messages**.
- Ensure your project is **well-documented**, including:
  - **README.md** file
  - **Installation steps**
  - **Usage instructions**

## **Documentation**
- Document your code with **comments**, explaining the purpose of each **class and method**.
- Provide a **brief architectural description** of the project, including **class diagrams** if necessary.

## **Team Collaboration (If Applicable)**
- Assign tasks clearly and ensure **effective communication** among team members.
- Use **collaborative tools** such as:
  - **GitHub** (for version control)
  - **Trello** (for task management)
  - **Slack/Discord** (for team discussions)
- Maintain a structured workflow to **merge code effectively** and track project progress.

---


# JavaFX Car Rental System

## Setting Up the JavaFX-Based Car Rental System on Windows

---

## 1. Install Required Software

Before setting up the project, you need to install the following tools:

### 1.1 Install Java Development Kit (JDK 21)

1. Download JDK 21 (OpenJDK 23.0.2) from the official website:  
   👉 https://jdk.java.net/21/
2. Run the installer and follow the on-screen instructions.
3. Verify installation:

```bash
java -version
```

Expected Output:

```bash
openjdk version "21" ...
```

---

### 1.2 Install Eclipse IDE for Java Developers

- Download from: https://www.eclipse.org/downloads/
- Install and launch Eclipse.

---

### 1.3 Install Git for Windows

1. Download: https://git-scm.com/download/win
2. Verify installation:

```bash
git --version
```

---

### 1.4 Install Apache Maven

1. Download from: https://maven.apache.org/download.cgi  
2. Extract to: `C:\maven`
3. Set environment variables:
    - `MAVEN_HOME = C:\maven`
    - Add `C:\maven\bin` to system `Path`
4. Verify installation:

```bash
mvn -version
```

---

### 1.5 Install JavaFX 21.0.6

1. Download SDK: https://gluonhq.com/products/javafx/
2. Extract to: `C:\javafx-sdk-21.0.6`
3. Set environment variable:
    - `PATH_TO_FX = C:\javafx-sdk-21.0.6\lib`
4. Verify:

```bash
echo %PATH_TO_FX%
```

---

### 1.6 Install MySQL (Database)

#### Step 1: Install MySQL Community Edition

- Download Installer: https://dev.mysql.com/downloads/installer/
- Install:
  - MySQL Server
  - MySQL Workbench
  - MySQL Shell
- Set a root password during setup

#### Step 2: Verify MySQL Installation

```bash
mysql -u root -p
```

> Enter your root password. If you see `mysql>`, you're good to go.

#### Step 3: Create the Database

```sql
CREATE DATABASE carrental;
USE carrental;
```

> Keep MySQL running for the app to connect.

---

## 2. Clone and Set Up the Project

### 2.1 Clone the GitHub Repository

```bash
cd C:\Users\YourUsername\Documents
git clone https://github.com/AUCOS3506/COSC3506-001XE.git
cd COSC3506-001XE
```

### 2.2 Import into Eclipse

1. File → Import... → Existing Maven Projects
2. Browse to `CarRentalSystem` folder
3. Finish

### 2.3 Update Maven Dependencies

- Right-click project → Maven → Update Project → OK

---

## 3. Configure and Run the Application

### 3.1 Add VM Arguments for JavaFX

1. Run → Run Configurations...
2. Select your Java Application (e.g., `MainApp`)
3. Under **VM Arguments**, add:

```bash
--module-path C:\javafx-sdk-21.0.6\lib --add-modules javafx.controls,javafx.fxml
```

4. Apply → Run

---

### 3.2 Run the App from Eclipse

- Right-click `MainApp.java` → Run As → Java Application  
> The login screen should appear.

---

### 3.3 Run Using Maven (Optional)

```bash
mvn clean javafx:run
```

Or fallback command:

```bash
mvn exec:java -Dexec.mainClass="com.carrental.MainApp"
```

---

## 4. Scene Builder Integration (Optional)

### Install Scene Builder

- Download: https://gluonhq.com/products/scene-builder/

### Connect with Eclipse

- Right-click any `.fxml` → Open With → Scene Builder  
- You can design your UI visually, and changes will reflect in Eclipse.

---

## License

This project is licensed under Eduation purpose of Algoma University.

---

## Contact

Created by **Deepasree Meena Padmanabhan, Pujan Bhuva, Simranjeet Kaur & OgheneRukevwe Esegba**  
For questions, reach out at: https://github.com/Deepasree-MP, https://github.com/heisenbug-io , https://github.com/dhillonsimran26 and https://github.com/rukky0123




