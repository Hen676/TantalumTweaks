package hen676.tantalumtweaks.render;

import hen676.tantalumtweaks.config.Config;
import hen676.tantalumtweaks.gui.screen.option.CompassPlacement;
import hen676.tantalumtweaks.gui.screen.option.Options;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;

public class CompassRenderer {
    public static final Text NORTH_TEXT = Text.translatable("gui.tantalumtweaks.compass.north");
    public static final Text SOUTH_TEXT = Text.translatable("gui.tantalumtweaks.compass.south");
    public static final Text WEST_TEXT = Text.translatable("gui.tantalumtweaks.compass.west");
    public static final Text EAST_TEXT = Text.translatable("gui.tantalumtweaks.compass.east");
    public static final Text ERORR_TEXT = Text.translatable("gui.tantalumtweaks.compass.error");

    public static void draw(MinecraftClient client, Direction direction, BlockPos pos, @NotNull DrawContext context, CompassPlacement placement) {
        List<Pair<MutableText, Integer>> compassText = getCompassText(pos, direction);
        MatrixStack stack = context.getMatrices();
        int length = Config.COMPASS_GAP * 3;
        for (Pair<MutableText, Integer> pair : compassText) {
            length += client.textRenderer.getWidth(pair.getLeft());
        }
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
            case TOP_MIDDLE ->  stack.translate(((float) -length /2) - 2, 0, 0);
            default -> { }
        }
        int currentLength = 0;
        for (Pair<MutableText, Integer> pair : compassText) {
            context.drawText(
                    client.textRenderer,
                    pair.getLeft(),
                    currentLength,
                    0,
                    pair.getRight(),
                    Config.COMPASS_SHADOW);
            currentLength += client.textRenderer.getWidth(pair.getLeft()) + Config.COMPASS_GAP;
        }
        stack.pop();
    }

    private static @NotNull List<Pair<MutableText, Integer>> getCompassText(@NotNull BlockPos pos, Direction direction) {
        return Arrays.asList(
                new Pair<>(Text.translatable("gui.tantalumtweaks.compass.x",
                        formatPosition(pos.getX())),Options.getCompassXColorWithAlpha()),
                new Pair<>(Text.translatable("gui.tantalumtweaks.compass.y",
                        formatPosition(pos.getY())),Options.getCompassYColorWithAlpha()),
                new Pair<>(Text.translatable("gui.tantalumtweaks.compass.z",
                        formatPosition(pos.getZ())),Options.getCompassZColorWithAlpha()),
                new Pair<>(getTextFromDirection(direction).copy(),Options.getCompassDirectionColorWithAlpha())
        );
    }

    private static @NotNull String formatPosition(int pos) {
        return NumberFormat.getIntegerInstance().format(pos);
    }

    private static Text getTextFromDirection(@NotNull Direction direction) {
        return switch (direction) {
            case NORTH -> NORTH_TEXT;
            case SOUTH -> SOUTH_TEXT;
            case WEST -> WEST_TEXT;
            case EAST -> EAST_TEXT;
            default -> ERORR_TEXT;
        };
    }

    protected static double getWidthTranslate(int width, @NotNull CompassPlacement placement) {
        return switch (placement) {
            case TOP_LEFT, BOTTOM_LEFT -> Config.COMPASS_X_OFFSET;
            case TOP_MIDDLE -> (double) width / 2;
            default -> width - Config.COMPASS_X_OFFSET;
        };
    }

    protected static double getHeightTranslate(int height, @NotNull CompassPlacement placement) {
        return switch (placement) {
            case TOP_LEFT, TOP_RIGHT, TOP_MIDDLE -> Config.COMPASS_Y_OFFSET;
            default -> height - Config.COMPASS_Y_OFFSET;
        };
    }
}
