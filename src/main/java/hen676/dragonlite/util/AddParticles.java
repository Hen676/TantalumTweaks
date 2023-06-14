package hen676.dragonlite.util;

import hen676.dragonlite.config.Config;
import net.minecraft.block.AbstractFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public class AddParticles {
    public static void addFurnaceParticles(BlockState state, World world, BlockPos pos, Random random, boolean outputParticlesOnSide) {
        if (state.get(AbstractFurnaceBlock.LIT) && random.nextInt(2) == 0 && Config.ENABLE_SMOKEY_FURNACE) {
            if(outputParticlesOnSide) {
                Direction direction = state.get(Properties.HORIZONTAL_FACING);
                double x = 0, z = 0;
                switch (direction) {
                    case EAST -> {
                        x = pos.getX() + 1.1D;
                        z = pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1);
                    }
                    case WEST -> {
                        x = pos.getX() - 0.1D;
                        z = pos.getZ() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1);
                    }
                    case SOUTH -> {
                        x = pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1);
                        z = pos.getZ() + 1.1D;
                    }
                    default -> {
                        x = pos.getX() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1);
                        z = pos.getZ() - 0.1D;
                    }
                }
                world.addParticle(ParticleTypes.CAMPFIRE_COSY_SMOKE,
                        x,
                        (double) pos.getY() + 0.5D + random.nextDouble() / 3.0D * (double) (random.nextBoolean() ? 1 : -1),
                        z,
                        0.0D,
                        0.01D,
                        0.0D);
            } else {
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
}
