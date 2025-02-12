package hen676.tantalumtweaks.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.Map;

public class EntityListCommand {

    public static void register(CommandDispatcher<FabricClientCommandSource> dispatcher, CommandRegistryAccess ignoredCommandRegistryAccess) {
        dispatcher.register(ClientCommandManager.literal("list_entities")
                .executes(context -> listEntitiesAroundPlayer(context.getSource(), 10)) // Default case with area_size = 10
                .then(ClientCommandManager.argument("area_size", IntegerArgumentType.integer(0, 128))
                        .executes(context -> listEntitiesAroundPlayer(context.getSource(), IntegerArgumentType.getInteger(context, "area_size")))
                )
        );
    }

    private static int listEntitiesAroundPlayer(FabricClientCommandSource source, int areaSize) {
        ClientPlayerEntity player = source.getClient().player;
        ClientWorld world = source.getClient().world;
        if (world == null || player == null) return 0;

        BlockPos playerPos = player.getBlockPos();
        Map<Text, Integer> entityNames = new HashMap<>();

        world.getEntities().forEach((entity -> {
            if (entity.isPlayer()) return;
            if (entity.getBlockPos().isWithinDistance(playerPos,areaSize)) {
                Text name = entity.getName();
                if (entityNames.containsKey(name))
                    entityNames.put(name, entityNames.get(name) + 1);
                else
                    entityNames.put(name, 1);
            }
        }));

        if (entityNames.isEmpty()) {
            source.sendFeedback(Text.literal("No entities %s distance from player!".formatted(areaSize))
                    .styled(style -> style.withColor(Formatting.RED)));
            return 1;
        }

        source.sendFeedback(Text.literal("List of entities from %s distance from player".formatted(areaSize))
                .styled(style -> style.withColor(Formatting.GREEN)));
        entityNames.forEach((text, amount) -> source.sendFeedback(text.copy()
                .append(" - ")
                .append(String.valueOf(amount)).styled(style -> style.withColor(Formatting.GRAY))));
        return 1;
    }
}
