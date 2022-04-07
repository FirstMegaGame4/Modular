package fr.firstmegagame4.modular;

import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import fr.firstmegagame4.modular.lib.events.OnEntityFall;
import fr.firstmegagame4.modular.lib.events.OnClickOnBlock;
import fr.firstmegagame4.modular.modules.ModuleCommand;
import fr.firstmegagame4.modular.modules.ModuleCommandParameter;
import net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback;
import net.minecraft.command.argument.BlockStateArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.command.argument.NumberRangeArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;

import java.util.ArrayList;
import java.util.List;

public class Modules {

    public static void setupModule(JsonObject module) {
        JsonObject moduleCommands = module.getAsJsonObject("commands");
        for (String command: moduleCommands.keySet()) {
            JsonObject paramsModuleCommand = moduleCommands.getAsJsonObject(command).getAsJsonObject("parameters");
            List<ModuleCommandParameter> paramList = new ArrayList<>();
            for (String param: paramsModuleCommand.keySet()) {
                paramList.add(new ModuleCommandParameter(param, paramsModuleCommand.get(param).getAsString()));
            }
            Modules.addCommand(command, paramList);
        }
    }

    public static void executeEvent(String event, List<Object> params) {
        switch (event) {
            case "onClickOnBlock" -> OnClickOnBlock.execute(params);
            case "onEntityFall" -> OnEntityFall.execute(params);
        }
    }

    public static void addCommand(String name, List<ModuleCommandParameter> params) {
        CommandRegistrationCallback.EVENT.register(((dispatcher, dedicated) -> {
            LiteralArgumentBuilder<ServerCommandSource> manager = CommandManager.literal(name);
            for (ModuleCommandParameter param: params) {
                switch (param.getType()) {
                    case "type:int" -> manager = manager.then(CommandManager.argument(param.getName(), IntegerArgumentType.integer()));
                    case "type:float" -> manager = manager.then(CommandManager.argument(param.getName(), FloatArgumentType.floatArg()));
                    case "type:player" -> manager = manager.then(CommandManager.argument(param.getName(), EntityArgumentType.players()));
                    case "type:message" -> manager = manager.then(CommandManager.argument(param.getName(), MessageArgumentType.message()));
                    case "type:block" -> manager = manager.then(CommandManager.argument(param.getName(), BlockStateArgumentType.blockState()));
                    case "type:int_range" -> manager = manager.then(CommandManager.argument(param.getName(), NumberRangeArgumentType.intRange()));
                    case "type:float_range" -> manager = manager.then(CommandManager.argument(param.getName(), NumberRangeArgumentType.floatRange()));
                }
            }
            dispatcher.register(manager.executes(context -> new ModuleCommand(params).run(context)));
        }));
    }

}
