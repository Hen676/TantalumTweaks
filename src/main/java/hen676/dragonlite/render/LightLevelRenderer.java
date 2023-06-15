package hen676.dragonlite.render;

import hen676.dragonlite.config.Config;
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

@Environment(EnvType.CLIENT)
public class LightLevelRenderer {
    private static void render(MatrixStack matrices, Camera camera, ClientWorld world, @Nullable VertexConsumerProvider consumers) {
        if (!LightLevelKeybinding.toggle || !Config.ENABLE_LIGHT_LEVEL) return;

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
        float size = 0.25f;

        DyeColor dyeColor = DyeColor.byId(Config.LIGHT_LEVEL_COLOR);
        float red = dyeColor.getColorComponents()[0];
        float green = dyeColor.getColorComponents()[1];
        float blue = dyeColor.getColorComponents()[2];

        int range = 16;
        for (int x = -range; x < range; x++)
            for (int y = -range; y < 6; y++)
                for (int z = -range; z < range; z++) {
                    BlockPos blockPos = playerBlockPos.add(x, y, z);
                    if (!world.isTopSolid(blockPos.down(), player) || world.isTopSolid(blockPos, player)) continue;
                    int blockLight = world.getLightLevel(LightType.BLOCK, blockPos);
                    if (blockLight < 1) {
                        vertexConsumer.vertex(matrix4f, j - size + x, i + y, k - size + z).color(red, green, blue, (float)Config.LIGHT_LEVEL_ALPHA).next();
                        vertexConsumer.vertex(matrix4f, j - size + x, i + y, k + size + z).color(red, green, blue, (float)Config.LIGHT_LEVEL_ALPHA).next();
                        vertexConsumer.vertex(matrix4f, j + size + x, i + y, k + size + z).color(red, green, blue, (float)Config.LIGHT_LEVEL_ALPHA).next();
                        vertexConsumer.vertex(matrix4f, j + size + x, i + y, k - size + z).color(red, green, blue, (float)Config.LIGHT_LEVEL_ALPHA).next();
                    }
                }
    }

    public static void render(WorldRenderContext worldRenderContext) {
        render(worldRenderContext.matrixStack(), worldRenderContext.camera(), worldRenderContext.world(), worldRenderContext.consumers());
    }
}
