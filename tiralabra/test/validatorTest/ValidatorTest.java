package validatorTest;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.validator.*;

public class ValidatorTest {
    
    Validator val;
    
    @Before
    public void setUp() {
        val = new Validator();
    }
    
    @Test
    public void testSimple1(){
        val.setRegex("fdsakjlaf");
        assertTrue(val.validate());
        val.setRegex("(a|b)");
        assertTrue(val.validate());
        val.setRegex("(a|b");
        assertTrue(!val.validate());
    }
    
    @Test
    public void testSimple2() {
        val.setRegex("(a|(b|c))");
        assertTrue(val.validate());
        val.setRegex("(|(b|c)");
        assertTrue(!val.validate());
        val.setRegex("(a|(b|c)");
        assertTrue(!val.validate());
    }
    
    @Test
    public void testUnaryOperators() {
        val.setRegex("+sadf");
        assertTrue(!val.validate());
        val.setRegex("(ab|c(*fe))");
        assertTrue(!val.validate());
        val.setRegex("(ab?)(cde(f?))?");
        assertTrue(val.validate());
    }
    
    @Test
    public void testCurlies() {
        val.setRegex("((abc){345-3245}){0-45}");
        assertTrue(val.validate());
        val.setRegex("(abc){45-}");
        assertTrue(!val.validate());
    }
    
    @Test
    public void testSqBrackets() {
        val.setRegex("[[]][[]");
        assertTrue(val.validate());
        val.setRegex("[|(*][*|?]");
        assertTrue(val.validate());
        val.setRegex("[[])");
        assertTrue(!val.validate());
    }
    
}
