package hen676.dragonlite.mixins.gui.screen;

import hen676.dragonlite.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(value=EnvType.CLIENT)
@Mixin(CraftingScreen.class)
public abstract class CraftingScreenMixin {

    @Shadow
    @Final
    private RecipeBookWidget recipeBook;

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookWidget;initialize(IILnet/minecraft/client/MinecraftClient;ZLnet/minecraft/screen/AbstractRecipeScreenHandler;)V", shift = At.Shift.AFTER))
    private void craftingRecipeBook(CallbackInfo ci) {
        if(Config.ENABLE_CUSTOM_SPLIT_RECIPE_BOOK_FOR_CRAFTING) {
            if (Config.ON_INIT_OPEN_RECIPE_BOOK_FOR_CRAFTING != this.recipeBook.isOpen())
                this.recipeBook.toggleOpen();
        }
    }
}
