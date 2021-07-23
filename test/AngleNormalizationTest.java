import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class AngleNormalizationTest {

    @Test
    public void checkAboveDoublePiNormalization() {
        Angle angle = new Angle(Math.PI * 5 / 2);

        assertAngles(Directions.UP, angle);
    }

    @Test
    public void checkBelowZeroPiNormalization() {
        Angle angle = new Angle(-Math.PI / 2);

        assertAngles(Directions.DOWN, angle);
    }

    @Test
    public void checkPiNormalization() {
        Angle angle = new Angle(Math.PI * 3);

        assertAngles(Directions.LEFT, angle);
    }

    @Test
    public void checkMinusPiNormalization() {
        Angle angle = new Angle(-Math.PI * 3);

        assertAngles(Directions.LEFT, angle);
    }

    public void assertAngles(Angle expected, Angle actually) {
        assertEquals(0, expected.compare(actually), actually.getAngle());
    }

}
