package hen676.dragonlite.mixin;

import hen676.dragonlite.gui.screen.ConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(OptionsScreen.class)
public abstract class OptionsScreenMixin extends Screen {
    protected OptionsScreenMixin(Text title) {
        super(title);
    }

    @Inject(method= "init", at = @At("RETURN"))
    private void init(CallbackInfo ci) {
        this.addDrawableChild(new ButtonWidget(
                5,
                this.height - 25,
                50,
                20,
                Text.translatable("options.dragonlite.config"),
                button -> {
                    if (this.client != null)
                        this.client.setScreen(new ConfigScreen(this));
                }
        ));
    }
}
