package hen676.dragonlite.gui.screen;

import hen676.dragonlite.config.ConfigLoader;
import hen676.dragonlite.gui.screen.option.Options;
import hen676.dragonlite.gui.widget.ColorWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

@SuppressWarnings("ConstantConditions")
@Environment(EnvType.CLIENT)
public class LightLevelConfigScreen extends Screen {
    private final Screen parent;

    protected LightLevelConfigScreen(Screen parent) {
        super(Text.translatable("screen.dragonlite.light_level_config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(2);

        adder.add(new ColorWidget(310,26, Options::getLightLevelColor), 2);
        adder.add(Options.lightLevelColorRed.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.lightLevelColorGreen.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.lightLevelColorBlue.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.lightLevelAlpha.createWidget(this.client.options, 0, 0, 150));
        adder.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.client.setScreen(this.parent)).width(200).build(), 2, adder.copyPositioner().marginTop(6));

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6 - 12, this.width, this.height, 0.5f, 0.0f);
        gridWidget.forEachChild(this::addDrawableChild);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, DyeColor.LIGHT_BLUE.getSignColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void removed() {
        ConfigLoader.createOrSaveConfig();
    }

}
