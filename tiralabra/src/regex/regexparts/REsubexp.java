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
    
}
