package life;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AppTests {

    private PrintStream out = System.out;
    private PrintStream err = System.err;
    // Will be catching system out / err in here
    private ByteArrayOutputStream outCaptor = new ByteArrayOutputStream();
    private ByteArrayOutputStream errCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void beforeEach() {
        // Swap in our captor for system out / err
        System.setOut(new PrintStream(outCaptor));
        System.setErr(new PrintStream(errCaptor));
    }

    @AfterEach
    void afterEach() {
        // Restore the original system out / err
        System.setOut(out);
        System.setErr(err);
    }

    @Test
    void shouldRequireSeed() {
        main();
        assertErr("expected");
    }

    @Test
    void shouldValidateTheSeed() {
        main("[[1,2] [2,3]]");
        assertErr("expected");
    }

    @Test
    void shouldUseTheSeed() {
        main("[[1, 1], [1, 2], [2, 1]]");
        assertOut(" 100: [[1, 1], [1, 2], [2, 1], [2, 2]]");
    }

    @Test
    void shouldJoinAllArgumentsForParsing() {
        main(
                "[[1,",
                "1],",
                " [1,2], [",
                "2,1], [2,2",
                "]]");
        assertOut(" 100: [[1, 1], [1, 2], [2, 1], [2, 2]]");
    }

    @Test
    void shouldSatisfySetRequirement() {
        main("[[5, 5], [6, 5], [7, 5], [5, 6], [6, 6], [7, 6]]");
        assertOut(" 100: [[5, 5], [5, 6], [6, 4], [6, 7], [7, 5], [7, 6]]");
    }

    private void main(String... args) {
        App.main(args);
    }

    private void assertOut(String expText) {
        assertTrue(outCaptor.toString().contains(expText));
        assertTrue(errCaptor.toString().isBlank());
    }

    private void assertErr(String expText) {
        assertTrue(errCaptor.toString().contains(expText));
        assertTrue(outCaptor.toString().isBlank());
    }

}
