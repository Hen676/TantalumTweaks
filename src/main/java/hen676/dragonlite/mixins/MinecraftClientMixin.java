package hen676.dragonlite.mixins;

import hen676.dragonlite.keybinds.FreecamKeybinding;
import hen676.dragonlite.util.CallbackUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static hen676.dragonlite.DragonLite.MC;

@Environment(EnvType.CLIENT)
@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {

        @Inject(method = "tick", at = @At("HEAD"))
        private void preventPlayerControlOnFreecam(CallbackInfo ci) {
                if (FreecamKeybinding.isFreecam()) {
                        if (MC.player != null && MC.player.input instanceof KeyboardInput) {
                                Input input = new Input();
                                input.sneaking = MC.player.input.sneaking;
                                MC.player.input = input;
                        }
                        MC.gameRenderer.setRenderHand(false);
                }
        }

        @Inject(method = "doAttack", at = @At("HEAD"), cancellable = true)
        private void preventAttackOnFreecam(CallbackInfoReturnable<Boolean> cir) {
                CallbackUtil.FreecamCancel(cir);
        }

        @Inject(method = "doItemPick", at = @At("HEAD"), cancellable = true)
        private void preventItemPickOnFreecam(CallbackInfo ci) {
                CallbackUtil.FreecamCancel(ci);
        }

        @Inject(method = "handleBlockBreaking", at = @At("HEAD"), cancellable = true)
        private void preventBreakBlocksOnFreecam(CallbackInfo ci) {
                CallbackUtil.FreecamCancel(ci);
        }

        @Inject(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z", ordinal = 2), cancellable = true)
        private void preventHotbarKeysOnFreecam(CallbackInfo ci) {
                CallbackUtil.FreecamCancel(ci);
        }

        @Inject(method = "handleInputEvents", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;wasPressed()Z", ordinal = 0), cancellable = true)
        private void preventPerspectiveChangeOnFreecam(CallbackInfo ci) {
                CallbackUtil.FreecamCancel(ci);
        }
}
