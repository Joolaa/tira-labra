package performanceTest;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.evaluate.Evaluator;
import java.util.Random;
import java.util.regex.*;

public class PerformanceTest {
    
    Evaluator eval;
    
    @Before
    public void setUp() {
        
        eval = new Evaluator();
        
    }
    
    private String generateInput(int size, int amount) {
        
        Random ran = new Random();
        
        String result = "";
        
        for(int i = 0; i < size; i++) {
            
            char chnew = 'A';
            
            chnew += ran.nextInt(amount - 1);
            
            result += chnew;
        }
        
        return result;
    }
    
    @Test
    public void testSize10() {
        
        String input = generateInput(10, 4);
        
        long starttime = System.nanoTime();
        
        eval.loadRegex("[A-D]+");
        
        boolean res = eval.evaluateString(input);
        
        long endtime = System.nanoTime();
        
        long elapsed = endtime - starttime;
        
        System.out.println("Size 10: " + elapsed);
        
        //250 000 ns == 0.25 ms
        assertTrue(elapsed < 250000);
        assertTrue(res);
        
        
    }
    
    @Test
    public void testSize100() {
        
        String input = generateInput(100, 4);
        
        long starttime = System.nanoTime();
        
        eval.loadRegex("[A-D]+");
        
        eval.evaluateString(input);
        
        long endtime = System.nanoTime();
        
        long elapsed = endtime - starttime;
        
        System.out.println("Size 100: " + elapsed);
        
        //30 000 000 ns == 30 ms
        assertTrue(elapsed < 30000000);

        
        
    }
    
    @Test
    public void testSize1000() {
        
        String input = generateInput(1000, 4);
        
        long starttime = System.nanoTime();
        
        eval.loadRegex("[A-D]+");
        
        eval.evaluateString(input);
        
        long endtime = System.nanoTime();
        
        long elapsed = endtime - starttime;
        
        System.out.println("size 1000: " + elapsed);
        
        //60 000 000 ns = 70 ms
        assertTrue(elapsed < 70000000);
        
        //java stdlib regexes:
        
        starttime = System.nanoTime();
        
        input.matches("[A-D]+");
        
        endtime = System.nanoTime();
        elapsed = endtime - starttime;
        
        System.out.println("Java size 1000: " + elapsed);
        
        
    }
    
    @Test
    public void testSize10000() {
        
        String input = generateInput(10000, 4);
        
        long starttime = System.nanoTime();
        
        eval.loadRegex("[A-D]+");
        
        eval.evaluateString(input);
        
        long endtime = System.nanoTime();
        
        long elapsed = endtime - starttime;
        
        System.out.println("size 10000: " + elapsed);
        
        //80 000 000 ns == 60 ms
        assertTrue(elapsed < 60000000);
        
        //Java stdlib regexes:
        starttime = System.nanoTime();
        
        input.matches("[A-D]+");
        
        endtime = System.nanoTime();
        elapsed = endtime - starttime;
        
        System.out.println("Java size 10000: " + elapsed);
    }
    
}
