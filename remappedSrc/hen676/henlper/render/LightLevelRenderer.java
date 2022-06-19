package hen676.henlper.render;

import hen676.henlper.config.Config;
import hen676.henlper.keybinds.LightLevelKeybinding;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;

public class LightLevelRenderer {
    public static void render(MatrixStack matrices, Camera camera) {
        if (!LightLevelKeybinding.toggle || !Config.ENABLE_LIGHT_LEVEL) return;

        // return if invalid instance
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return;
        ClientPlayerEntity player = client.player;
        ClientWorld world = client.world;
        if (world == null || player == null) return;

        // setup matrices and render system
        Vec3d pos = camera.getPos();
        matrices.push();
        matrices.translate(-pos.x, -pos.y, -pos.z);
        RenderSystem.enablePolygonOffset();
        RenderSystem.polygonOffset(1, 2);

        // loop blocks around player
        // TODO:: Replace number with square/cross
        int range = 16;
        for (int x = -range; x < range; x++)
            for (int y = -range; y < range; y++)
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
                        String lightText = String.valueOf(blockLight);
                        matrices.translate(8F - (client.textRenderer.getWidth(lightText) / 2F),client.textRenderer.fontHeight / 2F, 0);
                        client.textRenderer.drawWithShadow(matrices,lightText, 0, 0, DyeColor.byId(Config.LIGHT_LEVEL_COLOR).getSignColor());
                        matrices.pop();
                    }
                }
        matrices.pop();
    }
}
