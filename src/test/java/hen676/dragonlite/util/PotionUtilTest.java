package hen676.dragonlite.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PotionUtilTest {

    @Test
    public void miningFatigueSpeedTest() {
        double amplifier_one = PotionUtil.miningFatigueSpeed(1);
        double amplifier_two = PotionUtil.miningFatigueSpeed(2);
        double amplifier_three = PotionUtil.miningFatigueSpeed(3);

        Assertions.assertEquals(30.0D,amplifier_one);
        Assertions.assertEquals(9.0D,amplifier_two);
        Assertions.assertEquals(2.7D,amplifier_three);
    }

    @Test
    public void jumpHeightTest() {
        double jump_one = PotionUtil.jumpHeight(1);
        double jump_two = PotionUtil.jumpHeight(2);
        double jump_three = PotionUtil.jumpHeight(3);

        Assertions.assertEquals(1.83D,jump_one);
        Assertions.assertEquals(2.51D,jump_two);
        Assertions.assertEquals(3.28D,jump_three);
    }

    @Test
    public void perSecondTest() {
        double per_sec_one = PotionUtil.perSecond(1, 30);
        double per_sec_two = PotionUtil.perSecond(2, 30);
        double per_sec_three = PotionUtil.perSecond(3, 30);

        Assertions.assertEquals(0.67D,per_sec_one);
        Assertions.assertEquals(1.33D,per_sec_two);
        Assertions.assertEquals(2.86D,per_sec_three);
    }
}
