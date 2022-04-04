package fr.firstmegagame4.modular.lib.tasks;

import fr.firstmegagame4.modular.modules.ModuleFunctionParameter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class SetBlock {
    public static void execute(String string, List<ModuleFunctionParameter> params) {
        World world = null;
        float x = 0F;
        float y = 0F;
        float z = 0F;
        for (ModuleFunctionParameter moduleParameter: params) {
            if (moduleParameter.getName().equals("world") && moduleParameter.getValue() instanceof World) {
                world = (World) moduleParameter.getValue();
            }
            else if (moduleParameter.getName().equals("x") && moduleParameter.getValue() instanceof Float) {
                x = (float) moduleParameter.getValue();
            }
            else if (moduleParameter.getName().equals("y") && moduleParameter.getValue() instanceof Float) {
                y = (float) moduleParameter.getValue();
            }
            else if (moduleParameter.getName().equals("z") && moduleParameter.getValue() instanceof Float) {
                z = (float) moduleParameter.getValue();
            }
        }
        if (world == null || x == 0F || y == 0F || z == 0F) return;
        world.setBlockState(new BlockPos(x, y, z), Registry.BLOCK.get(new Identifier("minecraft:acacia_log")).getDefaultState());
    }
}
