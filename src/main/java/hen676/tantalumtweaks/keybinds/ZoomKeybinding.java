package hen676.tantalumtweaks.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ZoomKeybinding extends TweakKeybinding {
    private static KeyBinding keyBinding;
    private static boolean currentlyZoomed = false;
    private static boolean originalSettingSmoothCamera = false;
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void init() {
        keyBinding = register("key.tantalumtweaks.zoom", GLFW.GLFW_KEY_V);
    }

    public static boolean isZooming() {
        return keyBinding.isPressed();
    }

    public static void manageSmoothCamera() {
        if(zoomStarting()) {
            originalSettingSmoothCamera = mc.options.smoothCameraEnabled;
            currentlyZoomed = true;
            mc.options.smoothCameraEnabled = true;
        }
        if(zoomStopping()) {
            currentlyZoomed = false;
            mc.options.smoothCameraEnabled = originalSettingSmoothCamera;
        }
    }

    private static boolean zoomStarting() {
        return isZooming() && !currentlyZoomed;
    }

    private static boolean zoomStopping() {
        return !isZooming() && currentlyZoomed;
    }
}
