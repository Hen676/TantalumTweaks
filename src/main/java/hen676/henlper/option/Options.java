package hen676.henlper.option;

import com.mojang.serialization.Codec;
import hen676.henlper.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)
public class Options {
    public static final SimpleOption<Boolean> mobHealth = SimpleOption.ofBoolean("option.henlper.config.enable_mob_health",
            true,
            value -> Config.ENABLE_MOB_HEALTH = value);
    public static final SimpleOption<Boolean> zoom = SimpleOption.ofBoolean("option.henlper.config.enable_zoom",
            true,
            value -> Config.ENABLE_ZOOM = value);
    public static final SimpleOption<Boolean> reduceFog = SimpleOption.ofBoolean("option.henlper.config.enable_reduce_fog",
            true,
            value -> Config.ENABLE_REDUCED_FOG = value);
    public static final SimpleOption<Boolean> lightLevel = SimpleOption.ofBoolean("option.henlper.config.enable_light_level",
            true,
            value -> Config.ENABLE_LIGHT_LEVEL = value);
    public static final SimpleOption<Boolean> smokeyFurnace = SimpleOption.ofBoolean("option.henlper.config.enable_smokey_furnace",
            true,
            value -> Config.ENABLE_SMOKEY_FURNACE = value);
    public static final SimpleOption<Double> zoomLevel = new SimpleOption<Double>("option.henlper.config.zoom_level",
            SimpleOption.emptyTooltip(),
            (optionText, value) -> Text.translatable("option.henlper.config.zoom_amount").append(": " + value * 10),
            SimpleOption.DoubleSliderCallbacks.INSTANCE,
            Codec.doubleRange(2.0D, 8.0D),
            2.0D,
            value -> Config.ZOOM_AMOUNT = value / 10);


    /*
    "option.henlper.config.zoom_level",
            2D,
            8D,
            0.5F,
    gameOptions -> Config.ZOOM_AMOUNT*10,
            (gameOptions, aDouble) -> Config.ZOOM_AMOUNT = aDouble/10,
            (gameOptions, doubleOption) -> Text.translatable("option.henlper.config.zoom_amount").append(": " + doubleOption.get(gameOptions)/10)
    */

    public static void Load() {
        mobHealth.setValue(Config.ENABLE_MOB_HEALTH);
        zoom.setValue(Config.ENABLE_ZOOM);
        reduceFog.setValue(Config.ENABLE_REDUCED_FOG);
        lightLevel.setValue(Config.ENABLE_LIGHT_LEVEL);
        smokeyFurnace.setValue(Config.ENABLE_SMOKEY_FURNACE);
    }
}
