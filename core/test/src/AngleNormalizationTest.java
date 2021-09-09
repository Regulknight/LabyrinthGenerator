import com.lon.game.logic.angle.Angle;
import com.lon.game.logic.angle.Directions;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AngleNormalizationTest {

    @Test
    public void checkAboveDoublePiNormalization() {
        float angle = Angle.normalizeAngle(Math.PI * 5 / 2);

        assertEquals(Directions.UP, angle, 0.00001);
    }

    @Test
    public void checkBelowZeroPiNormalization() {
        float angle = Angle.normalizeAngle(-Math.PI / 2);

        assertEquals(Directions.DOWN, angle, 0.00001);
    }

    @Test
    public void checkPiNormalization() {
        float angle = Angle.normalizeAngle(Math.PI * 3);

        assertEquals(Directions.LEFT, angle, 0.00001);
    }

    @Test
    public void checkMinusPiNormalization() {
        float angle = Angle.normalizeAngle(-Math.PI * 3);

        assertEquals(Directions.LEFT, angle, 0.00001);
    }

}
