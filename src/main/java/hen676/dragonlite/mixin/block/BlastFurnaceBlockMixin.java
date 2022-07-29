package hen676.dragonlite.mixin.block;

import hen676.dragonlite.util.AddParticles;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlastFurnaceBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlastFurnaceBlock.class)
@Environment(EnvType.CLIENT)
public abstract class BlastFurnaceBlockMixin {
    @Inject(method = "randomDisplayTick", at=@At(value = "RETURN"))
    private void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random, CallbackInfo ci) {
        AddParticles.addFurnaceParticles(state, world, pos, random);
    }
}
