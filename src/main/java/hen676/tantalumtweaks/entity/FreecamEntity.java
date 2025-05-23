package hen676.tantalumtweaks.entity;

import com.mojang.authlib.GameProfile;
import hen676.tantalumtweaks.TantalumTweaks;
import hen676.tantalumtweaks.config.Config;
import hen676.tantalumtweaks.util.PositionUtil;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;
import net.minecraft.server.ServerLinks;

import java.util.*;

public class FreecamEntity extends ClientPlayerEntity {

    private static final ClientPlayNetworkHandler NETWORK_HANDLER = new ClientPlayNetworkHandler(
            TantalumTweaks.MC,
            Objects.requireNonNull(TantalumTweaks.MC.getNetworkHandler()).getConnection(),
            new ClientConnectionState(
                    new GameProfile(UUID.randomUUID(), "Freecam"),
                    TantalumTweaks.MC.getTelemetryManager().createWorldSession(false, null, null),
                    TantalumTweaks.MC.getNetworkHandler().getRegistryManager(),
                    TantalumTweaks.MC.getNetworkHandler().getEnabledFeatures(),
                    TantalumTweaks.MC.getNetworkHandler().getBrand(),
                    TantalumTweaks.MC.getCurrentServerEntry(),
                    TantalumTweaks.MC.currentScreen,
                    Collections.emptyMap(),
                    TantalumTweaks.MC.inGameHud.getChatHud().toChatState(),
                    Collections.emptyMap(),
                    ServerLinks.EMPTY
            )) {

        @Override
        public void sendPacket(Packet<?> packet) {
        }
    };

    public FreecamEntity(int id, PositionUtil position) {
        super(TantalumTweaks.MC,
                Objects.requireNonNull(TantalumTweaks.MC.world),
                NETWORK_HANDLER,
                Objects.requireNonNull(TantalumTweaks.MC.player).getStatHandler(),
                TantalumTweaks.MC.player.getRecipeBook(),
                false,
                false);
        setId(id);
        applyPosition(position);
        getAbilities().flying = true;
        input = new KeyboardInput(TantalumTweaks.MC.options);
        setLoaded(true); // Delay of 60 ticks on tick functions if not called
    }

    public void applyPosition(PositionUtil position) {
        refreshPositionAndAngles(position.x, position.y, position.z, position.yaw, position.pitch);
        renderPitch = getPitch();
        renderYaw = getYaw();
        lastRenderPitch = renderPitch;
        lastRenderYaw = renderYaw;
    }

    // Ignore ladders and vines
    @Override
    public boolean isClimbing() {
        return false;
    }

    // Ignore water
    @Override
    public boolean isTouchingWater() {
        return false;
    }

    // Ignore solid entities
    @Override
    public boolean collidesWith(Entity other) {
        return false;
    }

    // Ignore pistons
    @Override
    public PistonBehavior getPistonBehavior() {
        return PistonBehavior.IGNORE;
    }

    @Override
    public void tickMovement() {
        getAbilities().setFlySpeed((float) (0.1f * Config.FREECAM_FLIGHT_SPEED));
        super.tickMovement();
        getAbilities().flying = true;
        setOnGround(false);
        if (TantalumTweaks.DEBUG)
            TantalumTweaks.LOGGER.info("X:{} Y:{} Z:{}", getX(), getY(), getZ());
    }

    // Remove freecam entity to the world
    public void delete() {
        if (clientWorld != null && clientWorld.getEntityById(getId()) != null)
            clientWorld.removeEntity(getId(), RemovalReason.DISCARDED);
    }

    // Add freecam entity to the world
    public void create() {
        if (clientWorld != null)
            clientWorld.addEntity(this);
    }
}
