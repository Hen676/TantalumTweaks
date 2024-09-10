package hen676.dragonlite.render;

import hen676.dragonlite.config.Config;
import hen676.dragonlite.gui.screen.option.HudPlacement;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

import java.text.NumberFormat;

public class HudRenderer {
    public static final Text NORTH_TEXT = Text.translatable("gui.dragonlite.compass.north");
    public static final Text SOUTH_TEXT = Text.translatable("gui.dragonlite.compass.south");
    public static final Text WEST_TEXT = Text.translatable("gui.dragonlite.compass.west");
    public static final Text EAST_TEXT = Text.translatable("gui.dragonlite.compass.east");
    public static final Text ERORR_TEXT = Text.translatable("gui.dragonlite.compass.error");

    // TODO Improve - Config colors per number
    public static void draw(MinecraftClient client, Direction direction, BlockPos pos, DrawContext context, HudPlacement placement) {
        MutableText compassText = getCompassText(pos, direction);
        MatrixStack stack = context.getMatrices();
        int length = client.textRenderer.getWidth(compassText);
        stack.push();
        stack.translate(
                getWidthTranslate(context.getScaledWindowWidth(), placement),
                getHeightTranslate(context.getScaledWindowHeight(), placement),
                0D);
        stack.scale((float) Config.COMPASS_SCALE, (float)Config.COMPASS_SCALE, 1);

        switch (placement) {
            case TOP_RIGHT -> stack.translate(-length - 4, 0, 0);
            case BOTTOM_RIGHT -> stack.translate(-length - 4, -12, 0);
            case BOTTOM_LEFT -> stack.translate(0, -12, 0);
            case null, default -> { }
        }

        if (Config.COMPASS_BACKGROUND)
            context.fill(0, 0, length + (Config.COMPASS_SHADOW ? 4 : 2), (Config.COMPASS_SHADOW ? 12 : 10), -1873784752);
        context.drawText(client.textRenderer, compassText, 2, 2, 0, Config.COMPASS_SHADOW);
        stack.pop();
    }

    private static MutableText getCompassText(BlockPos pos, Direction direction) {
        return Text.translatable("gui.dragonlite.compass.number",
                        formatPosition(pos.getX()))
                .styled(style -> style.withColor(Formatting.DARK_GRAY))
                .append(Text.translatable("gui.dragonlite.compass.x")
                        .styled(style -> style.withColor(Formatting.RED)))
                .append(Text.translatable("gui.dragonlite.compass.number", formatPosition(pos.getY()))
                        .styled(style -> style.withColor(Formatting.DARK_GRAY)))
                .append(Text.translatable("gui.dragonlite.compass.y")
                        .styled(style -> style.withColor(Formatting.GREEN)))
                .append(Text.translatable("gui.dragonlite.compass.number", formatPosition(pos.getZ()))
                        .styled(style -> style.withColor(Formatting.DARK_GRAY)))
                .append(Text.translatable("gui.dragonlite.compass.z")
                        .styled(style -> style.withColor(Formatting.BLUE)))
                .append("| ").styled(style -> style.withColor(Formatting.DARK_GRAY))
                .append(getTextFromDirection(direction).copy()
                        .styled(style -> style.withColor(Formatting.GOLD)));
    }

    private static String formatPosition(int pos) {
        return NumberFormat.getIntegerInstance().format(pos);
    }

    private static Text getTextFromDirection(Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH_TEXT;
            case SOUTH -> SOUTH_TEXT;
            case WEST -> WEST_TEXT;
            case EAST -> EAST_TEXT;
            default -> ERORR_TEXT;
        };
    }

    private static double getWidthTranslate(int width, HudPlacement placement) {
        return placement == HudPlacement.TOP_LEFT || placement == HudPlacement.BOTTOM_LEFT ? 3D : width - 3D;
    }

    private static double getHeightTranslate(int height, HudPlacement placement) {
        return placement == HudPlacement.TOP_LEFT || placement == HudPlacement.TOP_RIGHT ? 3D : height - 3D;
    }
}
