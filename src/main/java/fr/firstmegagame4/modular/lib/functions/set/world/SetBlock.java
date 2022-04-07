package fr.firstmegagame4.modular.lib.functions.set.world;

import fr.firstmegagame4.modular.modules.ModuleFunctionParameter;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.List;

public class SetBlock {
    public static void execute(String function, List<ModuleFunctionParameter> params) {
        World world = null;
        String block = null;
        float x = 0F;
        float y = 0F;
        float z = 0F;
        for (ModuleFunctionParameter param: params) {
            if (param.getName().equals("world") && param.getValue() instanceof World) {
                world = (World) param.getValue();
            }
            else if (param.getName().equals("block") && param.getValue() instanceof String) {
                block = (String) param.getValue();
            }
            else if (param.getName().equals("x") && param.getValue() instanceof Float) {
                x = (float) param.getValue();
            }
            else if (param.getName().equals("y") && param.getValue() instanceof Float) {
                y = (float) param.getValue();
            }
            else if (param.getName().equals("z") && param.getValue() instanceof Float) {
                z = (float) param.getValue();
            }
        }
        if (world == null || x == 0F || y == 0F || z == 0F) return;
        world.setBlockState(new BlockPos(x, y, z), Registry.BLOCK.get(new Identifier(block)).getDefaultState());
    }
}
