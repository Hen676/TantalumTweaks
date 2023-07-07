package hen676.dragonlite.gui.screen.option;

import com.mojang.serialization.Codec;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.util.HudPlacement;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class Options {

    // Compass options
    public static final SimpleOption<Boolean> compass = SimpleOption.ofBoolean("option.dragonlite.config.enable_compass",
            true,
            value -> Config.ENABLE_COMPASS = value);
    public static final SimpleOption<DyeColor> compassColor = new SimpleOption<>("option.dragonlite.config.compass_color",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("color.minecraft." + value.toString().toLowerCase()).setStyle(Style.EMPTY.withColor(value.getSignColor())),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(DyeColor.values()), Codec.INT.xmap(DyeColor::byId, DyeColor::getId)),
            DyeColor.WHITE,
            value -> Config.COMPASS_COLOR = value.getId());
    public static final SimpleOption<HudPlacement> compassPlacement = new SimpleOption<>("option.dragonlite.config.compass_placement",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("option.dragonlite.config." + value.toString().toLowerCase()),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(HudPlacement.values()), Codec.INT.xmap(HudPlacement::byId, HudPlacement::getId)),
            HudPlacement.TOP_LEFT,
            value -> Config.COMPASS_PLACEMENT = value.getId());

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
    public static final SimpleOption<DyeColor> lightLevelColor = new SimpleOption<>("option.dragonlite.config.light_level_color",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("color.minecraft." + value.toString().toLowerCase()).setStyle(Style.EMPTY.withColor(value.getSignColor())),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(DyeColor.values()), Codec.INT.xmap(DyeColor::byId, DyeColor::getId)),
            DyeColor.RED,
            value -> Config.LIGHT_LEVEL_COLOR = value.getId());

    public static final SimpleOption<Double> lightLevelAlpha = new SimpleOption<>("option.dragonlite.config.light_level_alpha",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.light_level_alpha_amount"), (int)(value * 100.0D)),
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
        compassColor.setValue(DyeColor.byId(Config.COMPASS_COLOR));
        lightLevelColor.setValue(DyeColor.byId(Config.LIGHT_LEVEL_COLOR));
        smokeyFurnace.setValue(Config.ENABLE_SMOKEY_FURNACE);
        lightLevelAlpha.setValue(Config.LIGHT_LEVEL_ALPHA);
    }
}
