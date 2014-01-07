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
            
            if(amount > 1)
                chnew += ran.nextInt(amount - 1);
            
            result += chnew;
        }
        
        return result;
    }
    
    @Test
    public void testSize10() {
        
        dummyCalculation();
        
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
        
        dummyCalculation();
        
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
        
        dummyCalculation();
        
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
        
        dummyCalculation();
        
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
    
    @Test
    public void testGrowingExpression() {
        
        dummyCalculation();
        
        for(int i = 1; i < 25; i++) {
            
            String input = generateInput(i, 1);
            
            String regex = "A?{" + i + "}A{" + i + "}";
            
            long starttime = System.nanoTime();
            
            eval.loadRegex(regex);
            
            eval.evaluateString(input);
            
            long endtime = System.nanoTime();
            
            long elapsed = endtime - starttime;
            
            System.out.println("Growing size" + i +
                    ": " + elapsed / 1000 + "mcrs");
            
            // 20 ms * i^2 + 0.2 ms
            assertTrue(elapsed / 1000 < 10000 * i * i + 2000000);
        }
    }
    
    public void dummyCalculation() {
        
        String input = generateInput(1, 1);
            
        String regex = "A?{" + 1 + "}A{" + 1 + "}";
        
        eval.loadRegex(regex);
            
        eval.evaluateString(input);
        
    }
    
}
