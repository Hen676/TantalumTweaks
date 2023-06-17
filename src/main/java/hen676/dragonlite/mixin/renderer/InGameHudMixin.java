package hen676.dragonlite.mixin.renderer;

import hen676.dragonlite.config.Config;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "render", at = @At("RETURN"))
    public void renderCompassHud(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (!Config.ENABLE_COMPASS) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.cameraEntity == null) return;

        float degrees = MathHelper.wrapDegrees(client.cameraEntity.getYaw());
        String text = "";
        int sector = (int) (degrees + 180f);
        boolean northOrSouth = false;

        if (300 <= sector || sector <= 60) {
            text = "North";
            northOrSouth = true;
        }
        if (120 <= sector && sector <= 240) {
            text = "South";
            northOrSouth = true;
        }
        if (30 <= sector && sector <= 150)
            text += northOrSouth ? " East" : "East";
        if (210 <= sector && sector <= 330)
            text += northOrSouth ? " West" :"West";

        text += " (" + sector + ")";
        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;
        int k = textRenderer.getWidth(text);
        context.fill(1, 1, k + 3, 10, -1873784752);
        context.drawText(textRenderer, text, 2, 2, 14737632, false);
    }
}
