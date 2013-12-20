package regex;

import finite_state_automaton.State;

public class Regex {
    
    private String stringRep;
    private State initState;
    
    public Regex(String stringRep) {
        this.stringRep = stringRep;
        compileRegex();
    }
    
    private void compileRegex() {
        
        int point = 0;
        State curState = new State();
        initState = curState;
        State prevState = curState;
        
        while(point < stringRep.length()) {
            switch(stringRep.charAt(point)) {
                case '*':
                    curState.addTransition(stringRep.charAt(point - 1),
                            curState);
                    prevState.setIsFinal(true);
                    break;
                    
                default:
                    State nextState = new State();
                    curState.addTransition(stringRep.charAt(point),
                            nextState);
                    prevState = curState;
                    curState = nextState;
                    break; 
            }
            
            point++;
        }
        
        curState.setIsFinal(true);
    }
    
    public Regex derive(char c) {
        
        if(stringRep.isEmpty())
            return new Regex("\0");
        
        if(stringRep.length() == 1) {
            if(stringRep.charAt(0) == c)
                return new Regex("");
            return new Regex("\0");
        }
        
        return new Regex()
    }
    
    private Regex[] splitRegex() {
        
        Regex[] arr = new Regex[2];
        
        if(stringRep.isEmpty()) {
            arr[0] = new Regex("");
            arr[1] = new Regex("");
        } else if(stringRep.length() == 1) {
            arr[0] = this;
            arr[1] = new Regex("");
        } else if(stringRep.charAt(0) != '(') {
            arr[0] = new Regex(Character.toString(stringRep.charAt(0)));
            arr[1] = new Regex(stringRep.substring(1));
        } else {
            int splitpoint = indexOfTail();
            arr[0] = new Regex(stringRep.substring(0, splitpoint));
            arr[1] = new Regex(stringRep.substring(splitpoint));
        }
        
        return arr;
    }
    
    private int indexOfTail() {
        
        if(stringRep.isEmpty() || stringRep.length() == 1) {
            return 0;
        } else if (stringRep.charAt(stringRep.length() - 1) == ')') {
            return 0;
        } else if(stringRep.charAt(0) != '(') {
            return 1;
        }
        
        int parencount = 1;
        int result = 0;
        
        for(int i = 1; i < stringRep.length(); i++) {
            if(stringRep.charAt(i) == '(')
                parencount++;
            else if(stringRep.charAt(i) == ')')
                parencount--;
            
            if(parencount == 0) {
                result = i + 1;
                break;
            }
        }
        
        return result;
    }
    
    public boolean evalString(String s) {
        
        State curState = initState;
        int point = 0;
        
        while(point < s.length() && curState != null) {
            curState = curState.transfer(s.charAt(point));
            point++;
        }
        
        if(curState == null || point < s.length() - 1) {
            return false;
        }
        
        return curState.getIsFinal();
    }
    
}
