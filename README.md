# Smart Complaint and Grievance Redressal System

## 📌 Project Overview

The **Smart Complaint and Grievance Redressal System** is a Java-based desktop application designed to simplify and streamline the process of registering, managing, tracking, and resolving complaints in educational institutions.

The system provides a centralized platform where administrators can manage student complaints, assign departments, update complaint statuses, monitor progress, and generate reports.

The main objective of this project is to reduce manual complaint management, improve transparency, and ensure an organized grievance redressal process.

## 🎯 Objectives

- Digitize the traditional manual complaint management process.
- Generate unique IDs for every registered complaint.
- Simplify complaint searching and tracking.
- Enable administrators to manage complaint statuses.
- Maintain complaint history and resolution details.
- Provide statistical reports for better decision-making.

## ✨ Key Features

- **Admin Login:** Secure administrator login to access the management system.
- **Complaint Registration:** Register complaints with student details, descriptions, categories, departments, and priorities.
- **Unique Complaint ID:** Automatically generate a unique ID for every complaint.
- **Complaint Management:** View, search, filter, and manage registered complaints.
- **Status Management:** Update complaints as Submitted, In Progress, or Resolved.
- **Complaint History:** Maintain complaint updates, remarks, and timestamps.
- **Dashboard:** Display total, pending, and resolved complaint statistics.
- **Data Visualization:** Present complaint statistics through pie charts and department-wise bar charts.
- **Report Generation:** Generate complaint reports in HTML format.
- **CSV Export:** Export complaint records for further analysis.
- **Data Persistence:** Store and retrieve complaint information using Java file handling and serialization.
- **Input Validation:** Validate user inputs and prevent duplicate complaints.

## 🛠️ Technologies Used

| Technology | Purpose |
|---|---|
| Java | Core programming language |
| Java Swing | Graphical User Interface |
| Java AWT | GUI layouts and graphics |
| ArrayList | Managing complaint records |
| File Handling | Storing and retrieving data |
| Serialization | Saving complaint objects |
| HTML | Generating reports |
| CSV | Exporting complaint records |

## 📂 Project Structure

```text
SmartComplaintSystem/
│
├── src/
│   ├── Main.java
│   ├── WelcomeScreen.java
│   ├── Login.java
│   ├── Dashboard.java
│   ├── Complaint.java
│   ├── ComplaintOperations.java
│   ├── ComplaintManager.java
│   ├── ComplaintForm.java
│   ├── ComplaintTable.java
│   ├── ComplaintHistory.java
│   ├── FileHandler.java
│   ├── ReportGenerator.java
│   ├── Charts.java
│   └── CSVExporter.java
│
└── README.md
```

## ⚙️ How to Run the Project

**Prerequisites:**
- Java Development Kit (JDK) installed.
- Any Java-compatible operating system.

**Steps:**

1. Clone or download this repository.
2. Open the project folder.
3. Open the `src` directory in Command Prompt.
4. Compile the Java files:

```bash
javac *.java
```

5. Run the application:

```bash
java Main
```

## 🔐 Demo Login Credentials

```text
Username: admin
Password: admin123
```

*Note: These are demonstration credentials. Change the default credentials before using the application in a real environment.*

## 💡 Problem Solved

Traditional complaint management systems often depend on manual records, making it difficult to track complaints, monitor their progress, and maintain proper documentation.

This project addresses these challenges by providing a digital complaint management platform that improves accessibility, organization, transparency, and administrative efficiency.

## 🚀 Future Enhancements

- Student login and individual complaint tracking.
- Email notifications for complaint status updates.
- Student feedback and rating system.
- Database integration using MySQL.
- Mobile application support.
- Advanced analytics and automated complaint prioritization.

## 🎓 Project Information

**Project Name:** Smart Complaint and Grievance Redressal System  
**Problem Statement:** PS-19  
**Domain:** Java Application Development  
**Project Type:** Desktop-Based Application

## 👩‍💻 Developed By

Developed as an academic project to demonstrate practical knowledge of Java programming, Object-Oriented Programming, GUI development, file handling, and application design.

---

⭐ If you find this project useful, consider giving this repository a star!
