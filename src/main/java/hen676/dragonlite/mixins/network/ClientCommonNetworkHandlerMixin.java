package hen676.dragonlite.mixins.network;

import hen676.dragonlite.util.CallbackUtil;
import net.minecraft.client.network.ClientCommonNetworkHandler;
import net.minecraft.network.packet.Packet;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientCommonNetworkHandler.class)
public class ClientCommonNetworkHandlerMixin {
    @Inject(method = "sendPacket", at = @At("HEAD"), cancellable = true)
    private void cancelPacketOnFreecam(Packet<?> packet, CallbackInfo ci) {
        CallbackUtil.FreecamCancel(ci);
    }
}
