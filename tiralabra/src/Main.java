import java.io.FileReader;
import regex.evaluate.Evaluator;
import java.util.Scanner;

public class Main {
    
    /*
     * Command line parameters:
     * 
     * -f <regex> <srcfile>, from file mode, read input from file
     * 
     * -i <regex>, interactive mode, match strings line by line form stdin,
     * cannot be enabled simultaneously with from file mode. :q quits
     * 
     * -g <regex> <srcfile>, grep mode, read lines from the file and
     * print lines which match the regex to stdout
     */
    
    public static void main(String args[]) {
        
        FileReader file;
        Scanner scanner;
        String input;
        String regex = args[1];
        
        boolean readFile = false;
        boolean interactive = false;
        boolean expanded = false;
        boolean grep = false;
        
        Evaluator eval = new Evaluator();
        
        if(args.length < 2) {
            
            throw new IllegalArgumentException("Illegal number of arguments");
            
        } else if(args[0].charAt(0) == '-') {
            
            for(int i = 1; i < args[0].length(); i++) {
                
                switch(args[0].charAt(i)) {
                    
                    case 'f':
                        readFile = true;
                        break;
                    case 'i':
                        interactive = true;
                        break;
                    case 'g':
                        grep = true;
                        break;
                }
            }
            
        } else {
            
            eval.loadRegex(args[0]);
            
            for(int i = 1; i < args.length; i++) {
                System.out.println(eval.evaluateString(args[i]));
            }
            
            return;
        }
        
        
        eval.loadRegex(regex);
        
        if(readFile || grep) {
            
            try {
                file = new FileReader(args[args.length - 1]);
            } catch(Exception e) {
                System.out.println("Invalid file");
                return;
            }
            
            scanner = new Scanner(file);
            
            if(readFile) {
                scanner.useDelimiter("\\Z");
                
                input = scanner.next();

                System.out.println(eval.evaluateString(input));

                return;
                
            } else if(grep) {
                
                while(scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    
                    if(eval.evaluateString(line))
                        System.out.println(line);
                }
                
                return;
            }
            
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
