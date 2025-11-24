# ThinkOrSwim-Docs
Collections for TOS Scripts

## ThinkScript Manager CLI

This repository includes a Java 25 command-line application for managing and organizing thinkScript files. 

See [USAGE.md](USAGE.md) for detailed instructions on how to use the ThinkScript Manager.

### Quick Start

```bash
# List all scripts
java ScriptManager.java list

# View a script
java ScriptManager.java view <filename>

# Add tags to a script
java ScriptManager.java tag <filename> <tag1> <tag2> ...

# Find scripts by tags
java ScriptManager.java find <tag1> <tag2> ...
```

All scripts should be placed in the `think-scripts/` directory.
