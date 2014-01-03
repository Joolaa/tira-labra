package regex.validator;

public class CharStack{
    
    private class CharStackNode {
        
        private char c;
        private CharStackNode next;
        
        private CharStackNode(char c, CharStackNode next) {
            this.c = c;
            this.next = next;
        }
    }
    
    private CharStackNode top;
    
    public CharStack() {
        this.top = null;
    }
    
    /**
     * Push a new character to stack
     * @param c character to be pushed
     */
    public void push(char c) {
        
        CharStackNode n = new CharStackNode(c, top);
        top = n;
        
    }
    
    /**
     * Tells whether the stack is empty or not
     * @return true if stack is empty, false otherwise
     */
    public boolean isEmpty() {
        
        return top == null;
        
    }
    
    /**
     * Pops the stack, returns null char if popping
     * an empty stack is attempted
     * @return character from the top of the stack
     */
    public char pop() {
        
        if(this.isEmpty())
            return '\0';
        
        char c = top.c;
        
        top = top.next;
        
        return c;
    }
    
    /**
     * Peeks the stack, returning the top element of the stack without
     * removing it from the stack, return null char
     * if peeking an empty stack is attempted
     * @return character from the top of the stack
     */
    public char peek() {
        
        if(this.isEmpty())
            return '\0';
        
        return top.c;
    }
    
    /**
     * Resets the stack. After calling this method the stack will
     * be empty.
     */
    public void reset() {
        
        this.top = null;
        
    }
    
}
