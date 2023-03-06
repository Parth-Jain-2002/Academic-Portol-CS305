package softwareEngineering;
import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ScannerTest {
    @Test public void testScanner() {
        String input = "7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner sc = new softwareEngineering.Scanner();
        assertEquals(sc.nextInt(), 7);

        sc.close();
    }

    @Test public void testScanner1() {
        String input = "7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner sc = new softwareEngineering.Scanner();
        assertEquals(sc.nextLine(), "7");

        sc.close();
    }

    @Test public void testScanner2() {
        String input = "Parth\n7\n";
        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);
        Scanner sc = new softwareEngineering.Scanner();
        assertEquals(sc.nextInt(), 7);

        sc.close();
    }

}
