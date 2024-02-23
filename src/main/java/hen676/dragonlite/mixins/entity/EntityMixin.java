package hen676.dragonlite.mixins.entity;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.keybinds.FreecamKeybinding;
import hen676.dragonlite.util.CallbackUtil;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "changeLookDirection", at = @At("HEAD"), cancellable = true)
    private void updateFreecamLookDirectionThenCancel(double x, double y, CallbackInfo ci) {
        if (FreecamKeybinding.isFreecam() && this.equals(DragonLite.MC.player)) {
            FreecamKeybinding.getFreeCameraEntity().changeLookDirection(x, y);
            ci.cancel();
        }
    }

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    private void preventFreecamGettingPushed(Entity entity, CallbackInfo ci) {
        CallbackUtil.FreecamCancel(ci);
    }

    @Inject(method = "setVelocity*", at = @At("HEAD"), cancellable = true)
    private void preventPlayerMovementOnFreecam1(CallbackInfo ci) {
        CallbackUtil.FreecamCancelAndToggle(ci, this.equals(DragonLite.MC.player));
    }

    @Inject(method = "updateVelocity", at = @At("HEAD"), cancellable = true)
    private void preventPlayerMovementOnFreecam2(CallbackInfo ci) {
        CallbackUtil.FreecamCancelAndToggle(ci, this.equals(DragonLite.MC.player));
    }

    @Inject(method = "setPosition*", at = @At("HEAD"), cancellable = true)
    private void preventPlayerMovementOnFreecam3(CallbackInfo ci) {
        CallbackUtil.FreecamCancelAndToggle(ci, this.equals(DragonLite.MC.player));
    }

    @Inject(method = "setPos", at = @At("HEAD"), cancellable = true)
    private void preventPlayerMovementOnFreecam4(CallbackInfo ci) {
        CallbackUtil.FreecamCancelAndToggle(ci, this.equals(DragonLite.MC.player));
    }
}
