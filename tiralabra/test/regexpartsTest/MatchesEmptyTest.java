package regexpartsTest;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.parser.*;
import regex.regexparts.*;

public class MatchesEmptyTest {
    
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
    public void testEpsilon() {
        REsubexpEpsilon eps = new REsubexpEpsilon();
        assertTrue(eps.matchesEmpty());
    }
    
    @Test
    public void testSingular() {
        assertTrue(!resing.matchesEmpty());
    }
    
    @Test
    public void testStar() {
        assertTrue(resingstar.matchesEmpty());
    }
    
    @Test
    public void testConcat() {
        assertTrue(!re.matchesEmpty());
    }
    
    @Test
    public void testCatStar() {
        assertTrue(!restar.matchesEmpty());
    }
    
    @Test
    public void testUnionStar() {
        REunion uni1 = new REunion(resing, resingstar);
        REunion uni2 = new REunion(resingstar, resing);
        
        assertTrue(uni1.matchesEmpty());
        assertTrue(uni2.matchesEmpty());
    }
    
}
