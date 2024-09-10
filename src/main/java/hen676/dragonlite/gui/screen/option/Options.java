package hen676.dragonlite.gui.screen.option;

import com.mojang.serialization.Codec;
import hen676.dragonlite.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class Options {

    // Compass options
    public static final SimpleOption<Boolean> compass = SimpleOption.ofBoolean("option.dragonlite.config.enable_compass",
            false,
            value -> Config.ENABLE_COMPASS = value);
    public static final SimpleOption<Boolean> compassBackground = SimpleOption.ofBoolean("option.dragonlite.config.compass_background",
            false,
            value -> Config.COMPASS_BACKGROUND = value);
    public static final SimpleOption<Boolean> compassShadow = SimpleOption.ofBoolean("option.dragonlite.config.compass_shadow",
            true,
            value -> Config.COMPASS_SHADOW = value);
    public static final SimpleOption<HudPlacement> compassPlacement = new SimpleOption<>("option.dragonlite.config.compass_placement",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("option.dragonlite.config." + value.toString().toLowerCase()),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(HudPlacement.values()), Codec.INT.xmap(HudPlacement::byId, HudPlacement::getId)),
            HudPlacement.TOP_LEFT,
            value -> Config.COMPASS_PLACEMENT = value.getId());
    public static final SimpleOption<Double> compassScale = new SimpleOption<>("option.dragonlite.config.compass_scale",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.compass_scale"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 100)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 50.0D,
                    (value) -> (int) (value * 50.0D)),
            Codec.doubleRange(0.2D, 2D),
            1D,
            value -> Config.COMPASS_SCALE = value);

    // Zoom options
    public static final SimpleOption<Double> zoomLevel = new SimpleOption<>("option.dragonlite.config.zoom_level",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.zoom_amount"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 40)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 50.0D,
                    (value) -> (int) (value * 50.0D)),
            Codec.doubleRange(0.2D, 0.8D),
            0.35D,
            value -> Config.ZOOM_AMOUNT = value);

    // Smokey furnace option
    public static final SimpleOption<Boolean> smokeyFurnace = SimpleOption.ofBoolean("option.dragonlite.config.enable_smokey_furnace",
            SimpleOption.constantTooltip(Text.translatable("option.dragonlite.config.enable_smokey_furnace.tooltip")),
            true,
            value -> Config.ENABLE_SMOKEY_FURNACE = value);

    // Remove fog option
    public static final SimpleOption<Boolean> reduceFog = SimpleOption.ofBoolean("option.dragonlite.config.enable_reduce_fog",
            true,
            value -> Config.ENABLE_REDUCED_FOG = value);

    // Light level options
    public static final SimpleOption<Double> lightLevelColorRed = new SimpleOption<>("option.dragonlite.config.light_level_color_red",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_color_red"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 1.0D,
            value -> Config.LIGHT_LEVEL_COLOR_RED = value);
    public static final SimpleOption<Double> lightLevelColorGreen = new SimpleOption<>("option.dragonlite.config.light_level_color_green",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_color_green"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.0D,
            value -> Config.LIGHT_LEVEL_COLOR_GREEN = value);
    public static final SimpleOption<Double> lightLevelColorBlue = new SimpleOption<>("option.dragonlite.config.light_level_color_blue",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_color_blue"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.0D,
            value -> Config.LIGHT_LEVEL_COLOR_BLUE = value);
    public static final SimpleOption<Double> lightLevelAlpha = new SimpleOption<>("option.dragonlite.config.light_level_alpha",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_alpha"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 100)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.1D, 1.0D),
            0.3D,
            value -> Config.LIGHT_LEVEL_ALPHA = value);
    public static final SimpleOption<Double> lightLevelSquareSize = new SimpleOption<>("option.dragonlite.config.light_level_square_size",
            SimpleOption.constantTooltip(Text.translatable("option.dragonlite.config.light_level_square_size.tooltip")),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_square_size"), (int)(value * 200.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(5, 45)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.05D, 0.45D),
            0.25D,
            value -> Config.LIGHT_LEVEL_SQUARE_SIZE = value);


    // Freecam options
    public static final SimpleOption<Double> freecamFlightSpeed = new SimpleOption<>("option.dragonlite.config.freecam_flight_speed",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.freecam_flight_speed"), (int)(value * 200.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 100)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.1D, 1.0D),
            0.5D,
            value -> Config.FREECAM_FLIGHT_SPEED = value);

    // Full Bright options
    public static final SimpleOption<Boolean> fullBrightOnFreecam = SimpleOption.ofBoolean("option.dragonlite.config.full_bright_on_freecam",
            true,
            value -> Config.ENABLE_FULL_BRIGHT_ON_FREECAM = value);

    // Tooltip durability
    public static final SimpleOption<Boolean> durabilityTooltip = SimpleOption.ofBoolean("option.dragonlite.config.durability_tooltip",
            SimpleOption.constantTooltip(Text.translatable("option.dragonlite.config.durability_tooltip.tooltip")),
            true,
            value -> Config.ENABLE_DURABILITY_TOOLTIP = value);


    public static void Load() {
        reduceFog.setValue(Config.ENABLE_REDUCED_FOG);
        compass.setValue(Config.ENABLE_COMPASS);
        compassPlacement.setValue(HudPlacement.byId(Config.COMPASS_PLACEMENT));
        compassShadow.setValue(Config.COMPASS_SHADOW);
        compassBackground.setValue(Config.COMPASS_BACKGROUND);
        compassScale.setValue(Config.COMPASS_SCALE);
        lightLevelColorRed.setValue(Config.LIGHT_LEVEL_COLOR_RED);
        lightLevelColorGreen.setValue(Config.LIGHT_LEVEL_COLOR_GREEN);
        lightLevelColorBlue.setValue(Config.LIGHT_LEVEL_COLOR_BLUE);
        lightLevelAlpha.setValue(Config.LIGHT_LEVEL_ALPHA);
        lightLevelSquareSize.setValue(Config.LIGHT_LEVEL_SQUARE_SIZE);
        smokeyFurnace.setValue(Config.ENABLE_SMOKEY_FURNACE);
        freecamFlightSpeed.setValue(Config.FREECAM_FLIGHT_SPEED);
        fullBrightOnFreecam.setValue(Config.ENABLE_FULL_BRIGHT_ON_FREECAM);
        durabilityTooltip.setValue(Config.ENABLE_DURABILITY_TOOLTIP);
    }

    public static int getLightLevelColor() {
        return new Color((float)Config.LIGHT_LEVEL_COLOR_RED, (float)Config.LIGHT_LEVEL_COLOR_GREEN, (float)Config.LIGHT_LEVEL_COLOR_BLUE).getRGB();
    }
}
