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
        assertErr("USAGE");
    }

    @Test
    void shouldValidateTheSeed() {
        main("[[1,2]", "[2,3]]");
        assertErr("expected");
    }

    @Test
    void shouldUseTheSeed() {
        main("[[1,1]]");
        assertOut("[[1, 1]]");
    }

    @Test
    void shouldJoinAllArguments() {
        main("[[", "1,1]", ",[2", ",2]]");
        assertOut("[[1, 1], [2, 2]]");
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
