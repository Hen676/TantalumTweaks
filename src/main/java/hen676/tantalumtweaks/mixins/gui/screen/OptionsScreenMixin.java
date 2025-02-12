package hen676.tantalumtweaks.mixins.gui.screen;

import hen676.tantalumtweaks.TantalumTweaks;
import hen676.tantalumtweaks.gui.screen.ConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    @Unique
    ButtonWidget button = ButtonWidget.builder(
            Text.translatable("options.tantalumtweaks.config"), button ->
            {
                if (this.client != null)
                    this.client.setScreen(new ConfigScreen(this));
            }).dimensions(5, this.height - 25, 20, 20).build();

    @Unique
    private void positionButton(int height) {
        button.setX(5);
        button.setY(height - 25);
    }

    @SuppressWarnings("unused")
    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method= "init", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        button = addDrawableChild(button);
    }

    @Override
    public void resize(MinecraftClient client, int width, int height) {
        super.resize(client, width, height);
        positionButton(height);
    }

    @Override
    public void onDisplayed() {
        super.onDisplayed();
        Window window = TantalumTweaks.MC.getWindow();
        positionButton(window.getScaledHeight());
    }
}
