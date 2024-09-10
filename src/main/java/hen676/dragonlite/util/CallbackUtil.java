package hen676.dragonlite.util;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.keybinds.FreecamKeybinding;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CallbackUtil {
    public static void CancelIfFreecamOn(CallbackInfo ci) {
        if (FreecamKeybinding.isFreecam())
            ci.cancel();
    }

    public static void CancelIfPlayerIsEntityAndFreecamOn(CallbackInfo ci, Entity entity) {
        if (IsFreecamOnAndPlayerEntity(entity)) ci.cancel();
    }

    public static boolean IsFreecamOnAndPlayerEntity(Entity entity) {
        return FreecamKeybinding.isFreecam() && entity.equals(DragonLite.MC.player);
    }
}
