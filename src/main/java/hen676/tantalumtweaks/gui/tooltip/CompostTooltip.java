package hen676.tantalumtweaks.gui.tooltip;

import hen676.tantalumtweaks.config.Config;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class CompostTooltip {
    public static void onItemTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> texts) {
        if (!Config.ENABLE_COMPOST_TOOLTIP) return;
        if (!ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.containsKey(itemStack.getItem())) return;

        float compostChance = ComposterBlock.ITEM_TO_LEVEL_INCREASE_CHANCE.getFloat(itemStack.getItem());
        MutableText text = Text.translatable("tooltip.tantalumtweaks.compost", Math.round(compostChance*100))
                .styled(style -> style.withColor(Formatting.DARK_GRAY));
        texts.add(1, text);
    }
}
