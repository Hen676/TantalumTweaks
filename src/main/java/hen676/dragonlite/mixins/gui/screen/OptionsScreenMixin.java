package hen676.dragonlite.mixins.gui.screen;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.gui.screen.ConfigScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.OptionsScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.text.Text;
import net.minecraft.util.DyeColor;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
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
        this.addDrawableChild(ButtonWidget.builder(Text.translatable("options.dragonlite.config")
                .styled(style -> style.withColor(DyeColor.LIGHT_BLUE.getSignColor())), button -> {
            if (this.client != null)
                this.client.setScreen(new ConfigScreen(this));
        }).dimensions(5, this.height - 25, 20, 20).build());
        if (DragonLite.DEBUG) // TODO:: Not adjusting to window resize
            DragonLite.LOGGER.info(this.height);
    }


}
