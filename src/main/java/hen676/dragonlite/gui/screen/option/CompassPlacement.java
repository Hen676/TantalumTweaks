package hen676.dragonlite.gui.screen.option;

import net.minecraft.util.function.ValueLists;

import java.util.function.IntFunction;

public enum CompassPlacement {
    TOP_LEFT(0),
    TOP_MIDDLE(1),
    TOP_RIGHT(2),
    BOTTOM_LEFT(3),
    BOTTOM_RIGHT(4);

    private static final IntFunction<CompassPlacement> BY_ID = ValueLists.createIdToValueFunction(CompassPlacement::getId, values(), ValueLists.OutOfBoundsHandling.ZERO);
    private final int id;

    CompassPlacement(int id) {
        this.id = id;
    }

    public static CompassPlacement byId(int id) {
        return BY_ID.apply(id);
    }

    public int getId() {
        return this.id;
    }
}
