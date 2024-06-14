package hen676.dragonlite.render;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.keybinds.DebugKeybinding;
import hen676.dragonlite.keybinds.LightLevelKeybinding;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

/**
 * TODO:: Add light level adjustment option
 */
@Environment(EnvType.CLIENT)
public class LightLevelRenderer {
    private static boolean lastWasFrameDebug = false;
    private static long time = 0;
    private static int frames = 0;

    private static void render(MatrixStack matrices, Camera camera, ClientWorld world, @Nullable VertexConsumerProvider consumers) {
        if (!LightLevelKeybinding.isLightLevel()) return;

        // Return if invalid instance
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null || consumers == null) return;
        if (client.player == null) return;

        Entity entity = camera.getFocusedEntity();
        VertexConsumer vertexConsumer = consumers.getBuffer(RenderLayer.getDebugQuads());

        // Setup matrices and render system
        Vec3d pos = camera.getPos();
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        BlockPos playerBlockPos = entity.getBlockPos();

        float i = (float) (playerBlockPos.getY() - pos.getY()) + 0.1f;
        float j = (float) (playerBlockPos.getX() - pos.getX()) + 0.5f;
        float k = (float) (playerBlockPos.getZ() - pos.getZ()) + 0.5f;

        matrices.push(); //TODO:: Test if this is needed
        int range = 16;
        for (int x = -range; x < range; x++)
            for (int y = -range; y < 6; y++)
                for (int z = -range; z < range; z++) {
                    BlockPos blockPos = playerBlockPos.add(x, y, z);
                    if (!world.getWorldBorder().contains(blockPos)) continue;

                    if (!world.isTopSolid(blockPos.down(), entity) || world.isTopSolid(blockPos, entity)) continue;
                    if (!world.getFluidState(blockPos).isEmpty()) continue;

                    BlockState blockState = world.getBlockState(blockPos);
                    BlockState blockStateDown = world.getBlockState(blockPos.down());
                    if (blockState.emitsRedstonePower() ||
                            blockState.isIn(BlockTags.PREVENT_MOB_SPAWNING_INSIDE) ||
                            blockStateDown.isIn(BlockTags.LEAVES) ||
                            blockStateDown.isIn(BlockTags.IMPERMEABLE)) continue;

                    if (world.getLightLevel(LightType.BLOCK, blockPos) == 0) {
                        float size = (float)Config.LIGHT_LEVEL_SQUARE_SIZE;

                        vertexHelper(matrix4f, vertexConsumer,j - size + x, i + y, k - size + z);
                        vertexHelper(matrix4f, vertexConsumer,j - size + x, i + y, k + size + z);
                        vertexHelper(matrix4f, vertexConsumer,j + size + x, i + y, k + size + z);
                        vertexHelper(matrix4f, vertexConsumer,j + size + x, i + y, k - size + z);
                    }
                }
        matrices.pop();
    }

    private static void vertexHelper(Matrix4f matrix4f, VertexConsumer vertexConsumer, float x, float y, float z) {
        vertexConsumer.vertex(matrix4f, x, y, z)
                .color((float)Config.LIGHT_LEVEL_COLOR_RED,
                        (float)Config.LIGHT_LEVEL_COLOR_GREEN,
                        (float)Config.LIGHT_LEVEL_COLOR_BLUE,
                        (float)Config.LIGHT_LEVEL_ALPHA);
    }

    public static void render(WorldRenderContext worldRenderContext) {
        if (DebugKeybinding.isDebug()) {
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
