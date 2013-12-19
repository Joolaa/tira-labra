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
        
        while(point < stringRep.length()) {
            switch(stringRep.charAt(point)) {
                case '*':
                    curState.addTransition(stringRep.charAt(point - 1),
                            curState);
                    break;
                    
                default:
                    State nextState = new State();
                    curState.addTransition(stringRep.charAt(point),
                            nextState);
                    curState = nextState;
                    break; 
            }
            
            point++;
        }
        
        curState.setIsFinal(true);
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
