
package regex.regexparts;

import regex.*;

public interface REsubexpBinary extends REsubexp{
    
    /**
     * Get the right branch of parse tree of a binary subexpression
     * in parse tree.
     * @return right branch of parse tree.
     */
    public REsubexp getRight();
    
}
