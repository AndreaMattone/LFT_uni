public class NumberTok extends Token {
	// ... completare ...
	/*
	sarà come la classe word ma al posto della stringa metto un int (lexeme)
	quando leggo i numeri ad es se leggo 1
	poi leggo 2 quindi devo fare 1*10 + 2 ...

	e poi restituisco un un numero e il tag

	*/
 	public int int_const; //costante numerica che voglio rappresentare
    public NumberTok(int tag, int n) { super(tag); int_const=n; }
    public String toString() { 
    	return "<" + tag + ", " + int_const + ">"; 
    }
  
}