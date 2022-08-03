package hen676.dragonlite.render;

import com.mojang.blaze3d.systems.RenderSystem;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.keybinds.LightLevelKeybinding;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;

@Environment(EnvType.CLIENT)
public class LightLevelRenderer {
    private static void render(MatrixStack matrices, Camera camera, ClientWorld world) {
        if (!LightLevelKeybinding.toggle || !Config.ENABLE_LIGHT_LEVEL) return;

        // Return if invalid instance
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        ClientPlayerEntity player = client.player;
        if (player == null) return;

        // Setup matrices and render system
        Vec3d pos = camera.getPos();
        matrices.push();
        var frustum = new Frustum(matrices.peek().getPositionMatrix(), RenderSystem.getProjectionMatrix());
        frustum.setPosition(pos.x, pos.y, pos.z);
        matrices.translate(-pos.x, -pos.y, -pos.z);
        String symbol = "■";
        float a = 8F - (client.textRenderer.getWidth(symbol) / 2F);
        float b = client.textRenderer.fontHeight / 2F;

        // loop blocks around player
        int range = 16;
        for (int x = -range; x < range; x++)
            for (int y = -range; y < 6; y++)
                for (int z = -range; z < range; z++) {
                    // check block is valid spawn
                    BlockPos blockPos = player.getBlockPos().add(x, y, z);
                    if (!world.isTopSolid(blockPos.down(), player) || world.isTopSolid(blockPos, player)) continue;
                    int blockLight = world.getLightLevel(LightType.BLOCK, blockPos);
                    if (blockLight < 1) {
                        // render light level number
                        matrices.push();
                        matrices.translate(blockPos.getX(), blockPos.getY() + 0.01D, blockPos.getZ());
                        matrices.multiply(new Quaternion(Vec3f.POSITIVE_X, 90, true));

                        float scale = 0.0625F;
                        matrices.scale(scale, scale, scale);
                        matrices.translate(a, b, 0);
                        client.textRenderer.draw(matrices, symbol, 0, 0, DyeColor.byId(Config.LIGHT_LEVEL_COLOR).getSignColor());
                        matrices.pop();
                    }
                }
        matrices.pop();
    }

    public static void render(WorldRenderContext worldRenderContext) {
        render(worldRenderContext.matrixStack(), worldRenderContext.camera(), worldRenderContext.world());
    }
}
