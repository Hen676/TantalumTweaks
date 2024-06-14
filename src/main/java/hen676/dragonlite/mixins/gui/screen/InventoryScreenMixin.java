package hen676.dragonlite.mixins.gui.screen;

import hen676.dragonlite.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(InventoryScreen.class)
public abstract class InventoryScreenMixin {

    @Shadow
    @Final
    private RecipeBookWidget recipeBook;

    @Inject(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookWidget;initialize(IILnet/minecraft/client/MinecraftClient;ZLnet/minecraft/screen/AbstractRecipeScreenHandler;)V", shift = At.Shift.AFTER))
    private void inventoryRecipeBook(CallbackInfo ci) {
        if(Config.ENABLE_CUSTOM_SPLIT_RECIPE_BOOK_FOR_CRAFTING) {
            if (Config.ON_INIT_OPEN_RECIPE_BOOK_FOR_INVENTORY != this.recipeBook.isOpen())
                this.recipeBook.toggleOpen();
        }
    }
}
