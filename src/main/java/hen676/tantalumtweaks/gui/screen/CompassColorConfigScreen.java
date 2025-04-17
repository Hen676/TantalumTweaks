package hen676.tantalumtweaks.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import hen676.tantalumtweaks.TantalumTweaks;
import hen676.tantalumtweaks.gui.screen.option.Options;
import hen676.tantalumtweaks.gui.widget.ColorWidget;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class CompassColorConfigScreen extends Screen {
    private final Screen parent;
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private static final int BUTTON_WIDTH_COL_3 = 100;
    private static final int TITLE_WIDTH = 200;
    private static final int DEFAULT_BUTTON_HEIGHT = 20;
    private static final int DEFAULT_COLOR_HEIGHT = 10;
    private static final int COLOR_WIDGET_WIDTH_COL_3 = 208;

    public CompassColorConfigScreen(Screen parent) {
        super(Text.translatable("screen.tantalumtweaks.compass_color_config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.initHeader();
        this.initBody();
        this.layout.forEachChild(this::addDrawableChild);
        this.refreshWidgetPositions();
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    @Override
    protected void refreshWidgetPositions() {
        this.layout.refreshPositions();
    }

    protected void initHeader() {
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addHeader(DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(new TextWidget(TITLE_WIDTH, DEFAULT_BUTTON_HEIGHT, this.title, this.textRenderer));
        directionalLayoutWidget.add(ButtonWidget.builder(ScreenTexts.DONE, button -> this.close()).width(BUTTON_WIDTH_COL_3).build());
    }

    protected void initBody() {
        GridWidget gridWidget = new GridWidget();
        GridWidget.Adder adder = gridWidget.setColumnSpacing(8).setRowSpacing(4).createAdder(3);
        adder.add(getTextWidgetForGrid("options.tantalumtweaks.compass_color_config.x"), 1);
        adder.add(new ColorWidget(COLOR_WIDGET_WIDTH_COL_3,DEFAULT_COLOR_HEIGHT, Options::getCompassXColor), 2);
        adder.add(Options.compassColorRedX.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(Options.compassColorGreenX.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(Options.compassColorBlueX.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(getTextWidgetForGrid("options.tantalumtweaks.compass_color_config.y"), 1);
        adder.add(new ColorWidget(COLOR_WIDGET_WIDTH_COL_3,DEFAULT_COLOR_HEIGHT, Options::getCompassYColor), 2);
        adder.add(Options.compassColorRedY.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(Options.compassColorGreenY.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(Options.compassColorBlueY.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(getTextWidgetForGrid("options.tantalumtweaks.compass_color_config.z"), 1);
        adder.add(new ColorWidget(COLOR_WIDGET_WIDTH_COL_3,DEFAULT_COLOR_HEIGHT, Options::getCompassZColor), 2);
        adder.add(Options.compassColorRedZ.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(Options.compassColorGreenZ.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(Options.compassColorBlueZ.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(getTextWidgetForGrid("options.tantalumtweaks.compass_color_config.rotation"), 1);
        adder.add(new ColorWidget(COLOR_WIDGET_WIDTH_COL_3,DEFAULT_COLOR_HEIGHT, Options::getCompassDirectionColor), 2);
        adder.add(Options.compassColorRedRotation.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(Options.compassColorGreenRotation.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        adder.add(Options.compassColorBlueRotation.createWidget(TantalumTweaks.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        this.layout.addBody(gridWidget);
    }

    private TextWidget getTextWidgetForGrid(String key) {
        TextWidget textWidget = new TextWidget(BUTTON_WIDTH_COL_3, DEFAULT_COLOR_HEIGHT, Text.translatable(key), textRenderer).alignRight();
        return textWidget;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        if (this.client == null) return;
        Identifier identifier = this.client.world == null ? Screen.HEADER_SEPARATOR_TEXTURE : Screen.INWORLD_HEADER_SEPARATOR_TEXTURE;
        context.drawTexture(RenderLayer::getGuiTextured, identifier, 0, this.layout.getHeaderHeight() - 2, 0.0F, 0.0F, this.width, 2, 32, 2);
    }

    @Override
    protected void renderDarkening(DrawContext context) {
        context.drawTexture(RenderLayer::getGuiTextured, CreateWorldScreen.TAB_HEADER_BACKGROUND_TEXTURE, 0, 0, 0.0f, 0.0f, this.width, this.layout.getHeaderHeight(), 16, 16);
        this.renderDarkening(context, 0, this.layout.getHeaderHeight(), this.width, this.height);
    }
}
