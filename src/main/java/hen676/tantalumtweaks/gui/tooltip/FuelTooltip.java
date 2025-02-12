package hen676.tantalumtweaks.gui.tooltip;

import hen676.tantalumtweaks.TantalumTweaks;
import hen676.tantalumtweaks.config.Config;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class FuelTooltip {
    public static void onItemTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> texts) {
        if (!Config.ENABLE_FUEL_TOOLTIP || TantalumTweaks.MC.world == null) return;
        int fuelTicks = TantalumTweaks.MC.world.getFuelRegistry().getFuelTicks(itemStack);
        if (fuelTicks == 0) return;
        double itemsSmelted = (double) (Math.round(((float) fuelTicks / 200) * 10))/10;
        MutableText text = Text.translatable("tooltip.tantalumtweaks.fuel", itemsSmelted, fuelTicks)
                .styled(style -> style.withColor(Formatting.DARK_GRAY));
        texts.add(1, text);
    }
}
