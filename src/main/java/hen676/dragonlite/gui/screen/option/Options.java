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
            true,
            value -> Config.ENABLE_COMPASS = value);
    public static final SimpleOption<Double> compassColorRed = new SimpleOption<>("option.dragonlite.config.compass_color_red",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.compass_color_red"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.7D,
            value -> Config.COMPASS_COLOR_RED = value);
    public static final SimpleOption<Double> compassColorGreen = new SimpleOption<>("option.dragonlite.config.compass_color_green",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.compass_color_green"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.1D,
            value -> Config.COMPASS_COLOR_GREEN = value);
    public static final SimpleOption<Double> compassColorBlue = new SimpleOption<>("option.dragonlite.config.compass_color_blue",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.compass_color_blue"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.1D,
            value -> Config.COMPASS_COLOR_BLUE = value);
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
            Codec.doubleRange(0.0D, 1.0D), 0.7D,
            value -> Config.LIGHT_LEVEL_COLOR_RED = value);
    public static final SimpleOption<Double> lightLevelColorGreen = new SimpleOption<>("option.dragonlite.config.light_level_color_green",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_color_green"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.1D,
            value -> Config.LIGHT_LEVEL_COLOR_GREEN = value);
    public static final SimpleOption<Double> lightLevelColorBlue = new SimpleOption<>("option.dragonlite.config.light_level_color_blue",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_color_blue"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.1D,
            value -> Config.LIGHT_LEVEL_COLOR_BLUE = value);
    public static final SimpleOption<Double> lightLevelAlpha = new SimpleOption<>("option.dragonlite.config.light_level_alpha",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_alpha"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 100)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.1D, 1.0D),
            0.5D,
            value -> Config.LIGHT_LEVEL_ALPHA = value);

    public static void Load() {
        reduceFog.setValue(Config.ENABLE_REDUCED_FOG);

        compass.setValue(Config.ENABLE_COMPASS);
        compassPlacement.setValue(HudPlacement.byId(Config.COMPASS_PLACEMENT));
        compassColorRed.setValue(Config.COMPASS_COLOR_RED);
        compassColorGreen.setValue(Config.COMPASS_COLOR_GREEN);
        compassColorBlue.setValue(Config.COMPASS_COLOR_BLUE);
        compassScale.setValue(Config.COMPASS_SCALE);

        lightLevelColorRed.setValue(Config.LIGHT_LEVEL_COLOR_RED);
        lightLevelColorGreen.setValue(Config.LIGHT_LEVEL_COLOR_GREEN);
        lightLevelColorBlue.setValue(Config.LIGHT_LEVEL_COLOR_BLUE);
        lightLevelAlpha.setValue(Config.LIGHT_LEVEL_ALPHA);

        smokeyFurnace.setValue(Config.ENABLE_SMOKEY_FURNACE);
    }

    public static int getCompassColor() {
        return new Color((float)Config.COMPASS_COLOR_RED, (float)Config.COMPASS_COLOR_GREEN, (float)Config.COMPASS_COLOR_BLUE).getRGB();
    }

    public static int getLightLevelColor() {
        return new Color((float)Config.LIGHT_LEVEL_COLOR_RED, (float)Config.LIGHT_LEVEL_COLOR_GREEN, (float)Config.LIGHT_LEVEL_COLOR_BLUE).getRGB();
    }
}
