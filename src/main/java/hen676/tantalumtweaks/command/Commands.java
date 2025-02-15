package hen676.tantalumtweaks.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;

public class Commands {
    public static void init(CommandDispatcher<FabricClientCommandSource> fabricClientCommandSourceCommandDispatcher, CommandRegistryAccess ignoredCommandRegistryAccess) {
        EntityListCommand.register(fabricClientCommandSourceCommandDispatcher, ignoredCommandRegistryAccess);
    }
}
