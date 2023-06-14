package hen676.dragonlite.mixin.renderer;

import hen676.dragonlite.config.Config;
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
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class LivingEntityRenderMixin<T extends LivingEntity, M extends EntityModel<T>> extends EntityRenderer<T> implements FeatureRendererContext<T, M> {
    protected LivingEntityRenderMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "render*", at=@At(value = "RETURN"))
    private void render(T livingEntity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, CallbackInfo ci) {
        double d = this.dispatcher.getSquaredDistanceToCamera(livingEntity);
        if (!(d > 128.0D) && HealthBarKeybinding.toggle && Config.ENABLE_MOB_HEALTH) {
            matrixStack.push();
            boolean bl = !livingEntity.isSneaky();

            float y = livingEntity.getHeight() + 0.5F;
            matrixStack.translate(0.0D, y, 0.0D);
            matrixStack.multiply(this.dispatcher.getRotation());
            matrixStack.scale(-0.025F, -0.025F, 0.025F);
            int healthPercentage = Math.round((livingEntity.getHealth() / livingEntity.getMaxHealth()) * 100);

            String text = healthPercentage + "%";
            int color = 553648127;
            if (healthPercentage > 50) {
                color = 65280;
            }
            if (healthPercentage <= 50 && healthPercentage > 25) {
                color = 16776960;
            }
            if (healthPercentage <= 25 && healthPercentage > 10) {
                color = 16751360;
            }
            if (healthPercentage <= 10) {
                color = 16711680;
            }

            TextRenderer textRenderer = this.getTextRenderer();
            float x = (float)(-textRenderer.getWidth(text) / 2);
            int i = hasLabel(livingEntity) ? -10: 0;

            float g = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25F);
            int j = (int)(g * 255.0F) << 24;
            textRenderer.draw(text, x, (float)i, 553648127, false, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, j, light);
            if (bl) {
                textRenderer.draw(text, x, (float)i, color, false, matrixStack.peek().getPositionMatrix(), vertexConsumerProvider, TextRenderer.TextLayerType.SEE_THROUGH, 0, light);
            }
            matrixStack.pop();
        }
    }
}
