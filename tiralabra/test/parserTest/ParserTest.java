package parserTest;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.parser.*;
import regex.regexparts.*;

public class ParserTest {
    
    Parser par;
    REsubexp re;
    REsubexp restar;
    
    @Before
    public void setUp() {
        
        par = new Parser();
        re = par.parseString("ab");
        restar = par.parseString("a*b");
        
    }
    
    @Test
    public void testSimple() {
        
        REchar ch = (REchar) re.getLeft();
        assertTrue(ch.matches('a'));
        assertTrue(!ch.matches('b'));
    }
    
    @Test
    public void testConcat() {
        REconcat cat = (REconcat) re;
        REchar ch = (REchar) cat.getRight();
        assertTrue(ch.matches('b'));
        assertTrue(!ch.matches('a'));
    }
    
    @Test
    public void testStar1() {
        REstar star = (REstar) restar.getLeft();
        REchar ch = (REchar) star.getLeft();
        assertTrue(ch.matches('a'));
    }
    
    @Test
    public void testStar2() {
        REconcat cat = (REconcat) restar;
        REchar ch = (REchar) cat.getRight();
        assertTrue(ch.matches('b'));
    }
    
}
