package fr.firstmegagame4.modular.lib.events;

import com.google.gson.JsonObject;
import fr.firstmegagame4.modular.Modular;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;

public class OnClickOnBlock {
    public static void execute(List<Object> params) {
        LivingEntity entity = (LivingEntity) params.get(0);
        World world = (World) params.get(1);
        BlockPos pos = (BlockPos) params.get(2);

        if (entity.isSpectator()) return;

        if (entity instanceof PlayerEntity player) {
            player.sendMessage(Text.of("Hello !"), true);
        }

        for (JsonObject module: Modular.MODULAR_MODULES.values()) {
            module.getAsJsonObject("events").getAsJsonObject("placeBlock");
        }
    }
}
