package hen676.tantalumtweaks.mixins.network;

import hen676.tantalumtweaks.TantalumTweaks;
import hen676.tantalumtweaks.keybinds.FreecamKeybinding;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class ClientPlayNetworkHandlerMixin {
    // Respawn includes death and changing dimension
    @Inject(method = "onPlayerRespawn", at = @At("HEAD"))
    private void turnOffFreecamOnPlayerRespawn(PlayerRespawnS2CPacket packet, CallbackInfo ci) {
        if(FreecamKeybinding.isFreecam())
            FreecamKeybinding.toggleFreecam(TantalumTweaks.MC);
    }
}
