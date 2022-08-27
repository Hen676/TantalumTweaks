package hen676.dragonlite.util;

import hen676.dragonlite.config.Config;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;

public class AddParticles {
    public static void addFurnaceParticles(BlockState state, World world, BlockPos pos, Random random) {
        if (state.get(AbstractFurnaceBlock.LIT) && random.nextInt(2) == 0 && Config.ENABLE_SMOKEY_FURNACE) {
            world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                    (double)pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1),
                    (double)pos.getY() + 1.0D,
                    (double)pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double)(random.nextBoolean() ? 1 : -1),
                    0.0D,
                    0.01D,
                    0.0D);
        }
    }
}
