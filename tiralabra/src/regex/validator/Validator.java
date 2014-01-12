package regex.validator;

public class Validator {
    
    private String regex;
    private CharStack stack;
    
    /**
     * 
     * @param regex String representation of a regex
     */
    public Validator(String regex) {
        this.regex = regex;
        this.stack = new CharStack();
    }
    
    public Validator() {
        this(null);
    }
    
    /**
     * Validates a regex, that is, tells whether the given string
     * represents a valid regex
     * @return true if string is a syntactically valid regex, false otherwise
     */
    public boolean validate() {
        
        this.stack.reset();
        
        for(int i = 0; i < regex.length(); i++) {
            
            char ch = regex.charAt(i);
            
            if(stack.peek() == '[') {
                if(ch == ']') {
                    stack.pop();
                }
            } else if(stack.peek() == '{') {
                if(ch == '}') {
                    if(regex.charAt(i - 1) == '-')
                        return false;
                    stack.pop();
                } else if(!Character.isDigit(ch) && ch != '-') {
                    return false;
                } else if(ch == '-' && regex.charAt(i - 1) == '{') {
                    return false;
                }
            } else {
                
                switch(ch) {
                    
                    case '(':
                    case '[':
                    case '{':
                        stack.push(ch);
                        break;
                        
                    case ')':
                        char top = stack.pop();
                        if(top != '(')
                            return false;
                        break;
                    
                    case '|':
                        if(i == 0 || i == regex.length() - 1 ||
                                regex.charAt(i - 1) == '|')
                            return false;
                        break;
                    
                    case '*':
                    case '+':
                    case '?':
                        if(i == 0 || regex.charAt(i - 1) == '(')
                            return false;
                        break;
                    
                    default: break;
                       
                }
            }
        }
        
        if(!stack.isEmpty())
            return false;
        
        return true;
    }
    
    /**
     * Set the regex to validate
     * @param re regex to validate in String form
     */
    public void setRegex(String re) {
        this.regex = re;
    }
    
}
