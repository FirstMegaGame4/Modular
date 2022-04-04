package fr.firstmegagame4.modular.events;

import fr.firstmegagame4.modular.Modules;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.Arrays;

public class OnPlayerPlaceBlock implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        if (hitResult.getType() == HitResult.Type.BLOCK) {
            Modules.executeEvent("onClickOnBlock", Arrays.stream(new Object[]{player, world, hitResult.getBlockPos()}).toList());
        }
        return ActionResult.PASS;
    }
}
