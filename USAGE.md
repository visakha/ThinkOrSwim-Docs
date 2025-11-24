# ThinkScript Manager

A Java 25 command-line application for managing and organizing thinkScript files with a tagging system.

## Features

- 📁 Store and browse thinkScript files from the local filesystem
- 🏷️ Tag scripts for easy categorization
- 🔍 Query scripts by tags
- 🎨 Colored terminal output for better readability
- 💾 No database required - uses simple JSON file storage

## Requirements

- Java 25 (OpenJDK 25)

## Usage

Place all your thinkScript files in the `think-scripts/` directory.

### Quick Method (using wrapper script)

```bash
./ts-manager <command> [arguments]
```

### Direct Method

```bash
java ScriptManager.java <command> [arguments]
```

### Commands

#### List all scripts
```bash
./ts-manager list
# or
java ScriptManager.java list
```

#### View a script
```bash
./ts-manager view <filename>
# or
java ScriptManager.java view <filename>
```

#### Add tags to a script
```bash
./ts-manager tag <filename> <tag1> [tag2] [tag3] ...
# or
java ScriptManager.java tag <filename> <tag1> [tag2] [tag3] ...
```

Example:
```bash
./ts-manager tag simple_moving_average.txt moving-average trend beginner
```

#### Remove tags from a script
```bash
./ts-manager untag <filename> <tag1> [tag2] ...
# or
java ScriptManager.java untag <filename> <tag1> [tag2] ...
```

Example:
```bash
./ts-manager untag simple_moving_average.txt beginner
```

#### Find scripts by tags
```bash
./ts-manager find <tag1> [tag2] ...
# or
java ScriptManager.java find <tag1> [tag2] ...
```

Example:
```bash
./ts-manager find momentum
./ts-manager find beginner trend
```

#### Show help
```bash
./ts-manager help
# or
java ScriptManager.java help
```

## Example Workflow

1. Add some thinkScript files to the `think-scripts/` directory:
   ```bash
   cp my_script.txt think-scripts/
   ```

2. List all scripts:
   ```bash
   ./ts-manager list
   ```

3. View a script:
   ```bash
   ./ts-manager view my_script.txt
   ```

4. Add tags to organize your scripts:
   ```bash
   ./ts-manager tag my_script.txt indicator technical-analysis
   ```

5. Find scripts by tags:
   ```bash
   ./ts-manager find indicator
   ```

## Metadata Storage

Script metadata (tags) are stored in `think-scripts/.metadata.json`. This file is automatically created and updated when you add or remove tags. You can safely delete this file to reset all tags.

## Sample Scripts

The repository includes three sample thinkScript files:
- `simple_moving_average.txt` - A simple moving average indicator
- `rsi_indicator.txt` - Relative Strength Index (RSI) indicator
- `macd.txt` - MACD (Moving Average Convergence Divergence) indicator

## Java 25 Features

This application uses Java 25's script-style execution, which allows running Java files directly without explicit compilation. The application also uses modern Java features like:
- `var` keyword for type inference
- Switch expressions
- Records and pattern matching
- Text blocks (in the code)
