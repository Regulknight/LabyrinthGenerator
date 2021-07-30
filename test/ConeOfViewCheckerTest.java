
import com.lon.game.logic.ConeOfView;
import com.lon.game.logic.Directions;
import com.lon.game.logic.Sector;
import org.junit.jupiter.api.Test;

import java.awt.geom.Point2D;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConeOfViewCheckerTest {
    private static final Vector2 center = new Vector2(25, 25);
    private static final double rightAngle = Math.PI / 2;

    @Test
    public void upperMiddleConeTest() {
        Sector up = new Sector(Directions.UP, rightAngle);
        ConeOfView upCone = new ConeOfView(2, up);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                boolean result = (24 <= i && i <= 26 && j == 24) || (i == 25 && j == 25);

                assertEquals(result, upCone.isPointContainInCone(center, new Vector2(i, j)), i + " " + j);
            }
        }
    }

    @Test
    public void rightMiddleConeTest() {
        Sector right = new Sector(Directions.RIGHT, rightAngle);
        ConeOfView rightCone = new ConeOfView(2, right);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                boolean result = (24 <= j && j <= 26 && i == 26) || (i == 25 && j == 25);

                assertEquals(result, rightCone.isPointContainInCone(center, new Vector2(i, j)), i + " " + j);
            }
        }
    }

    @Test
    public void downMiddleConeTest() {
        Sector down = new Sector(Directions.DOWN, rightAngle);
        ConeOfView downCone = new ConeOfView(2, down);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                boolean result = (24 <= i && i <= 26 && j == 26) || (i == 25 && j == 25);

                assertEquals(result, downCone.isPointContainInCone(center, new Vector2(i, j)), i + " " + j);
            }
        }
    }

    @Test
    public void leftMiddleConeTest() {
        Sector left = new Sector(Directions.LEFT, rightAngle);
        ConeOfView leftCone = new ConeOfView(2, left);

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                boolean result = (24 <= j && j <= 26 && i == 24) || (i == 25 && j == 25);

                assertEquals(result, leftCone.isPointContainInCone(center, new Vector2(i, j)), i + " " + j);
            }
        }
    }
}
