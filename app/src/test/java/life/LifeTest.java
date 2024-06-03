package life;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class LifeTest {

    private final ByteArrayOutputStream bufferedOut = new ByteArrayOutputStream();
    private PrintStream out;

    @BeforeEach
    void setUp() {
        out = System.out;
        System.setOut(new PrintStream(bufferedOut));
    }

    @AfterEach
    void tearDown() {
        System.setOut(out);
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "[]", " [] ", "[ ]"
    })
    void main_emptyToBlank(String arg) {
        Life.main(new String[]{arg});
        var lines = bufferedOut.toString().lines().toList();
        assertThat(lines, hasSize(100));
        String last = lines.getLast();
        assertThat(last, Matchers.is("100: []"));
    }

    @Test
    void main_glider() {
        Life.main(new String[]{"[[3,1], [3,2], [3,3], [2,3], [1,2]]"});
        var lines = bufferedOut.toString().lines().toList();
        assertThat(lines, hasSize(100));
        String last = lines.getLast();
        assertThat(last, startsWith("100: "));
        assertThat(last, containsString("[28, 26]"));
        assertThat(last, containsString("[28, 27]"));
        assertThat(last, containsString("[28, 28]"));
        assertThat(last, containsString("[27, 28]"));
        assertThat(last, containsString("[26, 27]"));
    }
}