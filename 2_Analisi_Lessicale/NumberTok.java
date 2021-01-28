public class NumberTok extends Token {
	/*
	sarà come la classe word ma al posto della stringa metto un int (lexeme)
	*/
 	public int int_const; //costante numerica che voglio rappresentare
    public NumberTok(int tag, int n) { 
    	super(tag); 
    	int_const=n; 
    }

    public String toString() { 
    	return "<" + tag + ", " + int_const + ">"; 
    }
  
}