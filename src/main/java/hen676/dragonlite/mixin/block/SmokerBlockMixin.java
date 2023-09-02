package hen676.dragonlite.mixin.block;

import hen676.dragonlite.config.Config;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.SmokerBlock;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SmokerBlock.class)
@Environment(EnvType.CLIENT)
public abstract class SmokerBlockMixin {
    @Inject(method = "randomDisplayTick", at=@At(value = "RETURN"))
    private void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        addFurnaceParticles(state, world, pos, random);
    }

    public void addFurnaceParticles(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(AbstractFurnaceBlock.LIT) && random.nextInt(2) == 0 && Config.ENABLE_SMOKEY_FURNACE) {
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    (double) pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1),
                    (double) pos.getY() + 1.0D,
                    (double) pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1),
                    0.0D,
                    0.01D,
                    0.0D);
        }
    }
}
