package fr.firstmegagame4.modular.modules;

import com.google.gson.JsonObject;
import fr.firstmegagame4.modular.Modules;

import java.util.List;

public class ModuleTask {
    public ModuleTask(JsonObject task, List<ModuleFunctionParameter> params) {
        for (String instructionType: task.keySet()) {
            if (instructionType.startsWith("function:")) {
                Modules.executeFunction(task.get(instructionType).getAsString(), params);
            }
        }
    }
}
