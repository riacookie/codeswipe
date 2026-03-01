# 🚀 CodeSwipe - IT Project Discovery Platform

[![Live Demo](https://img.shields.io/badge/Live-Demo-brightgreen?style=for-the-badge)](https://codeswipe-40195048342.us-central1.run.app/)
[![Java](https://img.shields.io/badge/Java-17%2B-orange?style=for-the-badge&logo=java)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-6DB33F?style=for-the-badge&logo=springboot)](https://spring.io/projects/spring-boot)

**CodeSwipe** is a "Tinder-style" project discovery platform for IT students. It gamifies the process of finding coding projects by allowing users to swipe through a deck of ideas curated by AI and the community.

---

## ✨ Key Features

* **🔥 Swipe-Based Discovery:** Browse project cards—swipe right to "Like" or left to "Skip."
* **🤖 AI Project Generation:** Dynamic project ideas generated via **Google Gemini AI** based on specific skill sets.
* **🔐 Google Authentication:** Secure onboarding via **Firebase Auth**.
* **📋 Interested Projects:** A dedicated area to manage your "Liked" project backlog.
* **🔄 Smart Rescroll:** Ability to reset "Skipped" projects without affecting your "Liked" list.
* **☁️ Cloud Native:** Fully deployed and scalable architecture.

---

## 🌐 Live Build & Deployment

The application is hosted on **Google Cloud Platform (GCP)** using a modern serverless architecture:

* **Frontend/Backend:** Hosted on **Google Cloud Run** (Containerized via Docker).
* **Database:** Managed **Google Cloud SQL (PostgreSQL)**.
* **Live URL:** [https://codeswipe-40195048342.us-central1.run.app/](https://codeswipe-40195048342.us-central1.run.app/)

---

## 🛠 Technology Stack

| Layer | Technology |
| :--- | :--- |
| **Backend** | Spring Boot 3.x, Spring Data JPA, Spring Security |
| **Frontend** | Vanilla JavaScript, HTML5, CSS3, Tailwind CSS |
| **Database** | PostgreSQL (Cloud SQL) |
| **DevOps** | Docker, Google Cloud Run, Artifact Registry |
| **External APIs** | Google Gemini AI, Google Firebase Auth |

---

## 🚀 Setup & Installation

### 1. Prerequisites
* Java 17+
* Maven 3.x
* PostgreSQL (Local or Cloud instance)

### 2. Environment Configuration
Create a `.env` file in the root directory:

```env
DATABASE_URL=jdbc:postgresql://<YOUR_INSTANCE_IP>:5432/codeswipe_db
DATABASE_USERNAME=postgres
DATABASE_PASSWORD=your_secure_password

# External APIs
GOOGLE_GEMINI_KEY=your_gemini_api_key
FIREBASE_API_KEY=your_firebase_key
FIREBASE_PROJECT_ID=your_project_id
```

### 3. Build & Run

```bash
# Build the project
mvn clean install

# Run locally
mvn spring-boot:run
```

---

## 📐 API Documentation Summary

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| POST | `/api/auth/google-login` | Syncs Firebase user with local database |
| GET | `/api/projects/deck/{userId}` | Returns a stack of un-swiped project cards |
| POST | `/api/swipes` | Records a LIKE or SKIP action |
| DELETE | `/api/swipes/reset-skips/{userId}` | Clears skip history to reset the deck |
| POST | `/api/ai/generate` | Triggers Gemini AI to create a new project |

---

## 🧪 Testing Dataset

To populate your environment with sample data, use the following SQL script:

<details>
<summary>Click to view Seed SQL</summary>

```sql
-- DELETING PRE-DATA
TRUNCATE TABLE swipes, projects, users RESTART IDENTITY CASCADE;

-- INSERT DATA
-- ==========================================
-- 1. INSERT USERS (Students & System)
-- ==========================================
INSERT INTO users (username, email, experience_level, role, avt_url, telephone) VALUES 
('System Admin', 'admin@codeswipe.com', 'Advanced', 'ADMIN', 'https://api.dicebear.com/7.x/bottts/svg?seed=Admin', '0901234567'),
('Daniel Le', 'daniel@student.edu', 'Intermediate', 'STUDENT', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Daniel', '0911223344'),
('Minh Tran', 'minh.t@student.edu', 'Beginner', 'STUDENT', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Minh', '0922334455'),
('Sophia Nguyen', 'sophia@student.edu', 'Advanced', 'STUDENT', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Sophia', '0933445566'),
('Kevin Pham', 'kevin@student.edu', 'Intermediate', 'STUDENT', 'https://api.dicebear.com/7.x/avataaars/svg?seed=Kevin', '0944556677');

-- ==========================================
-- 2. INSERT PROJECTS
-- ==========================================

-- Beginner Projects (Mostly owned by System or Kevin)
INSERT INTO projects (title, description, difficulty_level, skill_name, created_date, user_id) VALUES 
('Personal Portfolio', 'A clean, responsive HTML/CSS website to showcase your homework and certificates.', 'Beginner', 'HTML, CSS, JavaScript', CURRENT_DATE, 1),
('Weather App', 'Fetch real-time weather data using the OpenWeather API. Good for learning Fetch API.', 'Beginner', 'JavaScript, HTML', CURRENT_DATE, 5),
('Simple Calculator', 'Build a calculator that handles basic math and has a dark mode toggle.', 'Beginner', 'JavaScript, CSS', CURRENT_DATE, 5),
('Todo List Pro', 'A local storage-based todo list with drag-and-drop features.', 'Beginner', 'JavaScript', CURRENT_DATE, 1);

-- Intermediate Projects (Owned by Daniel or System)
INSERT INTO projects (title, description, difficulty_level, skill_name, created_date, user_id) VALUES 
('Expense Tracker', 'Manage daily expenses with categories and beautiful Chart.js visualizations.', 'Intermediate', 'React, Firebase', CURRENT_DATE, 2),
('Recipe Finder', 'An app that suggests meals based on ingredients the user has in their fridge.', 'Intermediate', 'Python, Flask, SQLite', CURRENT_DATE, 1),
('Student Management System', 'A Spring Boot API to manage student grades and attendance records.', 'Intermediate', 'Java, Spring Boot, PostgreSQL', CURRENT_DATE, 2),
('Blog Platform', 'A simple CMS where users can write, edit, and delete their own blog posts.', 'Intermediate', 'Node.js, Express, MongoDB', CURRENT_DATE, 5);

-- Advanced Projects (Owned by Sophia or System)
INSERT INTO projects (title, description, difficulty_level, skill_name, created_date, user_id) VALUES 
('AI Study Planner', 'Uses Gemini API to generate personalized study schedules based on exam dates.', 'Advanced', 'React, Spring Boot, Google Gemini', CURRENT_DATE, 4),
('Real-time Chat App', 'A WebSocket-based chat app supporting multiple rooms and file sharing.', 'Advanced', 'Java, Spring Boot, React, WebSockets', CURRENT_DATE, 1),
('E-commerce Engine', 'A full-scale store with stripe payment integration and inventory management.', 'Advanced', 'Next.js, Spring Boot, Stripe', CURRENT_DATE, 4),
('Crypto Tracker', 'A real-time dashboard tracking top 100 coins with live price updates and alerts.', 'Advanced', 'React, Tailwind, Coingecko API', CURRENT_DATE, 1);




```

</details>

---

## 👥 Contributors

* **Backend & API:** [Your Name/Team]
* **AI Integration:** [Your Name/Team]
* **Frontend UI/UX:** [Your Name/Team]

*Created for the IT Student Community.*
