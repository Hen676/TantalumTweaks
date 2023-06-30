package hen676.dragonlite.mixin.renderer;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.util.HudPlacement;
import it.unimi.dsi.fastutil.objects.ObjectLists;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.Entity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {

    @Inject(method = "render", at = @At("RETURN"))
    public void renderCompassHud(DrawContext context, float tickDelta, CallbackInfo ci) {
        if (!Config.ENABLE_COMPASS) return;

        MinecraftClient client = MinecraftClient.getInstance();
        if (client.cameraEntity == null || client.options.debugEnabled) return;
        Entity camera = client.cameraEntity;
        Direction direction = camera.getHorizontalFacing();
        List<String> list = new ArrayList<>();
        String compass = switch (direction) {
            case NORTH -> "Direction: North (-Z)";
            case SOUTH -> "Direction: South (+Z)";
            case WEST -> "Direction: West (-X)";
            case EAST -> "Direction: East (+X)";
            default -> "Invalid direction";
        };

        if (DragonLite.DEBUG) {
            float degrees = MathHelper.wrapDegrees(camera.getYaw());
            compass += " (" + degrees + ")";
        }
        BlockPos pos = camera.getBlockPos();
        list.add(String.format("XYZ: %s, %s, %s",pos.getX(),pos.getY(),pos.getZ()));
        list.add(compass);

        drawText(context, client.textRenderer, list, HudPlacement.byId(Config.COMPASS_PLACEMENT));
    }

    private void drawText(DrawContext context, TextRenderer textRenderer, List<String> list, HudPlacement placement)
    {
        int x, y;
        if (placement == HudPlacement.TOP_RIGHT || placement == HudPlacement.TOP_LEFT) {
            y = 2;

            for (String s : list) {
                // TODO:: Make this a function. include below
                int k = textRenderer.getWidth(s);
                x = placement == HudPlacement.TOP_LEFT ? 2 : context.getScaledWindowWidth() - (4 + k);
                context.fill(x, y, x + k + 2, y + 10, -1873784752);
                context.drawText(textRenderer, s, x + 1, y + 1, DyeColor.byId(Config.COMPASS_COLOR).getSignColor(), true);
                y += 10;
            }
        }
        else {
            Collections.reverse(list);
            y = context.getScaledWindowHeight() - 12;
            for (String s : list) {
                int k = textRenderer.getWidth(s);
                x = placement == HudPlacement.BOTTOM_LEFT ? 2 : context.getScaledWindowWidth() - (4 + k);
                context.fill(x, y, x + k + 2, y + 10, -1873784752);
                context.drawText(textRenderer, s, x + 1, y + 1, DyeColor.byId(Config.COMPASS_COLOR).getSignColor(), true);
                y -= 10;
            }
        }
    }
}
