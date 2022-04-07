package fr.firstmegagame4.modular.modules;

import com.google.gson.JsonArray;

import java.util.List;

public class ModuleTask {
    public ModuleTask(JsonArray task, List<ModuleFunctionParameter> params) {
        for (int i = 0; i < task.size(); i++) {
            if (task.get(i).getAsString().startsWith("function:")) {
                ModuleFunction.function(task.get(i).getAsString(), params);
            }
            else if (task.get(i) instanceof JsonArray condition) {
                if (condition.get(0).getAsString().startsWith("condition:") && condition.get(1) instanceof JsonArray) {
                    if (ModuleFunction.condition(condition.get(0).getAsString(), params)) {
                        new ModuleTask(condition.get(1).getAsJsonArray(), params);
                    }
                }
            }
        }
    }
}
