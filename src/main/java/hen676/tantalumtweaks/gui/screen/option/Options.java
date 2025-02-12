package hen676.tantalumtweaks.gui.screen.option;

import com.mojang.serialization.Codec;
import hen676.tantalumtweaks.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

import java.awt.*;
import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class Options {

    // Compass options
    public static final SimpleOption<Boolean> compass = SimpleOption.ofBoolean("option.tantalumtweaks.config.enable_compass",
            false,
            value -> Config.ENABLE_COMPASS = value);
    public static final SimpleOption<Boolean> compassShadow = SimpleOption.ofBoolean("option.tantalumtweaks.config.compass_shadow",
            true,
            value -> Config.COMPASS_SHADOW = value);
    public static final SimpleOption<CompassPlacement> compassPlacement = new SimpleOption<>("option.tantalumtweaks.config.compass_placement",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("option.tantalumtweaks.config." + value.toString().toLowerCase()),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(CompassPlacement.values()), Codec.INT.xmap(CompassPlacement::byId, CompassPlacement::getId)),
            CompassPlacement.TOP_LEFT,
            value -> Config.COMPASS_PLACEMENT = value.getId());
    public static final SimpleOption<Double> compassScale = new SimpleOption<>("option.tantalumtweaks.config.compass_scale",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.compass_scale"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 100)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 50.0D,
                    (value) -> (int) (value * 50.0D)),
            Codec.doubleRange(0.2D, 2D),
            1.0D,
            value -> Config.COMPASS_SCALE = value);
    public static final SimpleOption<Double> compassAlpha = new SimpleOption<>("option.tantalumtweaks.config.compass_alpha",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.compass_alpha"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 100)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.1D, 1.0D),
            0.8D,
            value -> Config.COMPASS_ALPHA = value);
    public static final SimpleOption<Integer> compassXOffset = new SimpleOption<>("option.tantalumtweaks.config.compass_x_offset",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.pixel_value", Text.translatable("option.tantalumtweaks.config.compass_x_offset"), value),
            new SimpleOption.ValidatingIntSliderCallbacks(0, 30),
            Codec.intRange(0, 30),
            4,
            value -> Config.COMPASS_X_OFFSET = value);
    public static final SimpleOption<Integer> compassYOffset = new SimpleOption<>("option.tantalumtweaks.config.compass_y_offset",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.pixel_value", Text.translatable("option.tantalumtweaks.config.compass_y_offset"), value),
            new SimpleOption.ValidatingIntSliderCallbacks(0, 30),
            Codec.intRange(0, 30),
            4,
            value -> Config.COMPASS_Y_OFFSET = value);
    public static final SimpleOption<Integer> compassGap = new SimpleOption<>("option.tantalumtweaks.config.compass_gap",
            SimpleOption.constantTooltip(Text.translatable("option.tantalumtweaks.config.compass_gap.tooltip")),
            (optionText, value) -> Text.translatable("options.pixel_value", Text.translatable("option.tantalumtweaks.config.compass_gap"), value),
            new SimpleOption.ValidatingIntSliderCallbacks(4, 30),
            Codec.intRange(4, 30),
            6,
            value -> Config.COMPASS_GAP = value);

    // Compass colors
    public static final SimpleOption<Double> compassColorRedX = new SimpleOption<>("option.tantalumtweaks.config.red",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.red"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 1.0D,
            value -> Config.COMPASS_X_COLOR_RED = value);
    public static final SimpleOption<Double> compassColorGreenX = new SimpleOption<>("option.tantalumtweaks.config.green",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.green"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.4D,
            value -> Config.COMPASS_X_COLOR_GREEN = value);
    public static final SimpleOption<Double> compassColorBlueX = new SimpleOption<>("option.tantalumtweaks.config.blue",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.blue"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.4D,
            value -> Config.COMPASS_X_COLOR_BLUE = value);
    public static final SimpleOption<Double> compassColorRedY = new SimpleOption<>("option.tantalumtweaks.config.red",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.red"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.4D,
            value -> Config.COMPASS_Y_COLOR_RED = value);
    public static final SimpleOption<Double> compassColorGreenY = new SimpleOption<>("option.tantalumtweaks.config.green",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.green"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 1.0D,
            value -> Config.COMPASS_Y_COLOR_GREEN = value);
    public static final SimpleOption<Double> compassColorBlueY = new SimpleOption<>("option.tantalumtweaks.config.blue",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.blue"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.4D,
            value -> Config.COMPASS_Y_COLOR_BLUE = value);
    public static final SimpleOption<Double> compassColorRedZ = new SimpleOption<>("option.tantalumtweaks.config.red",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.red"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.4D,
            value -> Config.COMPASS_Z_COLOR_RED = value);
    public static final SimpleOption<Double> compassColorGreenZ = new SimpleOption<>("option.tantalumtweaks.config.green",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.green"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.4D,
            value -> Config.COMPASS_Z_COLOR_GREEN = value);
    public static final SimpleOption<Double> compassColorBlueZ = new SimpleOption<>("option.tantalumtweaks.config.blue",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.blue"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 1.0D,
            value -> Config.COMPASS_Z_COLOR_BLUE = value);
    public static final SimpleOption<Double> compassColorRedRotation = new SimpleOption<>("option.tantalumtweaks.config.red",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.red"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 1.0D,
            value -> Config.COMPASS_ROTATION_COLOR_RED = value);
    public static final SimpleOption<Double> compassColorGreenRotation = new SimpleOption<>("option.tantalumtweaks.config.green",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.green"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 1.0D,
            value -> Config.COMPASS_ROTATION_COLOR_GREEN = value);
    public static final SimpleOption<Double> compassColorBlueRotation = new SimpleOption<>("option.tantalumtweaks.config.blue",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.blue"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.4D,
            value -> Config.COMPASS_ROTATION_COLOR_BLUE = value);


    // Zoom options
    public static final SimpleOption<Double> zoomLevel = new SimpleOption<>("option.tantalumtweaks.config.zoom_level",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.zoom_amount"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 40)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 50.0D,
                    (value) -> (int) (value * 50.0D)),
            Codec.doubleRange(0.2D, 0.8D),
            0.35D,
            value -> Config.ZOOM_AMOUNT = value);

    // Smokey furnace option
    public static final SimpleOption<Boolean> smokeyFurnace = SimpleOption.ofBoolean("option.tantalumtweaks.config.enable_smokey_furnace",
            SimpleOption.constantTooltip(Text.translatable("option.tantalumtweaks.config.enable_smokey_furnace.tooltip")),
            true,
            value -> Config.ENABLE_SMOKEY_FURNACE = value);

    // Remove fog option
    public static final SimpleOption<Boolean> reduceFog = SimpleOption.ofBoolean("option.tantalumtweaks.config.enable_reduce_fog",
            true,
            value -> Config.ENABLE_REDUCED_FOG = value);

    // Light level options
    public static final SimpleOption<Double> lightLevelColorRed = new SimpleOption<>("option.tantalumtweaks.config.red",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.red"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 1.0D,
            value -> Config.LIGHT_LEVEL_COLOR_RED = value);
    public static final SimpleOption<Double> lightLevelColorGreen = new SimpleOption<>("option.tantalumtweaks.config.green",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.green"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.0D,
            value -> Config.LIGHT_LEVEL_COLOR_GREEN = value);
    public static final SimpleOption<Double> lightLevelColorBlue = new SimpleOption<>("option.tantalumtweaks.config.blue",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.blue"), (int) (value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(0, 100)).withModifier(
                    (sliderProgressValue) -> (double) sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.0D, 1.0D), 0.0D,
            value -> Config.LIGHT_LEVEL_COLOR_BLUE = value);
    public static final SimpleOption<Double> lightLevelAlpha = new SimpleOption<>("option.tantalumtweaks.config.light_level_alpha",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.light_level_alpha"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 100)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.1D, 1.0D),
            0.3D,
            value -> Config.LIGHT_LEVEL_ALPHA = value);
    public static final SimpleOption<Double> lightLevelSquareSize = new SimpleOption<>("option.tantalumtweaks.config.light_level_square_size",
            SimpleOption.constantTooltip(Text.translatable("option.tantalumtweaks.config.light_level_square_size.tooltip")),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.light_level_square_size"), (int)(value * 200.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(5, 45)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.05D, 0.45D),
            0.25D,
            value -> Config.LIGHT_LEVEL_SQUARE_SIZE = value);


    // Freecam options
    public static final SimpleOption<Double> freecamFlightSpeed = new SimpleOption<>("option.tantalumtweaks.config.freecam_flight_speed",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.tantalumtweaks.config.freecam_flight_speed"), (int)(value * 200.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 100)).withModifier(
                    (sliderProgressValue) -> (double)sliderProgressValue / 100.0D,
                    (value) -> (int) (value * 100.0D)),
            Codec.doubleRange(0.1D, 1.0D),
            0.5D,
            value -> Config.FREECAM_FLIGHT_SPEED = value);

    // Tooltip options
    public static final SimpleOption<Boolean> durabilityTooltip = SimpleOption.ofBoolean("option.tantalumtweaks.config.durability_tooltip",
            SimpleOption.constantTooltip(Text.translatable("option.tantalumtweaks.config.durability_tooltip.tooltip")),
            true,
            value -> Config.ENABLE_DURABILITY_TOOLTIP = value);
    public static final SimpleOption<Boolean> foodTooltip = SimpleOption.ofBoolean("option.tantalumtweaks.config.food_tooltip",
            true,
            value -> Config.ENABLE_FOOD_TOOLTIP = value);
    public static final SimpleOption<Boolean> fuelTooltip = SimpleOption.ofBoolean("option.tantalumtweaks.config.fuel_tooltip",
            true,
            value -> Config.ENABLE_FUEL_TOOLTIP = value);
    public static final SimpleOption<Boolean> compostTooltip = SimpleOption.ofBoolean("option.tantalumtweaks.config.compost_tooltip",
            true,
            value -> Config.ENABLE_COMPOST_TOOLTIP = value);
    public static final SimpleOption<Boolean> effectTooltip = SimpleOption.ofBoolean("option.tantalumtweaks.config.effect_tooltip",
            true,
            value -> Config.ENABLE_EFFECT_TOOLTIP = value);

    public static void Load() {
        reduceFog.setValue(Config.ENABLE_REDUCED_FOG);
        compass.setValue(Config.ENABLE_COMPASS);
        compassPlacement.setValue(CompassPlacement.byId(Config.COMPASS_PLACEMENT));
        compassShadow.setValue(Config.COMPASS_SHADOW);
        compassScale.setValue(Config.COMPASS_SCALE);
        compassAlpha.setValue(Config.COMPASS_ALPHA);
        compassXOffset.setValue(Config.COMPASS_X_OFFSET);
        compassYOffset.setValue(Config.COMPASS_Y_OFFSET);
        compassGap.setValue(Config.COMPASS_GAP);

        compassColorRedX.setValue(Config.COMPASS_X_COLOR_RED);
        compassColorGreenX.setValue(Config.COMPASS_X_COLOR_GREEN);
        compassColorBlueX.setValue(Config.COMPASS_X_COLOR_BLUE);
        compassColorRedY.setValue(Config.COMPASS_Y_COLOR_RED);
        compassColorGreenY.setValue(Config.COMPASS_Y_COLOR_GREEN);
        compassColorBlueY.setValue(Config.COMPASS_Y_COLOR_BLUE);
        compassColorRedZ.setValue(Config.COMPASS_Z_COLOR_RED);
        compassColorGreenZ.setValue(Config.COMPASS_Z_COLOR_GREEN);
        compassColorBlueZ.setValue(Config.COMPASS_Z_COLOR_BLUE);
        compassColorRedRotation.setValue(Config.COMPASS_ROTATION_COLOR_RED);
        compassColorGreenRotation.setValue(Config.COMPASS_ROTATION_COLOR_GREEN);
        compassColorBlueRotation.setValue(Config.COMPASS_ROTATION_COLOR_BLUE);

        lightLevelColorRed.setValue(Config.LIGHT_LEVEL_COLOR_RED);
        lightLevelColorGreen.setValue(Config.LIGHT_LEVEL_COLOR_GREEN);
        lightLevelColorBlue.setValue(Config.LIGHT_LEVEL_COLOR_BLUE);
        lightLevelAlpha.setValue(Config.LIGHT_LEVEL_ALPHA);
        lightLevelSquareSize.setValue(Config.LIGHT_LEVEL_SQUARE_SIZE);

        smokeyFurnace.setValue(Config.ENABLE_SMOKEY_FURNACE);
        freecamFlightSpeed.setValue(Config.FREECAM_FLIGHT_SPEED);
        durabilityTooltip.setValue(Config.ENABLE_DURABILITY_TOOLTIP);
        foodTooltip.setValue(Config.ENABLE_FOOD_TOOLTIP);
        fuelTooltip.setValue(Config.ENABLE_FUEL_TOOLTIP);
        compostTooltip.setValue(Config.ENABLE_COMPOST_TOOLTIP);
        effectTooltip.setValue(Config.ENABLE_EFFECT_TOOLTIP);
    }

    public static int getLightLevelColor() {
        return new Color((float)Config.LIGHT_LEVEL_COLOR_RED, (float)Config.LIGHT_LEVEL_COLOR_GREEN, (float)Config.LIGHT_LEVEL_COLOR_BLUE).getRGB();
    }

    public static int getCompassXColorWithAlpha() {
        return new Color((float)Config.COMPASS_X_COLOR_RED, (float)Config.COMPASS_X_COLOR_GREEN, (float)Config.COMPASS_X_COLOR_BLUE, (float)Config.COMPASS_ALPHA).getRGB();
    }

    public static int getCompassYColorWithAlpha() {
        return new Color((float)Config.COMPASS_Y_COLOR_RED, (float)Config.COMPASS_Y_COLOR_GREEN, (float)Config.COMPASS_Y_COLOR_BLUE, (float)Config.COMPASS_ALPHA).getRGB();
    }

    public static int getCompassZColorWithAlpha() {
        return new Color((float)Config.COMPASS_Z_COLOR_RED, (float)Config.COMPASS_Z_COLOR_GREEN, (float)Config.COMPASS_Z_COLOR_BLUE, (float)Config.COMPASS_ALPHA).getRGB();
    }

    public static int getCompassDirectionColorWithAlpha() {
        return new Color((float)Config.COMPASS_ROTATION_COLOR_RED, (float)Config.COMPASS_ROTATION_COLOR_GREEN, (float)Config.COMPASS_ROTATION_COLOR_BLUE, (float)Config.COMPASS_ALPHA).getRGB();
    }

    public static int getCompassXColor() {
        return new Color((float)Config.COMPASS_X_COLOR_RED, (float)Config.COMPASS_X_COLOR_GREEN, (float)Config.COMPASS_X_COLOR_BLUE).getRGB();
    }

    public static int getCompassYColor() {
        return new Color((float)Config.COMPASS_Y_COLOR_RED, (float)Config.COMPASS_Y_COLOR_GREEN, (float)Config.COMPASS_Y_COLOR_BLUE).getRGB();
    }

    public static int getCompassZColor() {
        return new Color((float)Config.COMPASS_Z_COLOR_RED, (float)Config.COMPASS_Z_COLOR_GREEN, (float)Config.COMPASS_Z_COLOR_BLUE).getRGB();
    }

    public static int getCompassDirectionColor() {
        return new Color((float)Config.COMPASS_ROTATION_COLOR_RED, (float)Config.COMPASS_ROTATION_COLOR_GREEN, (float)Config.COMPASS_ROTATION_COLOR_BLUE).getRGB();
    }
}
