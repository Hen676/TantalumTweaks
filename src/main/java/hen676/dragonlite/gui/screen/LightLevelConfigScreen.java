package hen676.dragonlite.gui.screen;

import hen676.dragonlite.config.ConfigLoader;
import hen676.dragonlite.gui.screen.option.Options;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;

@Environment(EnvType.CLIENT)
public class LightLevelConfigScreen extends Screen {
    private OptionListWidget list;
    private final Screen parent;
    private static final SimpleOption<?>[] OPTIONS;

    protected LightLevelConfigScreen(Screen parent) {
        super(Text.translatable("screen.dragonlite.light_level_config.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        this.list = new OptionListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
        this.list.addAll(OPTIONS);

        this.addSelectableChild(this.list);

        this.addDrawableChild(ButtonWidget.builder(ScreenTexts.DONE, button -> {
            ConfigLoader.createOrSaveConfig();
            if(this.client != null)
                this.client.setScreen(this.parent);
        }).dimensions(this.width / 2 - 100, this.height - 27, 200, 20).build());
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        this.list.render(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 20, DyeColor.LIGHT_BLUE.getSignColor());
        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void removed() {
        ConfigLoader.createOrSaveConfig();
    }

    static {
        OPTIONS = new SimpleOption[]{
                Options.lightLevelAlpha,
                Options.lightLevelColor
        };
    }
}
