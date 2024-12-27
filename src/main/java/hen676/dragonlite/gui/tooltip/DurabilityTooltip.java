package hen676.dragonlite.gui.tooltip;

import hen676.dragonlite.DragonLite;
import hen676.dragonlite.config.Config;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class DurabilityTooltip {

    public static void onItemTooltip(ItemStack itemStack, Item.TooltipContext ignoredTooltipContext, TooltipType tooltipType, List<Text> texts) {
        if (!Config.ENABLE_DURABILITY_TOOLTIP) return;
        if (!itemStack.isDamageable() || tooltipType.isAdvanced()) return;

        int maxDurability = itemStack.getMaxDamage();
        int durability = maxDurability - itemStack.getDamage();

        assert DragonLite.MC.world != null;
        RegistryEntry.Reference<Enchantment> key = DragonLite.MC.world.getRegistryManager()
                .getOrThrow(RegistryKeys.ENCHANTMENT).getOrThrow(Enchantments.UNBREAKING);

        MutableText text = Text.translatable("tooltip.dragonlite.durability", durability, maxDurability)
                .styled(style -> style.withColor(Formatting.DARK_GRAY));

        int unbreakingLevel = EnchantmentHelper.getLevel(key, itemStack);

        if (itemStack.isOf(Items.ELYTRA)) {
            text.append(elytraTooltip(durability * (unbreakingLevel + 1)));
        } else if (unbreakingLevel != 0) {
            int effectiveDurability = durability * (unbreakingLevel + 1);
            text.append(Text.translatable("tooltip.dragonlite.durability_with_unbreaking", effectiveDurability)
                    .styled(style -> style.withColor(Formatting.AQUA)));
        }
        texts.add(1,text);
    }

    private static Text elytraTooltip(int effectiveDurability) {
        int minutes = effectiveDurability/60;
        int seconds = effectiveDurability%60;

        if (minutes == 0) {
            return Text.translatable("tooltip.dragonlite.durability_elytra_s", seconds)
                    .styled(style -> style.withColor(Formatting.LIGHT_PURPLE));
        }
        return Text.translatable("tooltip.dragonlite.durability_elytra_m_and_s", minutes, seconds)
                .styled(style -> style.withColor(Formatting.LIGHT_PURPLE));
    }
}
