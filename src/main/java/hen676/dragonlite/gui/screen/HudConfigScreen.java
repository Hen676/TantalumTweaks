package hen676.dragonlite.gui.screen;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.config.ConfigLoader;
import hen676.dragonlite.gui.screen.option.HudPlacement;
import hen676.dragonlite.gui.screen.option.Options;
import hen676.dragonlite.gui.widget.ColorWidget;
import hen676.dragonlite.render.HudRenderer;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;


@Environment(EnvType.CLIENT)
public class HudConfigScreen extends Screen {
    private final Screen parent;
    private boolean EnablePreview = false;

    protected HudConfigScreen(Screen parent) {
        super(Text.translatable("screen.dragonlite.hud_config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        GridWidget gridWidget = new GridWidget();
        gridWidget.getMainPositioner().marginX(5).marginBottom(4).alignHorizontalCenter();
        GridWidget.Adder adder = gridWidget.createAdder(2);

        if(this.client == null)
            return;

        adder.add(Options.compass.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.compassPlacement.createWidget(this.client.options, 0, 0, 150));
        adder.add(new ColorWidget(310,26, Options::getCompassColor), 2);
        adder.add(Options.compassColorRed.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.compassColorGreen.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.compassColorBlue.createWidget(this.client.options, 0, 0, 150));
        adder.add(Options.compassScale.createWidget(this.client.options, 0, 0, 150));
        adder.add(ButtonWidget.builder(Text.translatable("option.dragonlite.config.toggle_preview"), button -> this.EnablePreview = !this.EnablePreview).width(200).build(), 2, adder.copyPositioner().marginTop(6));
        adder.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.client.setScreen(this.parent)).width(200).build(), 2, adder.copyPositioner().marginTop(6));

        gridWidget.refreshPositions();
        SimplePositioningWidget.setPos(gridWidget, 0, this.height / 6 - 12, this.width, this.height, 0.5f, 0.0f);
        gridWidget.forEachChild(this::addDrawableChild);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        if(EnablePreview)
            HudRenderer.draw(DragonLite.MC, Direction.NORTH, BlockPos.ORIGIN, context, HudPlacement.byId(Config.COMPASS_PLACEMENT));
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, DyeColor.LIGHT_BLUE.getSignColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void removed() {
        ConfigLoader.createOrSaveConfig();
    }
}
