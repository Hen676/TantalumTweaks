package hen676.tantalumtweaks.util;

import hen676.tantalumtweaks.gui.tooltip.EffectTooltip;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.Map;
import java.util.Objects;

public class PotionUtil {
    private static final String TOOLTIP_ID = "tooltip.tantalumtweaks.effect.";
    private static final Map<String, Formatting> EFFECT_FORMATTING = Map.ofEntries(
            Map.entry("absorption", Formatting.BLUE),
            Map.entry("bad_omen", Formatting.BLUE),
            Map.entry("blindness", Formatting.RED),
            Map.entry("conduit_power", Formatting.BLUE),
            Map.entry("darkness", Formatting.RED),
            Map.entry("dolphins_grace", Formatting.BLUE),
            Map.entry("fire_resistance", Formatting.BLUE),
            Map.entry("glowing", Formatting.BLUE),
            Map.entry("haste", Formatting.BLUE),
            Map.entry("health_boost", Formatting.BLUE),
            Map.entry("hero_of_the_village", Formatting.BLUE),
            Map.entry("hunger", Formatting.RED),
            Map.entry("infested", Formatting.RED),
            Map.entry("instant_damage", Formatting.RED),
            Map.entry("instant_health", Formatting.BLUE),
            Map.entry("invisibility", Formatting.BLUE),
            Map.entry("jump_boost", Formatting.BLUE),
            Map.entry("levitation", Formatting.RED),
            Map.entry("luck", Formatting.BLUE),
            Map.entry("mining_fatigue", Formatting.RED),
            Map.entry("nausea", Formatting.RED),
            Map.entry("night_vision", Formatting.BLUE),
            Map.entry("oozing", Formatting.RED),
            Map.entry("poison", Formatting.RED),
            Map.entry("raid_omen", Formatting.BLUE),
            Map.entry("regeneration", Formatting.BLUE),
            Map.entry("resistance", Formatting.BLUE),
            Map.entry("saturation", Formatting.BLUE),
            Map.entry("slow_falling", Formatting.BLUE),
            Map.entry("slowness", Formatting.RED),
            Map.entry("speed", Formatting.BLUE),
            Map.entry("strength", Formatting.BLUE),
            Map.entry("trial_omen", Formatting.BLUE),
            Map.entry("unluck", Formatting.RED),
            Map.entry("water_breathing", Formatting.BLUE),
            Map.entry("weakness", Formatting.RED),
            Map.entry("weaving", Formatting.RED),
            Map.entry("wind_charged", Formatting.RED),
            Map.entry("wither", Formatting.RED)
    );


    public static Text potionWhenAppliedToolTip(EffectTooltip.TooltipEffect tooltipEffect) {
        return getText(tooltipEffect.getName(), tooltipEffect.getAmplifier()).formatted(getEffectFormatting(tooltipEffect.getName()));
    }

    public static Text potionWhenAppliedToolTip(String effect, int amplifier) {
        return getText(effect, amplifier).formatted(getEffectFormatting(effect));
    }

    private static MutableText getText(String effect, int amplifier) {
        return switch (effect) {
            case "haste" -> Text.translatable(TOOLTIP_ID + effect, amplifier * 10, amplifier * 20);
            case "saturation" -> Text.translatable(TOOLTIP_ID + effect, amplifier, amplifier * 2);
            case "hero_of_the_village" -> Text.translatable(TOOLTIP_ID + effect, 30 + (6.25 * (amplifier - 1)));

            case "instant_damage" -> Text.translatable(TOOLTIP_ID + effect, amplifier * 3, amplifier * 2);
            case "instant_health" -> Text.translatable(TOOLTIP_ID + effect, amplifier * 2, amplifier * 3);

            case "jump_boost" -> Text.translatable( TOOLTIP_ID + effect, jumpHeight(amplifier), amplifier);

            case "mining_fatigue" -> Text.translatable(TOOLTIP_ID + effect, Math.min(amplifier * 10, 100), miningFatigueSpeed(amplifier));

            case "poison" -> Text.translatable( TOOLTIP_ID + effect, perSecond(amplifier, 25 ));
            case "regeneration" -> Text.translatable( TOOLTIP_ID + effect, perSecond(amplifier, 50 ));
            case "wither" -> Text.translatable(TOOLTIP_ID + effect, perSecond(amplifier, 40 ));

            case "resistance", "speed" -> Text.translatable(TOOLTIP_ID + effect, amplifier * 20);
            case "slowness" -> Text.translatable(TOOLTIP_ID + effect, amplifier * 15);
            case "health_boost", "absorption", "weakness" -> Text.translatable(TOOLTIP_ID + effect, amplifier * 4);
            case "strength" -> Text.translatable(TOOLTIP_ID + effect, amplifier * 3);
            case "levitation" -> Text.translatable( TOOLTIP_ID + effect, amplifier * 0.9);
            case "hunger" -> Text.translatable(TOOLTIP_ID + effect, amplifier * 0.1);
            default -> Text.translatable(TOOLTIP_ID + effect, amplifier);
        };
    }

    private static Formatting getEffectFormatting(String effect) {
        return Objects.requireNonNullElse(EFFECT_FORMATTING.get(effect), Formatting.WHITE);
    }

    //TODO:: Add tests
    static double miningFatigueSpeed(int amplifier) {
        double speed = Math.pow(0.3,Math.min(amplifier, 4)) * 100;
        return Math.round(speed * 100.0) / 100.0;
    }

    //TODO:: Add tests
    static double jumpHeight(int amplifier) {
        double height = 3.92 * Math.log((double) (5 * amplifier) / 196 + (double) 31 / 28) / Math.log(0.98) + 5 * amplifier + 21;
        return Math.round(height * 100.0) / 100.0;
    }

    //TODO:: Add tests
    static double perSecond(int amplifier, int ticks) {
        double perSecond = (double) 20 / (ticks >> (amplifier - 1));
        return Math.round(perSecond * 100.0) / 100.0;
    }
}
