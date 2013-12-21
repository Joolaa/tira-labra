package regex;

public class Regex {
    
    private String stringRep;
    
    public Regex(String stringRep) {
        this.stringRep = stringRep;
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
    

    
}
