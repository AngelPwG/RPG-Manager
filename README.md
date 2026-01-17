# RPG Campaign Manager

> **A modular desktop application designed to centralize and streamline Tabletop RPG campaign management.**

This project allows Dungeon Masters to manage the complete lifecycle of a campaign, from character creation to session archiving, utilizing a local relational database for data persistence and a custom-built command interpreter for game mechanics.

---

## System Modules

The application is architected into five distinct, interacting subsystems:

### 1. Campaign Management System (CMS)

Serving as the central hub of the application, this module orchestrates the high-level organization of game data.

* **Dashboards:** Provides a gateway to all other subsystems via a campaign overview interface.
* **Workflow:** Manages the creation, configuration, and listing of multiple distinct campaigns.

### 2. Character Management

Handles the organization of both Player Characters (PCs) and Non-Player Characters (NPCs).

* **Attribute Tracking:** Manages stats, skills, and dynamic values (HP, Mana).
* **Role-Play Elements:** Stores narrative data such as character bonds, fears, and background lore.
* **Data Association:** Links characters to specific campaigns for easy retrieval.

### 3. Session Lifecycle Management

Coordinates the active gameplay phase, managing the state of the game from start to finish.

* **Lifecycle Control:** Handles the transition between **Creation**, **Active Tracking**, and **Archival** states.
* **Event Logging:** Records chronological events within a session for review.
* **Integration:** Acts as the bridge between the Dice System and the Campaign data.

### 4. Dice Rolling System & Command Interpreter

A chat-style interface that processes user input commands (e.g., `"2d6+3"`) to generate random results.

* **Command Parsing:** Custom logic to interpret complex dice strings and modifiers.
* **History Persistence:** Stores roll results in the database, linked to specific sessions for historical verification.

### 5. Compendium Database

A reference module for Game Masters to access static game data during preparation or active play.

* **Bestiary:** Searchable database of monster statistics.
* **Item Catalog:** Inventory of magic items and equipment.

---

## Tech Stack

* **Language:** Java (JDK 21)
* **GUI Framework:** JavaFX (CSS styled)
* **Database:** SQLite (Relational Design)
* **Architecture:** MVC (Model-View-Controller)

---

## Technical Highlights

* **String Parsing & Logic:** The **Dice Rolling System** implements a custom parser to tokenize strings like `"2d20+5"` or `"1d8-1"`, converting them into mathematical operations.
* **Relational Data Modeling:** The SQLite database uses foreign keys to enforce referential integrity between **Campaigns -> Sessions -> Logs/Rolls**.
* **State Management:** The **Session Manager** implements specific logic to handle the state transitions of a game session (Active vs. Archived), ensuring data immutability for past sessions.

---

## Installation

1. **Clone the repository:**
```bash
git clone https://github.com/your-username/rpg-campaign-manager.git

```


2. **Build:**
```bash
mvn clean install

```


3. **Run:**
```bash
mvn javafx:run

```



---

## Authors

This project was a collaborative effort to build a robust Java application.

* **José Ángel García Plascencia**
* *Co-Developer*


* **Jorge Luis Sandoval Urenda**
* *Co-Developer*
