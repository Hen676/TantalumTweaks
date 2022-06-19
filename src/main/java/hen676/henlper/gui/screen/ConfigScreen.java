package hen676.henlper.gui.screen;

import hen676.henlper.config.Config;
import hen676.henlper.config.ConfigLoader;
import hen676.henlper.option.Options;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.CyclingButtonWidget;
import net.minecraft.client.gui.widget.OptionSliderWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private ButtonListWidget list;
    private final Screen parent;
    private static final SimpleOption[] OPTIONS;

    public ConfigScreen(Screen parent) {
        super(Text.translatable("screen.henlper.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addAll(OPTIONS);

        this.addSelectableChild(this.list);

        this.addDrawableChild(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, title, button -> {
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

        OPTIONS = new SimpleOption[]{
                Options.lightLevel,
                Options.mobHealth,
                Options.reduceFog,
                Options.smokeyFurnace,
                Options.zoom,
                Options.zoomLevel

                /*

                SimpleOption.SliderCallbacks<Double>("option.henlper.config.zoom_level",()-> Config.ZOOM_AMOUNT*10,2.0D),

                (
                        "option.henlper.config.zoom_level",
                        2D,
                        8D,
                        0.5F,
                        gameOptions -> Config.ZOOM_AMOUNT*10,
                        (gameOptions, aDouble) -> Config.ZOOM_AMOUNT = aDouble/10,
                        (gameOptions, doubleOption) -> Text.translatable("option.henlper.config.zoom_amount").append(": " + doubleOption.get(gameOptions)/10)
                ),
                new DoubleOption(
                        "option.henlper.config.light_level_color",
                        0D,
                        360D,
                        1F,
                        gameOptions -> (double) Config.LIGHT_LEVEL_COLOR,
                        (gameOptions, aDouble) -> Config.LIGHT_LEVEL_COLOR = aDouble.floatValue(),
                        (gameOptions, doubleOption) -> new TranslatableText("option.henlper.config.light_level_hue").append(": " + doubleOption.get(gameOptions))
                ),


                )*/
        };
    }
}
