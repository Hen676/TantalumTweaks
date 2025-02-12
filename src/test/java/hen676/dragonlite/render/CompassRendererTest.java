package hen676.dragonlite.render;

import hen676.dragonlite.config.Config;
import hen676.dragonlite.gui.screen.option.CompassPlacement;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CompassRendererTest {
    @Test
    public void getWidthTranslateTest() {
        double width1 = CompassRenderer.getWidthTranslate(100, CompassPlacement.TOP_LEFT);
        double width2 = CompassRenderer.getWidthTranslate(100, CompassPlacement.TOP_MIDDLE);
        double width3 = CompassRenderer.getWidthTranslate(100, CompassPlacement.TOP_RIGHT);

        Assertions.assertEquals(Config.COMPASS_X_OFFSET, width1);
        Assertions.assertEquals(50, width2);
        Assertions.assertEquals(100 - Config.COMPASS_X_OFFSET, width3);
    }

    @Test
    public void getHeightTranslateTest() {
        double height1 = CompassRenderer.getHeightTranslate(100, CompassPlacement.TOP_LEFT);
        double height2 = CompassRenderer.getHeightTranslate(100, CompassPlacement.TOP_MIDDLE);
        double height3 = CompassRenderer.getHeightTranslate(100, CompassPlacement.BOTTOM_LEFT);

        Assertions.assertEquals(Config.COMPASS_Y_OFFSET, height1);
        Assertions.assertEquals(Config.COMPASS_Y_OFFSET, height2);
        Assertions.assertEquals(100 - Config.COMPASS_Y_OFFSET, height3);
    }
}
