package hen676.tantalumtweaks.gui.tooltip;

import hen676.tantalumtweaks.config.Config;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class FoodTooltip {
    public static void onItemTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> texts) {
        if (!Config.ENABLE_FOOD_TOOLTIP) return;
        FoodComponent foodComponent = itemStack.get(DataComponentTypes.FOOD);
        if (foodComponent == null) return;
        double saturation = (double) (Math.round(foodComponent.saturation() * 10))/10;
        MutableText text = Text.translatable("tooltip.tantalumtweaks.food", foodComponent.nutrition(), saturation)
                .styled(style -> style.withColor(Formatting.DARK_GRAY));
        texts.add(1, text);
    }
}
