package hen676.dragonlite.mixins.render;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.keybinds.FreecamKeybinding;
import hen676.dragonlite.keybinds.HealthBarKeybinding;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(WorldRenderer.class)
public abstract class WorldRendererMixin {
    @Shadow @Final private MinecraftClient client;
    @Shadow @Final private EntityRenderDispatcher entityRenderDispatcher;

    @Inject(method = "renderEntities", at = @At(value = "HEAD"))
    private void renderPlayerOnFreecam(MatrixStack matrices, VertexConsumerProvider.Immediate immediate, Camera camera, RenderTickCounter tickCounter, List<Entity> entities, CallbackInfo ci) {
        if (FreecamKeybinding.isFreecam()) {
            entities.add(client.player);
        }
    }

    @Inject(method = "renderEntity", at = @At(value = "RETURN"))
    private void renderEntity(Entity entity, double cameraX, double cameraY, double cameraZ, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, CallbackInfo ci) {
        if (!HealthBarKeybinding.isHealthBar()) return;

        if (entity instanceof LivingEntity livingEntity) {
            double d = this.entityRenderDispatcher.getSquaredDistanceToCamera(livingEntity);
            if (!(d <= 128.0D)) return;
            Vec3d vec3d = livingEntity.getPos();
            if (DragonLite.MC.currentScreen != null) return;

            boolean bl = !livingEntity.isSneaking();
            matrices.push();
            matrices.translate(vec3d.x - cameraX, vec3d.y + livingEntity.getHeight() + 0.5 - cameraY, vec3d.z - cameraZ);

            matrices.multiply(this.entityRenderDispatcher.getRotation());

            matrices.scale(0.025F, -0.025F, 0.025F);
            Matrix4f matrix4f = matrices.peek().getPositionMatrix();

            int healthPercentage = Math.round((livingEntity.getHealth() / livingEntity.getMaxHealth()) * 100);
            String text = healthPercentage + "%";
            int color = getColor(healthPercentage);
            TextRenderer textRenderer = client.textRenderer;
            float x = (float) (-textRenderer.getWidth(text) / 2);
            int j = (int) (MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F) * 255.0F) << 24;

            textRenderer.draw(text, x, (float) -10, 0x20FFFFFF, false, matrix4f, vertexConsumers, bl ? TextRenderer.TextLayerType.SEE_THROUGH : TextRenderer.TextLayerType.NORMAL, j, this.entityRenderDispatcher.getLight(entity, tickDelta));
            if (bl)
                textRenderer.draw(text, x, (float) -10, color, false, matrix4f, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, this.entityRenderDispatcher.getLight(entity, tickDelta));
            matrices.pop();
        }
    }

    @Unique
    private static int getColor(int healthPercentage) {
        if (healthPercentage > 50)
            return DyeColor.GREEN.getSignColor();
        if (healthPercentage > 25)
            return DyeColor.YELLOW.getSignColor();
        if (healthPercentage > 10)
            return DyeColor.ORANGE.getSignColor();
        return DyeColor.RED.getSignColor();
    }
}
