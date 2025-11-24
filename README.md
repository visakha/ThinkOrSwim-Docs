# ThinkOrSwim-Docs
Collections for TOS Scripts

## ThinkScript Manager CLI

This repository includes a Java 25 command-line application for managing and organizing thinkScript files. 

See [USAGE.md](USAGE.md) for detailed instructions on how to use the ThinkScript Manager.

### Quick Start

```bash
# Using the wrapper script (easier)
./ts-manager list

# Or directly with Java
java ScriptManager.java list

# View a script
./ts-manager view <filename>

# Add tags to a script
./ts-manager tag <filename> <tag1> <tag2> ...

# Find scripts by tags
./ts-manager find <tag1> <tag2> ...
```

All scripts should be placed in the `think-scripts/` directory.
