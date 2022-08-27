package hen676.dragonlite.keybinds;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ZoomKeybinding {
    private static KeyBinding keyBindingZoom;
    private static boolean currentlyZoomed = false;
    private static boolean originalSettingSmoothCamera = false;
    private static final MinecraftClient mc = MinecraftClient.getInstance();

    public static void init() {
        keyBindingZoom = new KeyBinding(
                "key.henlper.zoom",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                "category.henlper.main");

        KeyBindingHelper.registerKeyBinding(keyBindingZoom);
    }

    public static boolean isZooming() {
        return keyBindingZoom.isPressed();
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
