package regex.regexparts;

import regex.*;

public interface REsubexp {
    
    /**
     * Get the left branch of parse tree.
     * @return REsubexp which is the left branch
     */
    public REsubexp getLeft();
    
    /**
     * Returns true if the expression matches empty string
     * @return True if matches empty, false otherwise
     */
    public boolean matchesEmpty();
    
    /**
     * Returns true if matches some pattern, returns false if
     * does not match any pattern and is equivalent to null expression
     * @return true if matches some character, false otherwise
     */
    public boolean matchesSome();
    
    /**
     * Computes the Brzozowski derivative of the regex with
     * respect to character c
     * @param c the character for which derivative is computed with respect to
     * @return derivative of the regex
     */
    public REsubexp derivative(char c);
    
    /**
     * If possible, returns a more simple, but equivalent, expression.
     * @return the reduced expression
     */
    public REsubexp reduce();
    
    /**
     * Height of the parse tree
     * @return height of the tallest subtree + 1
     */
    public int height();
    
}
