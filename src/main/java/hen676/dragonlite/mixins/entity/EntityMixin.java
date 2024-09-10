package hen676.dragonlite.mixins.entity;

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
        if (CallbackUtil.IsFreecamOnAndPlayerEntity((Entity)(Object)this)) {
            FreecamKeybinding.getFreeCameraEntity().changeLookDirection(x, y);
            ci.cancel();
        }
    }

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    private void preventFreecamGettingPushed(Entity entity, CallbackInfo ci) {
        CallbackUtil.CancelIfFreecamOn(ci);
    }

    @Inject(method = "setVelocity*", at = @At("HEAD"), cancellable = true)
    private void preventPlayerMovementOnFreecam1(CallbackInfo ci) {
        CallbackUtil.CancelIfPlayerIsEntityAndFreecamOn(ci, (Entity)(Object)this);
    }

    @Inject(method = "updateVelocity", at = @At("HEAD"), cancellable = true)
    private void preventPlayerMovementOnFreecam2(CallbackInfo ci) {
        CallbackUtil.CancelIfPlayerIsEntityAndFreecamOn(ci, (Entity)(Object)this);
    }

    @Inject(method = "setPosition*", at = @At("HEAD"), cancellable = true)
    private void preventPlayerMovementOnFreecam3(CallbackInfo ci) {
        CallbackUtil.CancelIfPlayerIsEntityAndFreecamOn(ci, (Entity)(Object)this);
    }

    @Inject(method = "setPos", at = @At("HEAD"), cancellable = true)
    private void preventPlayerMovementOnFreecam4(CallbackInfo ci) {
        CallbackUtil.CancelIfPlayerIsEntityAndFreecamOn(ci, (Entity)(Object)this);
    }


}
