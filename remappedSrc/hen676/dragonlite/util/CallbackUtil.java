package hen676.dragonlite.util;

import hen676.dragonlite.keybinds.FreecamKeybinding;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class CallbackUtil {

    public static void FreecamCancel(CallbackInfo ci) {
        if (FreecamKeybinding.isFreecam())
            ci.cancel();
    }

    public static void FreecamCancelAndToggle(CallbackInfo ci, boolean toggle) {
        if (FreecamKeybinding.isFreecam() && toggle)
            ci.cancel();
    }
}
