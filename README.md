# 🛠 Build Engine Simulation

## Problem Statement

Given a JSON file that specifies build targets and rules, this program simulates a build engine by printing build commands in a valid execution order based on declared dependencies.

### Assumptions
- Any file under `src/` and `include/` already exists.
- Any other input file **must** be produced by exactly one target defined under `"targets"`.
- If dependencies form a cycle, the build must fail.

---

## Example Output

```bash
cl.exe /EHsc /Yc /Iinclude src/pch.cpp /Fp:build/pch.pch /Fo:build/
cl.exe /LD /EHsc /Yu /Iinclude /Fp:build/pch.pch src/math_utils.cpp /Fo:build/ /Fe:build/math_utils.dll
cl.exe /LD /EHsc /Yu /Iinclude /Fp:build/pch.pch src/string_helpers.cpp /Fo:build/ /Fe:build/string_helpers.dll
cl.exe /EHsc /Iinclude src/main.cpp build/math_utils.lib build/string_helpers.lib /Fo:build/ /Fe:build/program.exe
powershell -Command "Send-MailMessage -To 'user@example.com' -From 'build@example.com' -Subject 'Build Complete'"
```

**High level Approach** 

1. Parse the JSON file into build targets.

2. Map output files → producing targets.

    - Files under src/ and include/ are treated as pre-existing.

    - Every other input file must have exactly one producer.

3. Build a dependency graph:

   - Directed edge: producer → consumer.

4. Perform a topological sort to determine execution order.

5. Detect cycles and invalid configurations and fail fast.

## Code Flow

### Target.java
- Data model representing a single build rule
- Holds:
  - `inputs`
  - `outputs`
  - `command`

---

### DependencyGraphBuilder.java
- Maps output files → producing targets
- Builds the dependency graph (producer → consumer)
- Performs topological sort
- Detects cycles and invalid configurations

---

### Main.java
- Entry point of the program
- Reads the JSON file
- Builds the dependency graph
- Prints build commands in execution order

---

## Code output :

![Build Output](images/output.png)


