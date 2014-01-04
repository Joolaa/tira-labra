import java.io.FileReader;
import regex.evaluate.Evaluator;
import java.util.Scanner;

public class Main {
    
    public static void main(String args[]) {
        
        FileReader file;
        Scanner scanner;
        String input;
        String regex = args[1];
        
        boolean readFile = false;
        boolean interactive = false;
        boolean expanded = false;
        
        Evaluator eval = new Evaluator();
        
        if(args.length < 2) {
            
            throw new IllegalArgumentException("Illegal number of arguments");
            
        } else if(args[0].charAt(0) == '-') {
            
            for(int i = 1; i < args[0].length(); i++) {
                
                switch(args[0].charAt(i)) {
                    
                    case 'f':
                        readFile = true;
                    case 'i':
                        interactive = true;
                    case 'e':
                        expanded = true;
                }
            }
            
        } else {
            
            eval.loadRegex(args[0]);
            
            for(int i = 1; i < args.length; i++) {
                System.out.println(eval.evaluateString(args[i]));
            }
            
            return;
        }
        
        if(expanded) {
            
            if(regex.charAt(0) != '^') {
                regex = ".*(" + regex + ")";
            } else {
                regex = regex.substring(1);
            }
            if(regex.charAt(regex.length() - 1) != '$') {
                regex = "(" + regex + ").*";
            } else {
                regex = regex.substring(0, regex.length() - 1);
            }
        }
        
        if(readFile) {
            
            try {
                file = new FileReader(args[args.length - 1]);
            } catch(Exception e) {
                System.out.println("Invalid file");
                return;
            }
            
            scanner = new Scanner(file);
            
            input = scanner.toString();
            
        } else if(interactive) {
            
            scanner = new Scanner(System.in);
            
            eval.loadRegex(regex);
            
            while(true) {
                input = scanner.nextLine();
                
                if(input.equals(":q")) {
                    return;
                } else {
                    System.out.println(eval.evaluateString(input));
                }
            }
        } else {
            
            input = args[2];
            
        }
        
    }
    
}
