package life;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        App.main(new String[] {});
        assertTrue(errCaptor.toString().contains("expected"));
        assertTrue(outCaptor.toString().trim().isEmpty());
    }

    @Test
    void shouldOnlyAcceptSingleArgument() {
        App.main(new String[] { "[[1,1]]", "[[2,2]]" });
        assertTrue(errCaptor.toString().contains("expected"));
        assertTrue(outCaptor.toString().trim().isEmpty());
    }

    @Test
    void shouldUseTheSeed() {
        App.main(new String[] { "[[1,1]]" });
        assertTrue(errCaptor.toString().trim().isEmpty());
        assertEquals("[[1, 1]]", outCaptor.toString().trim());
    }

}
