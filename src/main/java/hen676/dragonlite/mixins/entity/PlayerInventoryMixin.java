package hen676.dragonlite.mixins.entity;

import hen676.dragonlite.util.CallbackUtil;
import net.minecraft.entity.player.PlayerInventory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerInventory.class)
public abstract class PlayerInventoryMixin {
    @Inject(method = "setSelectedSlot", at = @At("HEAD"), cancellable = true)
    private void preventScrollInHotbarOnFreecam(int slot, CallbackInfo ci) {
        CallbackUtil.CancelIfFreecamOn(ci);
    }
}
