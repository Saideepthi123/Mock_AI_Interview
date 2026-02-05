package buildengine;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar ... <path-to-json-file>");
            System.exit(1);
        }

        String jsonFilePath = args[0];
        try {
            String jsonContent = Files.readString(Path.of(jsonFilePath));
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonContent);

            JsonNode targetsNode = root.get("targets");
            if (targetsNode == null || !targetsNode.isArray()) {
                throw new IllegalArgumentException("JSON must contain a top-level 'targets' array");
            }

            List<Target> targets = objectMapper.readValue(targetsNode.traverse(), new TypeReference<List<Target>>() {});

            Map<Target, java.util.List<Target>> dependencyGraph = DependencyGraphBuilder.buildDependencyGraph(targets);
            java.util.List<Target> sorted = DependencyGraphBuilder.topologicalSort(dependencyGraph);

            System.out.println("Topologically sorted commands:");
            for (Target t : sorted) {
                if (t.command != null && !t.command.isBlank()) {
                    System.out.println(t.command);
                }
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }
}
