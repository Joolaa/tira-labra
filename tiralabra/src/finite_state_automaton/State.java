package finite_state_automaton;

import java.util.HashMap;

public class State {
    
    private boolean isFinal;
    private HashMap<Character, State> transitions;
    
    public State(boolean isFinal, HashMap<Character, State> transitions) {
        this.isFinal = isFinal;
        this.transitions = transitions;
    }
    
    public State() {
        this(false, new HashMap());
    }
    
    public State transfer(char letter) {
        if(!transitions.containsKey(letter))
            return null;
        return transitions.get(letter);
    }
    
    public void setIsFinal(boolean b) {
        isFinal = b;
    }
    
    public void addTransition(char c, State s) {
        transitions.put(c, s);
    }
    
    public boolean getIsFinal() {
        return isFinal;
    }
    
}
