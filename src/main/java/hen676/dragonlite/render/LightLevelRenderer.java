package hen676.dragonlite.render;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.keybinds.DebugKeybinding;
import hen676.dragonlite.keybinds.LightLevelKeybinding;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/**
 * TODO:: Add light level adjustment option
 * TODO:: Add option to adjust square render size
 */
@Environment(EnvType.CLIENT)
public class LightLevelRenderer {
    private static boolean lastWasFrameDebug = false;
    private static long time = 0;
    private static int frames = 0;
    private static final float size = 0.25f;

    private static void render(MatrixStack matrices, Camera camera, ClientWorld world, @Nullable VertexConsumerProvider consumers) {
        if (!LightLevelKeybinding.toggle) return;

        // Return if invalid instance
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || consumers == null) return;
        if (client.player == null) return;

        ClientPlayerEntity player = client.player;
        VertexConsumer vertexConsumer = consumers.getBuffer(RenderLayer.getDebugQuads());

        // Setup matrices and render system
        Vec3d pos = camera.getPos();
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        BlockPos playerBlockPos = player.getBlockPos();
        float i = (float) (playerBlockPos.getY() - pos.getY()) + 0.1f;
        float j = (float) (playerBlockPos.getX() - pos.getX()) + 0.5f;
        float k = (float) (playerBlockPos.getZ() - pos.getZ()) + 0.5f;

        int range = 16;
        for (int x = -range; x < range; x++)
            for (int y = -range; y < 6; y++)
                for (int z = -range; z < range; z++) {
                    BlockPos blockPos = playerBlockPos.add(x, y, z);
                    if (!world.isTopSolid(blockPos.down(), player) || world.isTopSolid(blockPos, player)) continue;
                    if (world.getLightLevel(LightType.BLOCK, blockPos) == 0) {
                        vertexConsumer.vertex(matrix4f, j - size + x, i + y, k - size + z).color((float)Config.LIGHT_LEVEL_COLOR_RED, (float)Config.LIGHT_LEVEL_COLOR_GREEN, (float)Config.LIGHT_LEVEL_COLOR_BLUE, (float)Config.LIGHT_LEVEL_ALPHA).next();
                        vertexConsumer.vertex(matrix4f, j - size + x, i + y, k + size + z).color((float)Config.LIGHT_LEVEL_COLOR_RED, (float)Config.LIGHT_LEVEL_COLOR_GREEN, (float)Config.LIGHT_LEVEL_COLOR_BLUE, (float)Config.LIGHT_LEVEL_ALPHA).next();
                        vertexConsumer.vertex(matrix4f, j + size + x, i + y, k + size + z).color((float)Config.LIGHT_LEVEL_COLOR_RED, (float)Config.LIGHT_LEVEL_COLOR_GREEN, (float)Config.LIGHT_LEVEL_COLOR_BLUE, (float)Config.LIGHT_LEVEL_ALPHA).next();
                        vertexConsumer.vertex(matrix4f, j + size + x, i + y, k - size + z).color((float)Config.LIGHT_LEVEL_COLOR_RED, (float)Config.LIGHT_LEVEL_COLOR_GREEN, (float)Config.LIGHT_LEVEL_COLOR_BLUE, (float)Config.LIGHT_LEVEL_ALPHA).next();
                    }
                }
    }

    public static void render(WorldRenderContext worldRenderContext) {
        if (DebugKeybinding.toggle) {
            long startTime = System.nanoTime();
            render(worldRenderContext.matrixStack(), worldRenderContext.camera(), worldRenderContext.world(), worldRenderContext.consumers());
            long endTime = System.nanoTime();
            time += endTime - startTime;
            frames++;
            lastWasFrameDebug = true;
            return;
        }
        if (lastWasFrameDebug && frames != 0) {
            DragonLite.LOGGER.info(String.format("Average time for light level function during debug was: %,d", time / frames));
            time = 0;
            frames = 0;
            lastWasFrameDebug = false;
        }
        render(worldRenderContext.matrixStack(), worldRenderContext.camera(), worldRenderContext.world(), worldRenderContext.consumers());
    }
}
