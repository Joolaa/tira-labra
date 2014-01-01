package regex.parser;

import regex.regexparts.*;

public class Parser {
    
    public Parser(){}
    
    /**
     * Parses the given string representing a regex to a parse tree
     * consisting of REsubexps. Minimal effort to check whether
     * the given string is a valid regex.
     * 
     * @param s string representation of regex
     * @return parse tree of REsubexps
     */
    
    public REsubexp parseString(String st) {
        
        String s = st;
        
        if(s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')')
            s = removeParens(s);
        if(checkSqBrackets(s))
            return parseSqBracket(s);
        
        String[] split = splitRegex(s);
        
        if(split[1].isEmpty()) {
            if(split[0].charAt(split[0].length() - 1) == '*') {
                return new REstar(parseString
                        (split[0].substring(0, split[0].length() - 1)));
            } else if(split[0].charAt(split[0].length() - 1) == '+') {
                REsubexp tempexp = parseString(split[0]
                        .substring(0, split[0].length() - 1));
                return new REconcat(tempexp, new REstar(tempexp));
            } else if(split[0].charAt(split[0].length() - 1) == '?') {
                return new REunion(parseString
                        (split[0].substring(0, split[0].length() - 1)),
                        new REepsilon());
            } else if(split[0].charAt(0) == '.') {
                return new REwildCard();
            } else {
                return new REchar(split[0].charAt(0));
            }
        } else {
            if(split[1].charAt(0) == '|') {
                return new REunion(parseString(split[0]), 
                        parseString(splitRegex(split[1])[1]));
            } else {
                return new REconcat(parseString(split[0]), 
                        parseString(split[1]));
            }
        }
    }
    
    private REsubexp parseSqBracket(String st) {
        String s;
        if(st.charAt(0) == '[' && st.charAt(st.length() - 1) == ']')
            s = st.substring(1, st.length() - 1);
        else
            s = st;
        
        if(s.isEmpty())
            return new REepsilon();
        
        if(s.length() == 1) {
            return new REchar(s.charAt(0));
        }
        
        if(s.charAt(1) == '-') {
            if(s.charAt(0) > s.charAt(2))
                throw new IllegalStateException();
            else if(s.charAt(0) == s.charAt(2)) {
                if(s.length() == 3)
                    return new REchar(s.charAt(0));
                return new REunion(new REchar(s.charAt(0)),
                        parseSqBracket(s.substring(3)));
            } else {
                char fc = s.charAt(0);
                fc++;
                return new REunion(new REchar(s.charAt(0)),
                        parseSqBracket((fc)
                        + s.substring(1)));
            }
        }
        
        return new REunion(new REchar(s.charAt(0)),
                parseSqBracket(s.substring(1, s.length())));
    }
    
    private String[] splitRegex(String splittee) {

        String[] arr = new String[2];

        if (splittee.isEmpty()) {
            arr[0] = "";
            arr[1] = "";
        } else if (splittee.length() == 1) {
            arr[0] = splittee;
            arr[1] = "";
        } else {
            int splitpoint = indexOfTail(splittee);
            arr[0] = splittee.substring(0, splitpoint);
            arr[1] = splittee.substring(splitpoint);
        }
        
        if(!arr[1].isEmpty() && (arr[1].charAt(0) == '*' ||
                arr[1].charAt(0) == '+' || arr[1].charAt(0) == '?')) {
            String[] helper = splitRegex(arr[1]);
            
            arr[0] = arr[0] + helper[0];
            arr[1] = helper[1];
        }
        
        return arr;
    }
    
    private String removeParens(String s) {
        
        if(s.charAt(0) != '(' || 
                s.charAt(s.length() - 1) != ')')
            return s;
        else if(s.length() <= 2)
            return "";
        else {
            boolean test = true;
            int parencount = 0;
            
            for(int i = 0; i < s.length(); i++) {
                if(s.charAt(i) == '(')
                    parencount++;
                else if(s.charAt(i) == ')')
                    parencount--;
                
                if(parencount == 0 && i < s.length() - 1) {
                    test = false;
                    break;
                }
            }
            if(!test)
                return s;
            return s.substring(1, s.length() - 1);
        }
            
    }
    
    private int indexOfTail(String s) {
        
        if(s.isEmpty() || s.length() == 1)
            return 0;
        int sqbrackind;
        if(s.charAt(0) == '[') {
            sqbrackind = indexSqBracket(s);
            if(sqbrackind != 0)
                return sqbrackind;
        }
        
        int unionind = indexOfUnion(s);
        
        if(unionind != 0)
            return unionind;
        
        int parencount = 0;
        
        for(int i = 0; i < s.length(); i++) {          
            if(s.charAt(i) == '(')
                parencount++;
            else if(s.charAt(i) == ')')
                parencount--;
            
            if(parencount == 0) {
                return i + 1;
            }
        }
        
        return 0;
    }
    
    private int indexOfUnion(String s) {
        
        int parencount = 0;
        
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(')
                parencount++;
            else if(s.charAt(i) == ')')
                parencount--;
            else if(s.charAt(i) == '|' && parencount == 0)
                return i;
        }
        
        return 0;
    }
    
    private int indexSqBracket(String s) {
        
        int location = 0;
        
        
        for(int i = 0; i < s.length(); i++) {
            
            if(s.charAt(i) == '[' && location != 0) {
                return location + 1;
            } else if(s.charAt(i) == ']') {
                
                if(location == 0)
                    location = i;
                else
                    return i + 1;
            }
        }
        if(location != 0)
            return location + 1;
        
        return 0;
    }
    
    private boolean checkSqBrackets(String s) {
        if(s.charAt(0) != '[' || s.charAt(s.length() - 1) != ']')
            return false;
        
        boolean rightFound = false;
        
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '[' && rightFound)
                return false;
            else if(s.charAt(i) == ']')
                rightFound = true;
        }
        
        
        return true;
    }
    
}
