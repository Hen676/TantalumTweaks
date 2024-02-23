package hen676.dragonlite.gui.screen;

import hen676.dragonlite.config.ConfigLoader;
import hen676.dragonlite.gui.screen.option.Options;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.EmptyWidget;
import net.minecraft.client.gui.widget.GridWidget;
import net.minecraft.client.gui.widget.SimplePositioningWidget;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class ConfigScreen extends Screen {
    private static final Text HUD_TEXT = Text.translatable("options.dragonlite.hud");
    private static final Text LIGHT_LEVEL_TEXT = Text.translatable("options.dragonlite.light_level");
    private final Screen parent;

    public ConfigScreen(Screen parent) {
        super(Text.translatable("screen.dragonlite.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(2);

        if(this.client == null)
            return;

        adder.add(this.createButton(HUD_TEXT, () -> new HudConfigScreen(this)));
        adder.add(this.createButton(LIGHT_LEVEL_TEXT, () -> new LightLevelConfigScreen(this)));
        adder.add(EmptyWidget.ofHeight(26), 2);
        adder.add(Options.reduceFog.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.smokeyFurnace.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.zoomLevel.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.fullBrightOnFreecam.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.freecamFlightSpeed.createWidget(this.client.options, 0, 0, 150));
        adder.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.client.setScreen(this.parent)).width(200).build(), 2, adder.copyPositioner().marginTop(6));

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6 - 12, this.width, this.height, 0.5f, 0.0f);
        gridWidget.forEachChild(this::addDrawableChild);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, DyeColor.LIGHT_BLUE.getSignColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void removed() {
        ConfigLoader.createOrSaveConfig();
    }

    private ButtonWidget createButton(Text message, Supplier<Screen> screenSupplier) {
        return ButtonWidget.builder(message, button -> {
            this.client.setScreen(screenSupplier.get());
        }).build();
    }
}
