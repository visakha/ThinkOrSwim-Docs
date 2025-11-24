import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

public class ScriptManager {
    
    // ANSI Color codes for terminal output
    static class Colors {
        static final String RESET = "\u001B[0m";
        static final String RED = "\u001B[31m";
        static final String GREEN = "\u001B[32m";
        static final String YELLOW = "\u001B[33m";
        static final String BLUE = "\u001B[34m";
        static final String MAGENTA = "\u001B[35m";
        static final String CYAN = "\u001B[36m";
        static final String BOLD = "\u001B[1m";
    }
    
    // Metadata for a script
    static class ScriptMetadata {
        final String fileName;
        final Set<String> tags;
        
        ScriptMetadata(String fileName, Set<String> tags) {
            this.fileName = fileName;
            this.tags = tags != null ? new HashSet<>(tags) : new HashSet<>();
        }
        
        String toJson() {
            var json = new StringBuilder();
            json.append("{\"fileName\":\"").append(fileName).append("\",");
            json.append("\"tags\":[");
            json.append(tags.stream()
                .map(t -> "\"" + escapeJson(t) + "\"")
                .collect(Collectors.joining(",")));
            json.append("]}");
            return json.toString();
        }
        
        static ScriptMetadata fromJson(String json) {
            // Simple JSON parser for our needs
            var fileName = json.split("\"fileName\":\"")[1].split("\"")[0];
            var tags = new HashSet<String>();
            
            if (json.contains("\"tags\":[")) {
                var tagsStr = json.split("\"tags\":\\[")[1].split("\\]")[0];
                if (!tagsStr.isEmpty()) {
                    tags = Arrays.stream(tagsStr.split(","))
                        .map(s -> s.trim().replaceAll("\"", ""))
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toCollection(HashSet::new));
                }
            }
            return new ScriptMetadata(fileName, tags);
        }
        
        private static String escapeJson(String s) {
            return s.replace("\\", "\\\\").replace("\"", "\\\"");
        }
    }
    
    // Main script manager
    static class ScriptStore {
        private static final String SCRIPTS_DIR = "think-scripts";
        private static final String METADATA_FILE = "think-scripts/.metadata.json";
        private final Map<String, ScriptMetadata> metadata;
        
        ScriptStore() throws IOException {
            this.metadata = loadMetadata();
        }
        
        private Map<String, ScriptMetadata> loadMetadata() throws IOException {
            var metaPath = Paths.get(METADATA_FILE);
            var result = new HashMap<String, ScriptMetadata>();
            
            if (Files.exists(metaPath)) {
                var content = Files.readString(metaPath);
                // Parse simple JSON format: {"filename1":{...}, "filename2":{...}}
                if (content.trim().startsWith("{") && content.trim().length() > 2) {
                    content = content.trim().substring(1, content.length() - 1); // Remove outer {}
                    
                    // Split by script entries - simplified parsing
                    var parts = content.split("\"\\s*:\\s*\\{");
                    for (var i = 1; i < parts.length; i++) {
                        var keyPart = parts[i - 1];
                        var lastQuote = keyPart.lastIndexOf('"');
                        if (lastQuote >= 0) {
                            var key = keyPart.substring(lastQuote + 1).trim();
                            var valuePart = parts[i].split("\\}")[0] + "}";
                            try {
                                var meta = ScriptMetadata.fromJson("{" + valuePart);
                                result.put(key, meta);
                            } catch (Exception e) {
                                // Skip invalid entries
                            }
                        }
                    }
                }
            }
            return result;
        }
        
        private void saveMetadata() throws IOException {
            Files.createDirectories(Paths.get(SCRIPTS_DIR));
            var json = new StringBuilder("{\n");
            
            var entries = new ArrayList<>(metadata.entrySet());
            for (int i = 0; i < entries.size(); i++) {
                var entry = entries.get(i);
                json.append("  \"").append(entry.getKey()).append("\": ")
                    .append(entry.getValue().toJson());
                if (i < entries.size() - 1) {
                    json.append(",");
                }
                json.append("\n");
            }
            json.append("}\n");
            
            Files.writeString(Paths.get(METADATA_FILE), json.toString());
        }
        
        List<Path> listScripts() throws IOException {
            var scriptsPath = Paths.get(SCRIPTS_DIR);
            if (!Files.exists(scriptsPath)) {
                Files.createDirectories(scriptsPath);
                return new ArrayList<>();
            }
            
            return Files.walk(scriptsPath)
                .filter(Files::isRegularFile)
                .filter(p -> !p.getFileName().toString().equals(".metadata.json"))
                .sorted()
                .collect(Collectors.toList());
        }
        
        String readScript(String fileName) throws IOException {
            var scriptPath = Paths.get(SCRIPTS_DIR, fileName);
            if (!Files.exists(scriptPath)) {
                throw new FileNotFoundException("Script not found: " + fileName);
            }
            return Files.readString(scriptPath);
        }
        
        void addTags(String fileName, String... tags) throws IOException {
            var meta = metadata.computeIfAbsent(fileName, 
                k -> new ScriptMetadata(fileName, new HashSet<>()));
            meta.tags.addAll(Arrays.asList(tags));
            saveMetadata();
        }
        
        void removeTags(String fileName, String... tags) throws IOException {
            var meta = metadata.get(fileName);
            if (meta != null) {
                meta.tags.removeAll(Arrays.asList(tags));
                saveMetadata();
            }
        }
        
        Set<String> getTags(String fileName) {
            var meta = metadata.get(fileName);
            return meta != null ? new HashSet<>(meta.tags) : new HashSet<>();
        }
        
        List<String> findByTags(String... tags) {
            var searchTags = new HashSet<>(Arrays.asList(tags));
            return metadata.entrySet().stream()
                .filter(e -> !Collections.disjoint(e.getValue().tags, searchTags))
                .map(Map.Entry::getKey)
                .sorted()
                .collect(Collectors.toList());
        }
    }
    
    // Main application
    public static void main(String[] args) {
        try {
            var store = new ScriptStore();
            
            if (args.length == 0) {
                printHelp();
                return;
            }
            
            var command = args[0];
            
            switch (command) {
                case "list" -> listCommand(store);
                case "view" -> viewCommand(store, args);
                case "tag" -> tagCommand(store, args);
                case "untag" -> untagCommand(store, args);
                case "find" -> findCommand(store, args);
                case "help" -> printHelp();
                default -> {
                    println(Colors.RED + "Unknown command: " + command + Colors.RESET);
                    printHelp();
                }
            }
        } catch (IOException e) {
            println(Colors.RED + "Error: " + e.getMessage() + Colors.RESET);
            System.exit(1);
        }
    }
    
    static void listCommand(ScriptStore store) throws IOException {
        println(Colors.BOLD + Colors.CYAN + "=== ThinkScript Files ===" + Colors.RESET);
        var scripts = store.listScripts();
        
        if (scripts.isEmpty()) {
            println(Colors.YELLOW + "No scripts found in think-scripts directory" + Colors.RESET);
            return;
        }
        
        for (var script : scripts) {
            var fileName = script.getFileName().toString();
            var tags = store.getTags(fileName);
            
            print(Colors.GREEN + "📄 " + fileName + Colors.RESET);
            if (!tags.isEmpty()) {
                print(" " + Colors.BLUE + "[" + String.join(", ", tags) + "]" + Colors.RESET);
            }
            println("");
        }
    }
    
    static void viewCommand(ScriptStore store, String[] args) throws IOException {
        if (args.length < 2) {
            println(Colors.RED + "Usage: java ScriptManager.java view <filename>" + Colors.RESET);
            return;
        }
        
        var fileName = args[1];
        try {
            var content = store.readScript(fileName);
            var tags = store.getTags(fileName);
            
            println(Colors.BOLD + Colors.CYAN + "=== " + fileName + " ===" + Colors.RESET);
            if (!tags.isEmpty()) {
                println(Colors.BLUE + "Tags: " + String.join(", ", tags) + Colors.RESET);
            }
            println(Colors.MAGENTA + "─".repeat(60) + Colors.RESET);
            println(content);
            println(Colors.MAGENTA + "─".repeat(60) + Colors.RESET);
        } catch (FileNotFoundException e) {
            println(Colors.RED + "Error: " + e.getMessage() + Colors.RESET);
        }
    }
    
    static void tagCommand(ScriptStore store, String[] args) throws IOException {
        if (args.length < 3) {
            println(Colors.RED + "Usage: java ScriptManager.java tag <filename> <tag1> [tag2] ..." + Colors.RESET);
            return;
        }
        
        var fileName = args[1];
        var tags = Arrays.copyOfRange(args, 2, args.length);
        
        store.addTags(fileName, tags);
        println(Colors.GREEN + "✓ Added tags to " + fileName + ": " + 
                String.join(", ", tags) + Colors.RESET);
    }
    
    static void untagCommand(ScriptStore store, String[] args) throws IOException {
        if (args.length < 3) {
            println(Colors.RED + "Usage: java ScriptManager.java untag <filename> <tag1> [tag2] ..." + Colors.RESET);
            return;
        }
        
        var fileName = args[1];
        var tags = Arrays.copyOfRange(args, 2, args.length);
        
        store.removeTags(fileName, tags);
        println(Colors.GREEN + "✓ Removed tags from " + fileName + ": " + 
                String.join(", ", tags) + Colors.RESET);
    }
    
    static void findCommand(ScriptStore store, String[] args) throws IOException {
        if (args.length < 2) {
            println(Colors.RED + "Usage: java ScriptManager.java find <tag1> [tag2] ..." + Colors.RESET);
            return;
        }
        
        var tags = Arrays.copyOfRange(args, 1, args.length);
        var results = store.findByTags(tags);
        
        println(Colors.BOLD + Colors.CYAN + "=== Scripts with tags: " + 
                String.join(", ", tags) + " ===" + Colors.RESET);
        
        if (results.isEmpty()) {
            println(Colors.YELLOW + "No scripts found with these tags" + Colors.RESET);
            return;
        }
        
        for (var fileName : results) {
            var scriptTags = store.getTags(fileName);
            print(Colors.GREEN + "📄 " + fileName + Colors.RESET);
            print(" " + Colors.BLUE + "[" + String.join(", ", scriptTags) + "]" + Colors.RESET);
            println("");
        }
    }
    
    static void printHelp() {
        println(Colors.BOLD + Colors.CYAN + "ThinkScript Manager" + Colors.RESET);
        println("");
        println("Usage: java ScriptManager.java <command> [arguments]");
        println("");
        println(Colors.YELLOW + "Commands:" + Colors.RESET);
        println("  " + Colors.GREEN + "list" + Colors.RESET + "                     - List all scripts");
        println("  " + Colors.GREEN + "view <filename>" + Colors.RESET + "         - View script content");
        println("  " + Colors.GREEN + "tag <filename> <tags...>" + Colors.RESET + " - Add tags to a script");
        println("  " + Colors.GREEN + "untag <filename> <tags...>" + Colors.RESET + " - Remove tags from a script");
        println("  " + Colors.GREEN + "find <tags...>" + Colors.RESET + "          - Find scripts by tags");
        println("  " + Colors.GREEN + "help" + Colors.RESET + "                    - Show this help message");
        println("");
        println("All scripts should be placed in the " + Colors.BLUE + "think-scripts/" + Colors.RESET + " directory");
    }
    
    static void print(String s) {
        System.out.print(s);
    }
    
    static void println(String s) {
        System.out.println(s);
    }
}
