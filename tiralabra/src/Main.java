import finite_state_automaton.State;
import regex.Regex;

public class Main {
    
    public static void main(String args[]) {
        Regex r = new Regex("abc*");
        
        System.out.println(r.evalString("abccc"));
        System.out.println(r.evalString("ab"));
    }
    
}
