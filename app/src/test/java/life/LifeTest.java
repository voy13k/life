package life;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LifeTest {

    @Test
    void shouldCreateDeadGridOfRightSize() {
        Life life = new Life(3, 4);
        assertEquals("""
                [false, false, false]
                [false, false, false]
                [false, false, false]
                [false, false, false]
                """, life.toString());
    }

}
