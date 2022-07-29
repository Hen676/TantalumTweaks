package hen676.dragonlite.render;

import com.mojang.blaze3d.systems.RenderSystem;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.keybinds.LightLevelKeybinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.*;
import net.minecraft.world.LightType;

public class LightLevelRenderer {
    public static void render(MatrixStack matrices, Camera camera, GameRenderer gameRenderer, Matrix4f matrix) {
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
        //matrices.translate(-pos.x, -pos.y, -pos.z);
        //RenderSystem.enablePolygonOffset();
        //RenderSystem.polygonOffset(1, 2);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();

        RenderSystem.disableTexture();
        RenderSystem.disableBlend();
        RenderSystem.lineWidth(1.0F);
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);

        bufferBuilder.vertex(1, -55, 0).color(1.0F, 0.0F, 0.0F, 0.0F).next();
        bufferBuilder.vertex(1, -55, 0).color(1.0F, 0.0F, 0.0F, 0.5F).next();
        bufferBuilder.vertex(0, -55, 1).color(1.0F, 0.0F, 0.0F, 0.5F).next();
        bufferBuilder.vertex(0, -55, 1).color(1.0F, 0.0F, 0.0F, 0.0F).next();
        tessellator.draw();

        matrices.pop();

        RenderSystem.enableBlend();
        RenderSystem.enableTexture();
        /*
        // loop blocks around player
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
                        matrices.translate(8F - (client.textRenderer.getWidth("■") / 2F),client.textRenderer.fontHeight / 2F, 0);
                        client.textRenderer.draw(matrices, "■", 0, 0, DyeColor.byId(Config.LIGHT_LEVEL_COLOR).getSignColor());
                        matrices.pop();
                    }
                }
        matrices.pop();*/
    }
}
