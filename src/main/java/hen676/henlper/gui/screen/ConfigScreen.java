package hen676.henlper.gui.screen;

import hen676.henlper.config.Config;
import hen676.henlper.config.ConfigLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.option.CyclingOption;
import net.minecraft.client.option.DoubleOption;
import net.minecraft.client.option.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private ButtonListWidget list;
    private final Screen parent;
    private static final Option[] OPTIONS;

    public ConfigScreen(Screen parent) {
        super(new TranslatableText("screen.henlper.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addAll(OPTIONS);

        this.addSelectableChild(this.list);

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, button -> {
            ConfigLoader.createOrSaveConfig();
            if(this.client != null)
                this.client.setScreen(this.parent);
        }));
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        this.list.render(matrices, mouseX, mouseY, delta);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 5, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }

    @Override
    public void removed() {
        ConfigLoader.createOrSaveConfig();
    }

    static {
        OPTIONS = new Option[]{
                CyclingOption.create("option.henlper.config.enable_zoom",
                        gameOptions -> Config.ENABLE_ZOOM,
                        (gameOptions, option, value) -> Config.ENABLE_ZOOM = value
                        ),
                new DoubleOption(
                        "option.henlper.config.zoom_level",
                        2D,
                        8D,
                        0.5F,
                        gameOptions -> Config.ZOOM_AMOUNT*10,
                        (gameOptions, aDouble) -> Config.ZOOM_AMOUNT = aDouble/10,
                        (gameOptions, doubleOption) -> new TranslatableText("option.henlper.config.zoom_amount").append(": " + doubleOption.get(gameOptions)/10)
                ),
                CyclingOption.create("option.henlper.config.enable_reduce_fog",
                        gameOptions -> Config.ENABLE_REDUCED_FOG,
                        (gameOptions, option, value) -> Config.ENABLE_REDUCED_FOG = value
                ),
                CyclingOption.create("option.henlper.config.enable_light_level",
                        gameOptions -> Config.ENABLE_LIGHT_LEVEL,
                        (gameOptions, option, value) -> Config.ENABLE_LIGHT_LEVEL = value
                ),
                /*new DoubleOption(
                        "option.henlper.config.light_level_color",
                        0D,
                        360D,
                        1F,
                        gameOptions -> (double) Config.LIGHT_LEVEL_COLOR,
                        (gameOptions, aDouble) -> Config.LIGHT_LEVEL_COLOR = aDouble.floatValue(),
                        (gameOptions, doubleOption) -> new TranslatableText("option.henlper.config.light_level_hue").append(": " + doubleOption.get(gameOptions))
                ),*/
                CyclingOption.create(
                        "option.henlper.config.light_level_color",
                        DyeColor.values(),
                        colorOption -> new TranslatableText(colorOption.getName()),
                        gameOptions -> DyeColor.byId(Config.LIGHT_LEVEL_COLOR),
                        (gameOptions, option, colorOption) -> Config.LIGHT_LEVEL_COLOR = colorOption.getId()
                ),
                CyclingOption.create("option.henlper.config.enable_mob_health",
                        gameOptions -> Config.ENABLE_MOB_HEALTH,
                        (gameOptions, option, value) -> Config.ENABLE_MOB_HEALTH = value
                ),
                CyclingOption.create("option.henlper.config.enable_smokey_furnace",
                        gameOptions -> Config.ENABLE_SMOKEY_FURNACE,
                        (gameOptions, option, value) -> Config.ENABLE_SMOKEY_FURNACE = value
                )
        };
    }
}
