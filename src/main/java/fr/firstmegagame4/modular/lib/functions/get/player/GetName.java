package fr.firstmegagame4.modular.lib.functions.get.player;

import net.minecraft.entity.player.PlayerEntity;

public class GetName {
    public static String execute(PlayerEntity player) {
        return player.getName().asString();
    }
}
