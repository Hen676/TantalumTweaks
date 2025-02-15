package hen676.tantalumtweaks.util;

import hen676.tantalumtweaks.TantalumTweaks;
import hen676.tantalumtweaks.keybinds.FreecamKeybinding;
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
        return FreecamKeybinding.isFreecam() && entity.equals(TantalumTweaks.MC.player);
    }
}
