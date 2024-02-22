package hen676.dragonlite.render;

import hen676.dragonlite.config.Config;
import hen676.dragonlite.gui.screen.option.HudPlacement;
import hen676.dragonlite.gui.screen.option.Options;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

public class HudRenderer {

    public static void draw(MinecraftClient client, Direction direction, BlockPos pos, DrawContext context, HudPlacement placement) {
        String compass = switch (direction) {
            case NORTH -> "North (-Z)";
            case SOUTH -> "South (+Z)";
            case WEST -> "West (-X)";
            case EAST -> "East (+X)";
            default -> "Direction";
        };
        String text = String.format("%sX %sY %sZ | %s",pos.getX(), pos.getY(), pos.getZ(), compass);

        MatrixStack stack = context.getMatrices();
        int length = client.textRenderer.getWidth(text);
        stack.push();
        stack.translate(
                getWidthTranslate(context.getScaledWindowWidth(), placement),
                getHeightTranslate(context.getScaledWindowHeight(), placement),
                0D);
        stack.scale((float) Config.COMPASS_SCALE, (float)Config.COMPASS_SCALE, 1);

        if(placement == HudPlacement.TOP_RIGHT)
            stack.translate(-length - 4, 0, 0);
        if(placement == HudPlacement.BOTTOM_RIGHT)
            stack.translate(-length-  4, -12, 0);
        if(placement == HudPlacement.BOTTOM_LEFT)
            stack.translate(0, -12, 0);

        context.fill(0, 0, length + 4, 12, -1873784752);
        context.drawText(client.textRenderer, text, 2, 2, Options.getCompassColor(), true);
        stack.pop();

    }

    private static double getWidthTranslate(int width, HudPlacement placement) {
        if (placement == HudPlacement.TOP_LEFT || placement == HudPlacement.BOTTOM_LEFT)
            return 3D;
        else
            return width - 3D;
    }

    private static double getHeightTranslate(int height, HudPlacement placement) {
        if (placement == HudPlacement.TOP_LEFT || placement == HudPlacement.TOP_RIGHT)
            return 3D;
        else
            return height - 3D;
    }
}
