import com.lon.game.logic.angle.Angle;
import org.junit.Assert;
import org.junit.Test;

public class CoordAngleTest {

    @Test
    public void zeroAngleTest() {
        Angle angle = new Angle(10, 0);
        Assert.assertEquals(angle.getRadians(),0, 0.000001);
    }

    @Test
    public void quarterAngleTest() {
        Angle angle = new Angle(0, 10);
        Assert.assertEquals(angle.getRadians(), Math.PI/2, 0.000001);
    }

    @Test
    public void halfAngleTest() {
        Angle angle = new Angle(-10, 0);
        Assert.assertEquals(angle.getRadians(), Math.PI, 0.000001);
    }

    @Test
    public void threeQuarterAngleTest() {
        Angle angle = new Angle(0, -10);
        Assert.assertEquals(angle.getRadians(), Math.PI + Math.PI/2, 0.000001);
    }

    @Test
    public void oneEighthAngleTest() {
        Angle angle = new Angle(10, 10);
        Assert.assertEquals(angle.getRadians(),Math.PI/4, 0.000001);
    }

    @Test
    public void threeEighthAngleTest() {
        Angle angle = new Angle(-10, 10);
        Assert.assertEquals(angle.getRadians(),Math.PI - Math.PI/4, 0.000001);
    }
    @Test
    public void fiveEighthAngleTest() {
        Angle angle = new Angle(-10, -10);
        Assert.assertEquals(angle.getRadians(),Math.PI + Math.PI/4, 0.000001);
    }
    @Test
    public void sevenEighthAngleTest() {
        Angle angle = new Angle(10, -10);
        Assert.assertEquals(angle.getRadians(),2 * Math.PI - Math.PI/4, 0.000001);
    }
}
