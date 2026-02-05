package buildengine;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Target {
    public String name;
    public List<String> inputs;

    @JsonProperty("outputs")
    public List<String> outputs;

    public String command;

    // Jackson needs a no-arg constructor
    public Target() {}

    @Override
    public String toString() {
        return "Target{name='" + name + "'}";
    }
}
