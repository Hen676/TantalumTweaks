package hen676.tantalumtweaks.keybinds;

import hen676.tantalumtweaks.entity.FreecamEntity;
import hen676.tantalumtweaks.util.PositionUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.input.Input;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.option.Perspective;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

/**
 * TODO:: Remove freecam entity model to not create shadow with shaders
 */
@Environment(EnvType.CLIENT)
public class FreecamKeybinding extends TweakKeybinding {
    private static KeyBinding keyBinding;
    private static boolean toggle = false;
    private static FreecamEntity freecamEntity;
    private static Perspective orignalPerspective;

    public static void init() {
        keyBinding = register("key.tantalumtweaks.freecam", GLFW.GLFW_KEY_F4);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (keyBinding.wasPressed())
                toggleFreecam(client);
        });
    }

    public static void toggleFreecam(MinecraftClient client) {
        toggle = !toggle;
        if(toggle)
            onEnableFreeCamera(client);
        else
            onDisableFreeCamera(client);
    }

    public static boolean isFreecam() {
        return toggle;
    }

    public static FreecamEntity getFreeCameraEntity() {
        return freecamEntity;
    }

    public static void onEnableFreeCamera(MinecraftClient client) {
        client.chunkCullingEnabled = false;
        client.gameRenderer.setRenderHand(false);
        if (client.currentScreen != null)
            client.currentScreen.close();
        orignalPerspective = client.options.getPerspective();
        // Create freecam entity
        freecamEntity = new FreecamEntity(-676, new PositionUtil(client.gameRenderer.getCamera()));
        freecamEntity.create();
        client.options.setPerspective(Perspective.FIRST_PERSON);
        client.setCameraEntity(freecamEntity);

        if (client.player != null)
            client.player.sendMessage(Text
                    .translatable("message.tantalumtweaks.freecam")
                    .styled(style -> style.withColor(TEXT_COLOR))
                    .append(" ")
                    .append(ON),true);
    }

    public static void onDisableFreeCamera(MinecraftClient client) {
        client.chunkCullingEnabled = true;
        client.gameRenderer.setRenderHand(true);
        client.options.setPerspective(orignalPerspective);
        client.setCameraEntity(client.player);
        freecamEntity.delete();
        freecamEntity.input = new Input();
        freecamEntity = null;

        if (client.player != null) {
            client.player.input = new KeyboardInput(client.options);
        }

        if (client.player != null)
            client.player.sendMessage(Text
                    .translatable("message.tantalumtweaks.freecam")
                    .styled(style -> style.withColor(TEXT_COLOR))
                    .append(" ")
                    .append(OFF), true);
    }
}
