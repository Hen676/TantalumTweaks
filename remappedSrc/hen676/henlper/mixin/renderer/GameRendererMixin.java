package hen676.henlper.mixin.renderer;

import hen676.henlper.config.Config;
import hen676.henlper.keybinds.ZoomKeybinding;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {
    @Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
    public void getZoomLevel(CallbackInfoReturnable<Double> callbackInfo) {
        if (ZoomKeybinding.isZooming() && Config.ENABLE_ZOOM)
            callbackInfo.setReturnValue(callbackInfo.getReturnValue() * Config.ZOOM_AMOUNT);
        ZoomKeybinding.manageSmoothCamera();
    }
}
