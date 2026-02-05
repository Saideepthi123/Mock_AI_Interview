package buildengine;

import java.util.*;

public class DependencyGraphBuilder {

    public static Map<String, Target> mapOutputsToTargets(List<Target> targets) {
        Map<String, Target> outputToTargetMap = new HashMap<>();

        for (Target target : targets) {
            if (target.outputs == null) continue;
            for (String output : target.outputs) {
                if (outputToTargetMap.containsKey(output)) {
                    throw new IllegalArgumentException("Duplicate output file detected: " + output +
                            " produced by targets " + outputToTargetMap.get(output).name + " and " + target.name);
                }
                outputToTargetMap.put(output, target);
            }
        }

        return outputToTargetMap;
    }

    public static Map<Target, List<Target>> buildDependencyGraph(List<Target> targets) {
        Map<String, Target> outputToTargetMap = mapOutputsToTargets(targets);
        Map<Target, List<Target>> dependencyGraph = new HashMap<>();

        // initialize nodes
        for (Target t : targets) {
            dependencyGraph.put(t, new ArrayList<>());
        }

        for (Target target : targets) {
            if (target.inputs == null) continue;
            for (String input : target.inputs) {
                if (input.startsWith("src/") || input.startsWith("include/")) {
                    continue; // source and include files already exist
                }

                Target producer = outputToTargetMap.get(input);
                if (producer == null) {
                    throw new IllegalArgumentException("No producer found for input file: " + input +
                            " required by target " + target.name);
                }
                dependencyGraph.get(producer).add(target);
            }
        }

        return dependencyGraph;
    }

    public static List<Target> topologicalSort(Map<Target, List<Target>> dependencyGraph) {
        Map<Target, Integer> inDegree = new HashMap<>();
        for (Target t : dependencyGraph.keySet()) {
            inDegree.put(t, 0);
        }
        for (List<Target> consumers : dependencyGraph.values()) {
            for (Target consumer : consumers) {
                inDegree.put(consumer, inDegree.getOrDefault(consumer, 0) + 1);
            }
        }

        Queue<Target> q = new ArrayDeque<>();
        for (Map.Entry<Target, Integer> e : inDegree.entrySet()) {
            if (e.getValue() == 0) q.add(e.getKey());
        }

        List<Target> sorted = new ArrayList<>();
        while (!q.isEmpty()) {
            Target cur = q.poll();
            sorted.add(cur);

            for (Target consumer : dependencyGraph.getOrDefault(cur, Collections.emptyList())) {
                inDegree.put(consumer, inDegree.get(consumer) - 1);
                if (inDegree.get(consumer) == 0) q.add(consumer);
            }
        }

        if (sorted.size() != dependencyGraph.size()) {
            throw new IllegalStateException("Cycle detected in the dependency graph");
        }

        return sorted;
    }
}
