package hen676.dragonlite.mixins.network;

import hen676.dragonlite.keybinds.FreecamKeybinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.network.ClientConnection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    @Inject(method = "handleDisconnection", at = @At("HEAD"))
    private void turnOffFreecamOnDisconnection(CallbackInfo ci) {
        if(FreecamKeybinding.isFreecam())
            FreecamKeybinding.toggleFreecam(MinecraftClient.getInstance());
    }
}
