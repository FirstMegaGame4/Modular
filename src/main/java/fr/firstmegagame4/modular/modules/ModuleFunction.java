package fr.firstmegagame4.modular.modules;

import fr.firstmegagame4.modular.lib.functions.set.world.SetBlock;

import java.util.ArrayList;
import java.util.List;

public class ModuleFunction {
    public static void function(String functionString, List<ModuleFunctionParameter> params) {
        String function = functionString.replace("function:", "");
        switch (function) {
            case "setBlock" -> SetBlock.execute(function, params);
        }
    }

    public static boolean condition(String conditionString, List<ModuleFunctionParameter> params) {
        String condition = conditionString.replace("condition:", "");
        if (condition.startsWith("equals(") || condition.startsWith("notEquals(") || condition.startsWith("superior(") || condition.startsWith("inferior(")) {
            List<Object> objects = new ArrayList<>();

            String temp;
            if (condition.startsWith("equals(")) temp = condition.replace("equals(", "");
            else if (condition.startsWith("notEquals(")) temp = condition.replace("notEquals(", "");
            else if (condition.startsWith("superior(")) temp = condition.replace("superior(", "");
            else if (condition.startsWith("inferior(")) temp = condition.replace("inferior(", "");
            else return false;

            String[] stringObjects = temp.replace(")", "").split(",");
            for (String stringObject : stringObjects) {
                Object object;
                for (ModuleFunctionParameter param : params) {
                    if (stringObject.equals(param.getName())) {
                        object = param.getValue();
                        objects.add(object);
                    }
                }
                if (stringObject.startsWith("'") && stringObject.endsWith("'")) {
                    object = stringObject.replace("'", "");
                    objects.add(object);
                }
            }

            if (!((condition.startsWith("superior(") || condition.startsWith("inferior(")) && objects.get(0) instanceof Number && objects.get(1) instanceof Number)) {
                return false;
            }

            if (condition.startsWith("equals(")) return objects.get(0) == objects.get(1);
            else if (condition.startsWith("notEquals(")) return objects.get(0) != objects.get(1);
            else if (condition.startsWith("superior(")) return (float) objects.get(0) > (float) objects.get(1);
            else if (condition.startsWith("inferior(")) return (float) objects.get(0) < (float) objects.get(1);
        }
        return false;
    }
}
