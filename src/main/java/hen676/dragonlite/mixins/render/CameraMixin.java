package hen676.dragonlite.mixins.render;

import hen676.dragonlite.keybinds.FreecamKeybinding;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.render.Camera;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public abstract class CameraMixin {

    @Inject(method = "getSubmersionType", at = @At("HEAD"), cancellable = true)
    public void removeSubmersionTypeOnFreecam(CallbackInfoReturnable<CameraSubmersionType> cir) {
        if (FreecamKeybinding.isFreecam())
            cir.setReturnValue(CameraSubmersionType.NONE);
    }
}
