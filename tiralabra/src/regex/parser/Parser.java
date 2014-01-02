package regex.parser;

import regex.regexparts.*;

public class Parser {
    
    public Parser(){}
    
    /**
     * Parses the given string representing a regex to a parse tree
     * consisting of REsubexps. Minimal effort to check whether
     * the given string is a valid regex.
     * 
     * @param st string representation of regex
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
                
            } else if(split[0].charAt(split[0].length() - 1) == '}') {
                
                String[] helper = splitCurlies(split[0]);
                int[] curlyreading = readCurlies(helper[1]);
                REsubexp expression = parseString(helper[0]);
                
                return handleCurlies(expression,
                        curlyreading[0], curlyreading[1]);
                
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
        } else if(!arr[1].isEmpty() && arr[1].charAt(0) == '{') {
            arr = moveCurlies(arr);
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
    
    private String[] moveCurlies(String[] ss) {
        
        int indcurly = 0;
        
        for(int i = 0; i < ss[1].length(); i++) {
            if(ss[1].charAt(i) == '}') {
                indcurly = i;
                break;
            }
        }
        
        if(indcurly >= ss[1].length() - 1)
            return new String[]{ss[0] + ss[1], ""};
        
        return new String[]{ss[0] + ss[1].substring(0, indcurly + 1),
            ss[1].substring(indcurly + 1)};
    }
    
    private String[] splitCurlies(String s) {
        
        int indcurly = 0;
        
        for(int i = s.length() - 1; i >= 0; i--) {
            
            if(s.charAt(i) == '{') {
                indcurly = i;
                break;
            }
        }
        
        return new String[]{s.substring(0, indcurly), s.substring(indcurly)};
    }
    
    private int[] readCurlies(String curlies) {
        
        if(curlies.charAt(0) == '{' &&
                curlies.charAt(curlies.length() - 1) == '}') {
            return readCurlies(curlies.substring(1, curlies.length() - 1));
        }
        
        int[] result = new int[]{-1, -1};
        String temp = "";
        
        for(int i = 0; i < curlies.length(); i++) {
            if(Character.isDigit(curlies.charAt(i))) {
                temp += curlies.charAt(i);
            } else if(curlies.charAt(i) == '-') {
                result[0] = Integer.parseInt(temp);
                temp = "";
            }
        }
        
        result[1] = Integer.parseInt(temp);
        
        if(result[0] == -1)
            result[0] = result[1];
        
        return result;
    }
    
    private REsubexp handleCurlies(REsubexp se, int low, int high) {
        
        if (low > high) {
            throw new IllegalStateException();
        } else if (low == high) {
            return numeratedConcats(se, low);
        } else {
            return new REunion(numeratedConcats(se, low),
                    handleCurlies(se, low + 1, high));
        }
        
    } 
    
    private REsubexp numeratedConcats(REsubexp se, int times) {
        
        if(times == 0)
            return new REepsilon();
        else if(times == 1)
            return se;
        else if(times < 0)
            throw new IllegalStateException();
        else
            return new REconcat(se, numeratedConcats(se, times - 1));
    }
}
