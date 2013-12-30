package evaluateTest;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.parser.*;
import regex.regexparts.*;
import regex.evaluate.*;

public class EvaluatorTest {
    
    Evaluator eval;
    Parser par;
    
    @Before
    public void setUp() {
        par = new Parser();
        eval = new Evaluator(par);
    }
    
    @Test
    public void testSimpleRegex() {
        eval.loadRegex("abcdef");
        assertTrue(eval.evaluateString("abcdef"));
        assertTrue(!eval.evaluateString("abcde"));
    }
    
    @Test
    public void testUnion() {
        eval.loadRegex("abc|abe|a");
        assertTrue(eval.evaluateString("abc"));
        assertTrue(eval.evaluateString("abe"));
        assertTrue(eval.evaluateString("a"));
        
        assertTrue(!eval.evaluateString("ab"));
        assertTrue(!eval.evaluateString("abf"));
        assertTrue(!eval.evaluateString("b"));
        assertTrue(!eval.evaluateString("acb"));
    }
    
    @Test
    public void testStar() {
        eval.loadRegex("a*b*");
        assertTrue(eval.evaluateString(""));
        assertTrue(eval.evaluateString("a"));
        assertTrue(eval.evaluateString("b"));
        assertTrue(eval.evaluateString("bbbbbbbbb"));
        assertTrue(eval.evaluateString("aaaaaaaaabbbbbbb"));
        
        assertTrue(!eval.evaluateString("c"));
        assertTrue(!eval.evaluateString("bbbbbbaaaaa"));
    }
    
    @Test
    public void testComplicated1() {
        eval.loadRegex("((ab)*|(c|d*))f");
        assertTrue(eval.evaluateString("f"));
        assertTrue(eval.evaluateString("abf"));
        assertTrue(eval.evaluateString("cf"));
        assertTrue(eval.evaluateString("dddddddddf"));
        assertTrue(eval.evaluateString("ababababababf"));
        
        assertTrue(!eval.evaluateString("bf"));
        assertTrue(!eval.evaluateString("abababababaf"));
        assertTrue(!eval.evaluateString("ccf"));
        assertTrue(!eval.evaluateString(""));
        assertTrue(!eval.evaluateString("dddddddd"));
    }
    
    @Test
    public void testWildCardSimple() {
        eval.loadRegex(".");
        assertTrue(eval.evaluateString("a"));
        
        assertTrue(!eval.evaluateString("ab"));
    }
    
    @Test
    public void testWildCardComplicated1() {
        eval.loadRegex("((a.)*|(.|d*))f");
        assertTrue(eval.evaluateString("f"));
        assertTrue(eval.evaluateString("ajf"));
        assertTrue(eval.evaluateString("hf"));
        assertTrue(eval.evaluateString("dddddddddf"));
        assertTrue(eval.evaluateString("abaeabaqaxabf"));
        assertTrue(eval.evaluateString("bf"));
        
        assertTrue(!eval.evaluateString("aqabaeabacaf"));
        assertTrue(!eval.evaluateString("ccf"));
        assertTrue(!eval.evaluateString(""));
        assertTrue(!eval.evaluateString("dddddddd"));
    }
    
    @Test
    public void testComplicatedPlus1() {
        eval.loadRegex("((ab)+|(c|d+))f");
        assertTrue(eval.evaluateString("abf"));
        assertTrue(eval.evaluateString("cf"));
        assertTrue(eval.evaluateString("dddddddddf"));
        assertTrue(eval.evaluateString("ababababababf"));
        
        assertTrue(!eval.evaluateString("bf"));
        assertTrue(!eval.evaluateString("abababababaf"));
        assertTrue(!eval.evaluateString("ccf"));
        assertTrue(!eval.evaluateString(""));
        assertTrue(!eval.evaluateString("dddddddd"));
        assertTrue(!eval.evaluateString("f"));
    }
    
    @Test
    public void testComplicatedQuestionMark1() {
        eval.loadRegex("((ab)?|(c|d?))f");
        assertTrue(eval.evaluateString("f"));
        assertTrue(eval.evaluateString("abf"));
        assertTrue(eval.evaluateString("cf"));
        assertTrue(eval.evaluateString("df"));
        
        assertTrue(!eval.evaluateString("bf"));
        assertTrue(!eval.evaluateString("abababababaf"));
        assertTrue(!eval.evaluateString("ccf"));
        assertTrue(!eval.evaluateString(""));
        assertTrue(!eval.evaluateString("dddddddd"));
        assertTrue(!eval.evaluateString("dddddddddf"));
        assertTrue(!eval.evaluateString("ababababababf"));

    }
}
