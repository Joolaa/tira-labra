package regex.regexparts;

import regex.*;

public interface REsubexp {
    
    /**
     * Get the left branch of parse tree.
     * @return REsubexp which is the left branch
     */
    public REsubexp getLeft();
    
}
