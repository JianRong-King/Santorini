# Santorini Game

A Java implementation of the board game Santorini with a Swing-based GUI.

## Authors
1. Jian Rong King
2. Wilson Tan
3. Yong Han Lee
4. Louis Jeremie Ing-Seng Ah Yuen

## Features

* Customizable board with multi-level towers and domes.
* Simple and intuitive user interface using Java Swing.
* God powers: Supports **Artemis**, **Demeter** and **Zeus** now.
* Multiplayer support: Play with up to 2 to 8 players.
* Dynamic board size: adjust at /resources/map.txt. Where X is a valid board position and O is a hole.
* Leaderboard: Keep track of player scores by storing at user device and support score refreshing.

## Prerequisites

* **Java SE Development Kit (JDK) 22** or higher.
* (Optional) An IDE such as IntelliJ IDEA, Eclipse, or NetBeans.

## Supported Platforms

This game is distributed as a Java executable JAR and runs on any platform with a compatible Java Runtime Environment (JRE):

* **Windows** (7, 8, 10, 11)
* **Linux** (Ubuntu, Fedora, Debian, etc.)
* **macOS** (10.13+)

## Project Structure

```
Sprint Three/
├── src/                   # Java source files
│   ├── Santorini.java     # Main entry point
│   ├── action/            # Action related classes
│   ├── board/             # Board, Position, Location classes
│   ├── capability/        # Capability logic for specialized actions
│   ├── game/              # Game controller, UI components, world model
│   ├── god/               # God card implementations (Artemis, Demeter, etc.)
│   ├── ground/            # Ground, Floor and tower level classes
│   ├── player/            # Player, Worker and WorkerLocationController classes
│   └── resources/             # Image assets (gods, players)
|   └── utility/             # Utility classes for common functions
|   └── leaderboard/             # Leaderboard related classes
├── manifest.txt           # Manifest file for JAR creation
└── README.md              # Project documentation (this file)
```

## Building and Running

### Compile from Command Line

1. Open a terminal or command prompt and navigate to the project root.

2. Compile all `.java` files into an `out/` directory:

   ```bash
   javac -d out src/Santorini.java src/action/*.java src/board/*.java src/capability/*.java src/game/*.java src/god/*.java src/ground/*.java src/player/*.java src/leaderboard/*.java   src/utility/*.java 
   ```

3. Copy the resources to the `out/` directory:

   ```bash
   cp -r src/resources out/
   ```

4. Run the game:

   ```bash
   java -cp out Santorini
   ```

### Run in an IDE

1. Import the project as a Java module in your IDE.
2. Ensure the `src` folder is marked as the source root.
3. Run the `Santorini` class.

## Creating an Executable JAR

Bundle the game into a standalone executable JAR that can be run on any supported platform.

1. Ensure the manifest file `manifest.txt` with the following content is inside the directory, otherwise create it:

   ```text
   Main-Class: Santorini
   ```

2. Compile the source code (if not already compiled):

   ```bash
   javac -d out src/Santorini.java src/action/*.java src/board/*.java src/capability/*.java src/game/*.java src/god/*.java src/ground/*.java src/player/*.java src/leaderboard/*.java   src/utility/*.java 
   ```

3. Package the JAR, including the compiled classes and manifest (Use this command if you didn't copy resources to `out/` before):

   ```bash
   jar cfm Santorini.jar manifest.txt -C out . -C src resources
   ```

4. Package the JAR, including the compiled classes and manifest (Use this command if you copied resources to `out/` before):
   by using cp -r src/resources out/
   ```bash
   jar cfm Santorini.jar manifest.txt -C out .
   ```
   
5. Verify the JAR structure:

   ```bash
   jar tf Santorini.jar
   ```

## Running the Executable JAR

On any supported platform (Windows, Linux, macOS), ensure Java is installed and use:

```bash
java -jar Santorini.jar
```

You can double-click the `.jar` file on Windows or macOS if file associations are configured.

---

Enjoy playing Santorini!
