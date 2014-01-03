package validatorTest;

import org.junit.Test;
import org.junit.Rule;
import org.junit.Before;
import static org.junit.Assert.*;
import regex.validator.*;

public class CharStackTest {
    
    CharStack st;
    
    @Before
    public void setUp() {
        st = new CharStack();
    }
    
    @Test
    public void testSimple1() {
        st.push('a');
        assertTrue(st.pop() == 'a');
        assertTrue(st.isEmpty());
    }
    
    @Test
    public void testSimple2() {
        st.push('a');
        assertTrue(!st.isEmpty());
        assertTrue(st.peek() == 'a');
        assertTrue(!st.isEmpty());
    }
    
    @Test
    public void testComplicated1() {
        assertTrue(st.isEmpty());
        
        st.push('a');
        st.push('b');
        
        assertTrue(st.pop() == 'b');
        assertTrue(st.peek() == 'a');
        
        st.push('c');
        
        assertTrue(st.peek() == 'c');
        
        st.push('d');
        st.push('e');
        
        assertTrue(st.pop() == 'e');
        assertTrue(st.peek() == 'd');
        assertTrue(st.pop() == 'd');
        assertTrue(st.pop() == 'c');
        assertTrue(!st.isEmpty());
        assertTrue(st.pop() == 'a');
        assertTrue(st.isEmpty());
    }
    
}
