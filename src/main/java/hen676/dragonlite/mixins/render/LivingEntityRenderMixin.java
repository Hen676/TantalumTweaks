package hen676.dragonlite.mixins.render;

import hen676.dragonlite.keybinds.HealthBarKeybinding;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EntityAttachmentType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.DyeColor;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRenderMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    protected LivingEntityRenderMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    // TODO:: fix
    @Inject(method = "render*", at=@At(value = "RETURN"))
    private void render(T livingEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci) {
        if (!HealthBarKeybinding.isHealthBar()) return;
        double d = this.dispatcher.getSquaredDistanceToCamera(livingEntity);
        if (!(d <= 128.0D)) {
            return;
        }
        Vec3d vec3d = livingEntity.getAttachments().getPointNullable(EntityAttachmentType.NAME_TAG, 0, livingEntity.getYaw(tickDelta));
        if (vec3d == null) {
            return;
        }

        matrixStack.push();
        boolean bl = !livingEntity.isSneaky();
        //float y = livingEntity.getHeight() + 0.5F;
        //matrixStack.translate(0.0D, y, 0.0D);
        matrixStack.translate(vec3d.x, vec3d.y + 0.5, vec3d.z);
        matrixStack.multiply(this.dispatcher.getRotation());
        matrixStack.scale(-0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        int healthPercentage = Math.round((livingEntity.getHealth() / livingEntity.getMaxHealth()) * 100);
        String text = healthPercentage + "%";
        int color = getColor(healthPercentage);
        TextRenderer textRenderer = this.getTextRenderer();
        float x = (float)(-textRenderer.getWidth(text) / 2);
        int i = hasLabel(livingEntity) ? -10: 0;
        float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
        int j = (int)(g * 255.0F) << 24;
        textRenderer.draw(text, x, (float)i, 553648127, false, matrix4f, vertexConsumerProvider, bl ? TextRenderer.TextLayerType.SEE_THROUGH : TextRenderer.TextLayerType.NORMAL, j, light);
        if (bl) {
            textRenderer.draw(text, x, (float)i, color, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, light);
        }
        matrixStack.pop();
    }

    @Unique
    private static int getColor(int healthPercentage) {
        int color = DyeColor.WHITE.getSignColor();
        if (healthPercentage > 50) {
            color = DyeColor.GREEN.getSignColor();
        }
        if (healthPercentage <= 50 && healthPercentage > 25) {
            color = DyeColor.YELLOW.getSignColor();
        }
        if (healthPercentage <= 25 && healthPercentage > 10) {
            color = DyeColor.ORANGE.getSignColor();
        }
        if (healthPercentage <= 10) {
            color = DyeColor.RED.getSignColor();
        }
        return color;
    }
}
