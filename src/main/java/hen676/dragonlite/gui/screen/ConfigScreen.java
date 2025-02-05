package hen676.dragonlite.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import hen676.dragonlite.DragonLite;
import hen676.dragonlite.config.ConfigLoader;
import hen676.dragonlite.gui.screen.option.Options;
import hen676.dragonlite.gui.widget.ColorWidget;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenRect;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.tab.GridScreenTab;
import net.minecraft.client.gui.tab.TabManager;
import net.minecraft.client.gui.widget.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;

public class ConfigScreen extends Screen {
    private final Screen parent;
    private final TabManager tabManager = new TabManager(this::addDrawableChild, this::remove);
    private TabNavigationWidget tabNavigation;
    private final ThreePartsLayoutWidget layout = new ThreePartsLayoutWidget(this);
    private static final int BUTTON_WIDTH_COL_1 = 200;
    private static final int BUTTON_WIDTH_COL_2 = 150;
    private static final int BUTTON_WIDTH_COL_3 = 100;
    private static final int COLOR_WIDGET_WIDTH_COL_3 = 316;

    public ConfigScreen(Screen parent) {
        super(Text.translatable("screen.dragonlite.config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.tabNavigation = TabNavigationWidget.builder(this.tabManager, this.width)
                .tabs(new GeneralTab(), new CompassTab(), new LightLevelTab()).build();
        this.addDrawableChild(this.tabNavigation);
        DirectionalLayoutWidget directionalLayoutWidget = this.layout.addFooter(
                DirectionalLayoutWidget.horizontal().spacing(8));
        directionalLayoutWidget.add(ButtonWidget.builder(
                ScreenTexts.DONE, button -> this.close()).width(200).build());
        this.layout.forEachChild(child -> {
            child.setNavigationOrder(1);
            this.addDrawableChild(child);
        });
        this.tabNavigation.selectTab(0, false);
        this.initTabNavigation();
    }

    protected void initTabNavigation() {
        if (this.tabNavigation == null) {
            return;
        }
        this.tabNavigation.setWidth(this.width);
        this.tabNavigation.init();
        int i = this.tabNavigation.getNavigationFocus().getBottom();
        ScreenRect screenRect = new ScreenRect(
                0,
                i,
                this.width,
                this.height - this.layout.getFooterHeight() - i);
        this.tabManager.setTabArea(screenRect);
        this.layout.setHeaderHeight(i);
        this.layout.refreshPositions();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        super.render(context, mouseX, mouseY, delta);
        RenderSystem.enableBlend();
        context.drawTexture(
                RenderLayer::getGuiTextured,
                Screen.FOOTER_SEPARATOR_TEXTURE,
                0,
                this.height - this.layout.getFooterHeight() - 2,
                0.0f,
                0.0f,
                this.width,
                2,
                32,
                2);
        RenderSystem.disableBlend();
    }

    @Override
    protected void renderDarkening(DrawContext context) {
        context.drawTexture(
                RenderLayer::getGuiTextured,
                CreateWorldScreen.TAB_HEADER_BACKGROUND_TEXTURE,
                0,
                0,
                0.0f,
                0.0f,
                this.width,
                this.layout.getHeaderHeight(),
                16,
                16);
        this.renderDarkening(context, 0, this.layout.getHeaderHeight(), this.width, this.height);
    }

    @Override
    public void removed() {
        ConfigLoader.createOrSaveConfig();
    }

    @Override
    public void close() {
        assert this.client != null;
        this.client.setScreen(this.parent);
    }

    @Environment(value= EnvType.CLIENT)
    static class GeneralTab extends GridScreenTab {
        public GeneralTab() {
            super(Text.translatable("screen.dragonlite.config.title"));
            GridWidget.Adder adder = this.grid.setColumnSpacing(8).setRowSpacing(4).createAdder(2);
            adder.add(Options.reduceFog.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.smokeyFurnace.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.zoomLevel.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.freecamFlightSpeed.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.durabilityTooltip.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.foodTooltip.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.fuelTooltip.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.compostTooltip.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.effectTooltip.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
        }
    }

    @Environment(value=EnvType.CLIENT)
    class CompassTab extends GridScreenTab {
        public CompassTab() {
            super(Text.translatable("screen.dragonlite.compass_config.title"));
            GridWidget.Adder adder = this.grid.setColumnSpacing(8).setRowSpacing(4).createAdder(2);
            adder.add(
                    Options.compass.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_1),
                    2,
                    Positioner.create().alignHorizontalCenter());
            adder.add(Options.compassPlacement.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.compassScale.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.compassGap.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.compassShadow.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.compassXOffset.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(Options.compassYOffset.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
            adder.add(ButtonWidget.builder(
                    Text.translatable("options.dragonlite.compass_color_config"), button ->
                    {
                        if (ConfigScreen.this.client != null)
                            ConfigScreen.this.client.setScreen(new CompassColorConfigScreen(ConfigScreen.this));
                    }).width(BUTTON_WIDTH_COL_2).build());
            adder.add(Options.compassAlpha.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_2));
        }
    }

    @Environment(value=EnvType.CLIENT)
    static class LightLevelTab extends GridScreenTab {
        public LightLevelTab() {
            super(Text.translatable("screen.dragonlite.light_level_config.title"));
            GridWidget.Adder adder = this.grid.setColumnSpacing(8).setRowSpacing(4).createAdder(3);
            adder.add(Options.lightLevelAlpha.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
            adder.add(Options.lightLevelSquareSize.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
            adder.add(
                    new ColorWidget(COLOR_WIDGET_WIDTH_COL_3,10, Options::getLightLevelColor),
                    3);
            adder.add(Options.lightLevelColorRed.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
            adder.add(Options.lightLevelColorGreen.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
            adder.add(Options.lightLevelColorBlue.createWidget(DragonLite.MC.options, 0, 0, BUTTON_WIDTH_COL_3));
        }
    }
}
