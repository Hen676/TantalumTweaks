package hen676.dragonlite.entity;

import com.mojang.authlib.GameProfile;
import hen676.dragonlite.DragonLite;
import hen676.dragonlite.config.Config;
import hen676.dragonlite.util.PositionUtil;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientConnectionState;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.Packet;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class FreecamEntity extends ClientPlayerEntity {

    private static final ClientPlayNetworkHandler NETWORK_HANDLER = new ClientPlayNetworkHandler(
            DragonLite.MC,
            Objects.requireNonNull(DragonLite.MC.getNetworkHandler()).getConnection(),
            new ClientConnectionState(
                    new GameProfile(UUID.randomUUID(), "Freecam"),
                    DragonLite.MC.getTelemetryManager().createWorldSession(false, null, null),
                    DragonLite.MC.getNetworkHandler().getRegistryManager(),
                    DragonLite.MC.getNetworkHandler().getEnabledFeatures(),
                    DragonLite.MC.getNetworkHandler().getBrand(),
                    DragonLite.MC.getNetworkHandler().getServerInfo(),
                    DragonLite.MC.currentScreen,
                    new HashMap<>(), //TODO:: Check this is fine (serverCookies)
                    DragonLite.MC.inGameHud.getChatHud().toChatState(),
                    false,
                    Map.of(), //TODO:: Check this is fine (customReportDetails)
                    DragonLite.MC.getNetworkHandler().getServerLinks()
            )) {

        @Override
        public void sendPacket(Packet<?> packet) {
        }
    };

    public FreecamEntity(int id, PositionUtil position) {
        super(DragonLite.MC,
                Objects.requireNonNull(DragonLite.MC.world),
                NETWORK_HANDLER,
                Objects.requireNonNull(DragonLite.MC.player).getStatHandler(),
                DragonLite.MC.player.getRecipeBook(),
                false,
                false);
        setId(id);
        applyPosition(position);
        getAbilities().flying = true;
        input = new KeyboardInput(DragonLite.MC.options);
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
