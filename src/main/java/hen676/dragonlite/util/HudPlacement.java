package hen676.dragonlite.util;

import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum HudPlacement {
    TOP_LEFT(0),
    TOP_RIGHT(1),
    BOTTOM_LEFT(2),
    BOTTOM_RIGHT(3);

    private static final IntFunction<HudPlacement> BY_ID = ValueLists.createIdToValueFunction(HudPlacement::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
    private final int id;

    HudPlacement(int id) {
        this.id = id;
    }

    public static HudPlacement byId(int id) {
        return BY_ID.apply(id);
    }

    public int getId() {
        return this.id;
    }
}
