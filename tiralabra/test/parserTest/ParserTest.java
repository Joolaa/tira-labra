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
    REsubexp resingstar;
    REsubexp resing;
    REsubexp reunion;
    REsubexp reparen;
    REsubexp refirstparen;
    REsubexp rehard1;
    REsubexp reunion2;
    
    @Before
    public void setUp() {
        
        par = new Parser();
        re = par.parseString("ab");
        restar = par.parseString("a*b");
        resingstar = par.parseString("a*");
        resing = par.parseString("a");
        reunion = par.parseString("ab|cd");
        reunion2 = par.parseString("a|b|c");
        reparen = par.parseString("a(bc)");
        refirstparen = par.parseString("(ab)c");
        rehard1 = par.parseString("((ab)*|(cd)|ef)|g(h(ij)*)*k");
        
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
    
    @Test
    public void testSingularStar() {
        REstar sta = (REstar) resingstar;
        REchar ch = (REchar) sta.getLeft();
        assertTrue(ch.matches('a'));
        assertTrue(resingstar.getClass().equals(REstar.class));
    }
    
    @Test
    public void testSingleChar() {
        assertTrue(resing.getClass().equals(REchar.class));
        REchar ch = (REchar) resing;
        assertTrue(ch.matches('a'));
    }
    
    @Test
    public void testUnion() {
        assertTrue(reunion.getClass().equals(REunion.class));
        REunion reu = (REunion) reunion;
        REconcat cat1 = (REconcat) reu.getLeft();
        assertTrue(reu.getRight().getClass().equals(REconcat.class));
        REconcat cat2 = (REconcat) reu.getRight();
        REchar ch1 = (REchar) cat1.getLeft();
        REchar ch2 = (REchar) cat2.getRight();
        
        assertTrue(ch1.matches('a'));
        assertTrue(ch2.matches('d'));
    }
    
    @Test
    public void testUnion2() {
        assertTrue(reunion2.getClass().equals(REunion.class));
    }
    
    @Test
    public void testParen() {
        assertTrue(reparen.getClass().equals(REconcat.class));
        REconcat cat = (REconcat) reparen;
        assertTrue(cat.getLeft().getClass().equals(REchar.class));
        assertTrue(cat.getRight().getClass().equals(REconcat.class));
    }
    
    @Test
    public void testParenFirst() {
        assertTrue(refirstparen.getClass().equals(REconcat.class));
        REconcat cat1 = (REconcat) refirstparen;
        assertTrue(cat1.getLeft().getClass().equals(REconcat.class));
        REconcat cat2 = (REconcat) cat1.getLeft();
        
        REchar ch = (REchar) cat2.getRight();
        assertTrue(ch.matches('b'));
    }
    
    @Test
    public void testHard1() {
        assertTrue(rehard1.getClass().equals(REunion.class));
        REunion uni1 = (REunion) rehard1;
        
        assertTrue(uni1.getLeft().getClass().equals(REunion.class));
        REunion uni2 = (REunion) uni1.getLeft();
        assertTrue(uni2.getLeft().getClass().equals(REstar.class));
        assertTrue(uni2.getRight().getClass().equals(REunion.class));
        
        assertTrue(uni1.getRight().getClass().equals(REconcat.class));
    }
    
}
