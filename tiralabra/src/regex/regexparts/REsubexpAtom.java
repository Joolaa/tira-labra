package regex.regexparts;

import regex.*;


public interface REsubexpAtom extends REsubexp{
    
    /**
     * Returns true if atomic regex subexpression matches the character.
     * @param c character to match
     * @return true if the character matches
     */
    public boolean matches(char c);
}
