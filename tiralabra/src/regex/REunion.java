
package regex;


public class REunion implements REsubexpBinary{
    
    private REsubexp left;
    private REsubexp right;
    
    public REunion(REsubexp left, REsubexp right) {
        this.left = left;
        this.right = right;
    }
    
    public REsubexp getLeft() {
        return left;
    }
    
    public REsubexp getRight() {
        return right;
    }
    
}
