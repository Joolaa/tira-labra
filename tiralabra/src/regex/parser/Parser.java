package regex.parser;

import regex.regexparts.*;

public class Parser {
    
    public Parser(){}
    
    public REsubexp parseString(String s) {
        
        if(s.charAt(0) == '(' && s.charAt(s.length() - 1) == ')')
            return parseString(removeParens(s));
        
        String[] split = splitRegex(s);
        
        if(split[1].isEmpty()) {
            if(split[0].charAt(split[0].length() - 1) == '*') {
                return new REstar(parseString
                        (split[0].substring(0, split[0].length() - 1)));
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
    
    private String[] splitRegex(String splittee) {

        String[] arr = new String[2];

        if (splittee.isEmpty()) {
            arr[0] = "";
            arr[1] = "";
        } else if (splittee.length() == 1) {
            arr[0] = splittee;
            arr[1] = "";
        } else if (splittee.charAt(0) != '(') {
            arr[0] = Character.toString(splittee.charAt(0));
            arr[1] = splittee.substring(1);
        } else {
            int splitpoint = indexOfTail(splittee);
            arr[0] = splittee.substring(0, splitpoint);
            arr[1] = splittee.substring(splitpoint);
        }
        
        if(!arr[1].isEmpty() && arr[1].charAt(0) == '*') {
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
        else
            return s.substring(1, s.length() - 1);
    }
    
    private int indexOfTail(String s) {
        
        if(s.isEmpty() || s.length() == 1) {
            return 0;
        } else if (s.charAt(s.length() - 1) == ')') {
            return 0;
        } else if(s.charAt(0) != '(') {
            return 1;
        }
        
        int parencount = 1;
        int result = 0;
        
        for(int i = 1; i < s.length(); i++) {
            if(s.charAt(i) == '(')
                parencount++;
            else if(s.charAt(i) == ')')
                parencount--;
            
            if(parencount == 0) {
                result = i + 1;
                break;
            }
        }
        
        return result;
    }
    
}
