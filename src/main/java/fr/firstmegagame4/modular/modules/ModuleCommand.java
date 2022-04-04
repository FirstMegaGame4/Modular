package fr.firstmegagame4.modular.modules;

import com.google.gson.JsonObject;
import com.mojang.brigadier.Command;
import com.mojang.brigadier.context.CommandContext;
import fr.firstmegagame4.modular.Modular;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.List;

public class ModuleCommand implements Command<Object> {
    public List<ModuleCommandParameter> params;

    public ModuleCommand(List<ModuleCommandParameter> params) {
        this.params = params;
    }

    public int run(CommandContext context) {
        for (String module: Modular.MODULAR_MODULES.keySet()) {
            JsonObject commandModules = Modular.MODULAR_MODULES.get(module).getAsJsonObject("commands");
            for (String commandModule: commandModules.keySet()) {
                if (context.getCommand().toString().equals(commandModule)) {
                    List<ModuleFunctionParameter> params = new ArrayList<>();
                    params.add(new ModuleFunctionParameter("world", ((PlayerEntity) context.getSource()).getWorld()));
                    for (ModuleCommandParameter param: this.params) {
                        params.add(new ModuleFunctionParameter(param.getName(), context.getArgument(param.getName(), Object.class)));
                    }
                    new ModuleTask(commandModules.getAsJsonObject(commandModule).getAsJsonObject("execute"), params);
                }
            }
        }
        return 0;
    }
}
