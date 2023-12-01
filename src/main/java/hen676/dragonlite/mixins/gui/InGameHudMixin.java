package hen676.dragonlite.mixins.gui;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.gui.screen.option.HudPlacement;
import hen676.dragonlite.gui.screen.option.Options;
import hen676.dragonlite.keybinds.FreecamKeybinding;
import hen676.dragonlite.util.CallbackUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static hen676.dragonlite.DragonLite.MC;

@Environment(EnvType.CLIENT)
@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    // Freecam

    @Inject(method = "renderStatusBars", at = @At("HEAD"), cancellable = true)
    private void preventStatusBarRenderOnFreecam(DrawContext context, CallbackInfo ci) {
        CallbackUtil.FreecamCancel(ci);
    }

    @Inject(method = "renderHotbar", at = @At("HEAD"), cancellable = true)
    private void preventHotbarRenderOnFreecam(float tickDelta, DrawContext context, CallbackInfo ci) {
        CallbackUtil.FreecamCancel(ci);
    }

    @Inject(method = "renderCrosshair", at = @At("HEAD"), cancellable = true)
    private void preventCrosshairRenderOnFreecam(DrawContext context, CallbackInfo ci) {
        CallbackUtil.FreecamCancel(ci);
    }


    // Compass
    @Inject(method = "render", at = @At("RETURN"))
    public void renderCompassHud(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (!Config.ENABLE_COMPASS || FreecamKeybinding.isFreecam()) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.cameraEntity == null || client.options.debugEnabled) return;
        Entity camera = client.cameraEntity;

        Direction direction = camera.getHorizontalFacing();
        List<String> list = new ArrayList<>();
        String compass = switch (direction) {
            case NORTH -> "Facing: North (-Z)";
            case SOUTH -> "Facing: South (+Z)";
            case WEST -> "Facing: West (-X)";
            case EAST -> "Facing: East (+X)";
            default -> "Invalid direction";
        };

        if (DragonLite.DEBUG) {
            float degrees = MathHelper.wrapDegrees(camera.getYaw());
            compass += " (" + degrees + ")";
        }
        BlockPos pos = camera.getBlockPos();
        list.add(String.format("XYZ: %s, %s, %s",pos.getX(),pos.getY(),pos.getZ()));
        list.add(compass);

        draw(context, client.textRenderer, list, HudPlacement.byId(Config.COMPASS_PLACEMENT));
    }

    private void draw(DrawContext context, TextRenderer textRenderer, List<String> list, HudPlacement placement)
    {
        if (placement == HudPlacement.TOP_RIGHT || placement == HudPlacement.TOP_LEFT) {
            int y = 2;
            for (String s : list) {
                draw(context, textRenderer, s, y, placement);
                y += 10;
            }
        }
        else {
            Collections.reverse(list);
            int y = context.getScaledWindowHeight() - 12;
            for (String s : list) {
                draw(context, textRenderer, s, y, placement);
                y -= 10;
            }
        }
    }

    private void draw(DrawContext context, TextRenderer textRenderer, String s, int y, HudPlacement placement)
    {
        //stack.scale((float) Config.COMPASS_SCALE, (float)Config.COMPASS_SCALE, 1);

        int k = textRenderer.getWidth(s);
        int x = (placement == HudPlacement.TOP_LEFT || placement ==  HudPlacement.BOTTOM_LEFT) ? 2 : context.getScaledWindowWidth() - (4 + k);
        context.fill(x, y, x + k + 2, y + 10, -1873784752);
        context.drawText(textRenderer, s, x + 1, y + 1, Options.getCompassColor(), true);
    }
}
