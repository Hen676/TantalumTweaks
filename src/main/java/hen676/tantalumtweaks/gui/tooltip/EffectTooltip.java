package hen676.tantalumtweaks.gui.tooltip;

import hen676.tantalumtweaks.config.Config;
import hen676.tantalumtweaks.util.PotionUtil;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.OminousBottleAmplifierComponent;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.component.type.SuspiciousStewEffectsComponent;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.screen.ScreenTexts;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.ArrayList;
import java.util.List;

public class EffectTooltip {
    public static void onItemTooltip(ItemStack itemStack, Item.TooltipContext tooltipContext, TooltipType tooltipType, List<Text> texts) {
        if (!Config.ENABLE_EFFECT_TOOLTIP) return;
        List<TooltipEffect> effects = getEffect(itemStack);
        if (effects == null || effects.isEmpty()) return;

        if (!texts.contains(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE))) {
            texts.add(ScreenTexts.EMPTY);
            texts.add(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE));
        } else {
            int whenDrankIndex = texts.indexOf(Text.translatable("potion.whenDrank").formatted(Formatting.DARK_PURPLE));
            texts.subList(whenDrankIndex + 1, texts.size()).clear();
        }

        for (TooltipEffect tooltipEffect : effects) {
            texts.add(PotionUtil.potionWhenAppliedToolTip(tooltipEffect));
        }
    }

    private static List<TooltipEffect> getEffect(ItemStack itemStack) {
        Item item = itemStack.getItem();
        if (Items.POTION == item || Items.SPLASH_POTION == item || Items.LINGERING_POTION == item) {
            List<TooltipEffect> effects = new ArrayList<>();
            PotionContentsComponent potionContentsComponent = itemStack.get(DataComponentTypes.POTION_CONTENTS);
            if (potionContentsComponent == null) return effects;
            potionContentsComponent.getEffects().forEach(effectInstance -> effects.add(new TooltipEffect(effectInstance)));
            return effects;
        }
        else if (Items.SUSPICIOUS_STEW == item) {
            List<TooltipEffect> effects = new ArrayList<>();
            SuspiciousStewEffectsComponent suspiciousStewEffectsComponent = itemStack.get(DataComponentTypes.SUSPICIOUS_STEW_EFFECTS);
            if (suspiciousStewEffectsComponent == null) return effects;
            suspiciousStewEffectsComponent.effects().forEach(effectInstance -> effects.add(new TooltipEffect(effectInstance.createStatusEffectInstance())));
            return effects;
        }
        else if (Items.OMINOUS_BOTTLE == item) {
            List<TooltipEffect> effects = new ArrayList<>();
            OminousBottleAmplifierComponent ominousBottleAmplifierComponent = itemStack.get(DataComponentTypes.OMINOUS_BOTTLE_AMPLIFIER);
            if (ominousBottleAmplifierComponent == null) return effects;
            effects.add(new TooltipEffect("bad_omen", ominousBottleAmplifierComponent.value()));
            return effects;
        }
        else if (Items.ENCHANTED_GOLDEN_APPLE == item) {
            List<TooltipEffect> effects = new ArrayList<>();
            effects.add(new TooltipEffect("absorption", 4));
            effects.add(new TooltipEffect("regeneration", 2));
            effects.add(new TooltipEffect("fire_resistance", 1));
            effects.add(new TooltipEffect("resistance", 1));
            return effects;
        } else if (Items.GOLDEN_APPLE == item) {
            List<TooltipEffect> effects = new ArrayList<>();
            effects.add(new TooltipEffect("regeneration", 2));
            return effects;
        }
        return null;
    }

    public static class TooltipEffect {
        private final String name;
        private final int amplifier;

        public TooltipEffect(String name, int amplifier) {
            this.name = name;
            this.amplifier = amplifier;
        }

        public TooltipEffect(StatusEffectInstance effectInstance) {
            this.name = effectInstance.getTranslationKey().substring(17);
            this.amplifier = effectInstance.getAmplifier() + 1;
        }

        public String getName() {
            return name;
        }

        public int getAmplifier() {
            return amplifier;
        }
    }
}
