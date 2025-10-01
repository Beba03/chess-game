# ♟️ Java Chess Game

A fully functional **Java Chess Game** built with **Swing GUI** for an interactive interface.  
The project follows the **Model-View-Controller (MVC) architecture** and applies several **design patterns** to ensure clean, scalable, and maintainable code.  

Players can enjoy a **two-player local chess experience** with complete move validation, chess rules enforcement, and visually rendered pieces.

---

## ✨ Features
- 🎮 **Graphical User Interface (GUI)** built with Java Swing  
- ⚖️ Structured with **MVC Architecture** (Model-View-Controller)  
- 🏗️ **Design Patterns** implemented for scalability:
  - Strategy → Move behaviors for pieces
  - Observer → Updates between model and view  
  - Factory → Piece creation (White/Black factory)  
  - Singleton → Central game management  
- ♟️ Support for **all chess pieces** and their legal moves  
- 🔄 Special moves supported:
  - Castling
  - En Passant
  - Pawn Promotion  
- 👑 Game detection:
  - Check
  - Checkmate
  - Stalemate  
- 🎨 Chess piece PNG images included  
- 🏳️ Local **2-player mode**


## 🛠️ Technologies
- **Language:** Java  
- **GUI:** Swing  
- **Architecture:** MVC (Model-View-Controller)  
- **Design Patterns:** Strategy, Observer, Factory, Singleton  
- **Build Tool:** Maven  

---

## 🖥️ Demo

![Chess Game Demo](https://github.com/user-attachments/assets/991325e4-ccff-454a-9556-9cd5ff22fa7c)

---

## 📂 Project Structure

```
java-chess-game/
├── src/main/java/         # Source code (Core, Frontend, Pieces)
│   ├── Core/
│   ├── Frontend/
│   └── Pieces/
│
├── PiecesImages/          # Chess piece images (Black/White PNGs)
├── assets/                # Documentation assets
│   ├── demo.gif           # Demo gameplay animation
│   ├── demo.png           # Screenshot of gameplay
│   ├── Front-End.png      # GUI Architecture diagram
│   ├── Back-End.png       # Backend Design diagram
│   └── Factory DP.png     # Factory Design Pattern diagram
│
├── Undo.png               # GUI supporting asset
├── pom.xml                # Maven project configuration
└── README.md              # This file
```

## 🚀 Getting Started

### 1. Clone the Repository
```bash
git clone https://github.com/<your-username>/java-chess-game.git
cd java-chess-game
```

### 2. Compile the Source Code
If using plain Java:
```bash
javac -d out src/main/java/**/*.java
```

If using Maven:
```bash
mvn compile
```

### 3. Run the Game
Plain Java:
```bash
java -cp out Core.MyChess
```

With Maven:
```bash
mvn exec:java -Dexec.mainClass="Core.MyChess"
```
---

## 👨‍💻 Author
([@Beba03](https://github.com/Beba03))  
