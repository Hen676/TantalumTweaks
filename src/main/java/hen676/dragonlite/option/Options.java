package hen676.dragonlite.option;

import com.mojang.serialization.Codec;
import hen676.dragonlite.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

import java.util.Arrays;

@Environment(EnvType.CLIENT)
public class Options {
    public static final SimpleOption<Boolean> mobHealth = SimpleOption.ofBoolean("option.dragonlite.config.enable_mob_health",
            true,
            value -> Config.ENABLE_MOB_HEALTH = value);
    public static final SimpleOption<Boolean> zoom = SimpleOption.ofBoolean("option.dragonlite.config.enable_zoom",
            true,
            value -> Config.ENABLE_ZOOM = value);
    public static final SimpleOption<Boolean> reduceFog = SimpleOption.ofBoolean("option.dragonlite.config.enable_reduce_fog",
            true,
            value -> Config.ENABLE_REDUCED_FOG = value);
    public static final SimpleOption<Boolean> lightLevel = SimpleOption.ofBoolean("option.dragonlite.config.enable_light_level",
            true,
            value -> Config.ENABLE_LIGHT_LEVEL = value);
    public static final SimpleOption<Boolean> smokeyFurnace = SimpleOption.ofBoolean("option.dragonlite.config.enable_smokey_furnace",
            true,
            value -> Config.ENABLE_SMOKEY_FURNACE = value);

    public static final SimpleOption<DyeColor> lightLevelColor = new SimpleOption<>("option.dragonlite.config.light_level_color",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("color.minecraft." + value.toString().toLowerCase()).setStyle(Style.EMPTY.withColor(value.getSignColor())),
            new SimpleOption.PotentialValuesBasedCallbacks<>(Arrays.asList(DyeColor.values()), Codec.INT.xmap(DyeColor::byId, DyeColor::getId)),
            DyeColor.RED,
            value -> Config.LIGHT_LEVEL_COLOR = value.getId());

    public static final SimpleOption<Double> zoomLevel = new SimpleOption<>("option.dragonlite.config.zoom_level",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("options.percent_value", Text.translatable("option.dragonlite.config.zoom_amount"), (int)(value * 100.0D)),
            (new SimpleOption.ValidatingIntSliderCallbacks(10, 40)).withModifier(
                            (sliderProgressValue) -> (double)sliderProgressValue / 50.0D,
                            (value) -> (int) (value * 50.0D)),
            Codec.doubleRange(0.2D, 0.8D),
            0.35D,
            value -> Config.ZOOM_AMOUNT = value);

    public static void Load() {
        mobHealth.setValue(Config.ENABLE_MOB_HEALTH);
        zoom.setValue(Config.ENABLE_ZOOM);
        reduceFog.setValue(Config.ENABLE_REDUCED_FOG);
        lightLevel.setValue(Config.ENABLE_LIGHT_LEVEL);
        lightLevelColor.setValue(DyeColor.byId(Config.LIGHT_LEVEL_COLOR));
        smokeyFurnace.setValue(Config.ENABLE_SMOKEY_FURNACE);
    }
}
