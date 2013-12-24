package regexpartsTest;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.parser.*;
import regex.regexparts.*;

public class DerivativeTest {
    
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
    public void testSimpleDeriv() {
        REsubexp d = resing.derivative('a');
        assertTrue(d.matchesEmpty());
        REsubexp e = resing.derivative('b');
        assertTrue(!e.matchesEmpty());
    }
    
    @Test
    public void testStar() {
        REsubexp d = resingstar.derivative('a');
        assertTrue(d.matchesEmpty());
        d = d.derivative('a');
        assertTrue(d.matchesEmpty());
        d = d.derivative('b');
        assertTrue(!d.matchesEmpty());
        REsubexp e = resingstar.derivative('b');
        assertTrue(!e.matchesEmpty());
    }
    
    @Test
    public void testConcat1() {
        REsubexp d = reparen.derivative('a');
        assertTrue(!d.matchesEmpty());
        d = d.derivative('b');
        assertTrue(!d.matchesEmpty());
        d = d.derivative('c');
        assertTrue(d.matchesEmpty());
    }
    
    @Test
    public void testConcat2() {
        REsubexp d = refirstparen.derivative('a');
        assertTrue(!d.matchesEmpty());
        d = d.derivative('b');
        assertTrue(!d.matchesEmpty());
        d = d.derivative('c');
        assertTrue(d.matchesEmpty());
        d = d.derivative('c');
        assertTrue(!d.matchesEmpty());
    }
    
    @Test
    public void testUnion() {
        REsubexp d = reunion.derivative('c');
        assertTrue(!d.matchesEmpty());
        d = d.derivative('d');
        assertTrue(d.matchesEmpty());
        d = d.derivative('a');
        assertTrue(!d.matchesEmpty());
    }
    
    @Test
    public void testHard1() {
        assertTrue(rehard1.matchesEmpty());
        REsubexp d = rehard1.derivative('a');
        assertTrue(!d.matchesEmpty());
        d = d.derivative('b');
        assertTrue(d.matchesEmpty());
        d = d.derivative('a');
        assertTrue(!d.matchesEmpty());
        d = d.derivative('b');
    }
    
    @Test
    public void testHard2() {
        REsubexp d = rehard1.derivative('g');
        assertTrue(!d.matchesEmpty());
        d = d.derivative('k');
        assertTrue(d.matchesEmpty());
    }
    
}
