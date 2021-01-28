import java.io.*;

public class Parser {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;

    public Parser(Lexer l, BufferedReader br) {
        lex = l;
        pbr = br;
        move();
    }

    void move() {
        look = lex.lexical_scan(pbr);
        System.out.println("token = " + look);
    }

    void error(String s) {
	throw new Error("near line " + lex.line + ": " + s);
    }

    void match(int t) {
	if (look.tag == t) {
	    if (look.tag != Tag.EOF) move();
	} else error("syntax error");
    }

    public void start() {
        if(look==Token.lpt || look.tag==Tag.NUM){
            expr();
            match(Tag.EOF);
        }else{
            error("Error in start\n");
        }
    }

    private void expr() {
        if(look==Token.lpt || look.tag==Tag.NUM){
            term();
            exprp();
        }else{
            error("Error in expr\n");
        }
        
    }

    private void exprp() {
        switch(look.tag){
            case '+':
                match(Token.plus.tag);
                term();
                exprp();
            break;
            case '-':
                match(Token.minus.tag);
                term();
                exprp();
            break;

            
            case ')':
            //break; 

            case Tag.EOF:
            break;

            default:
                error("Error in exprp\n");
        }
    }

    private void term() {
        if(look==Token.lpt || look.tag == Tag.NUM){
            fact();
            termp();
        }else{
            error("Error in term\n");
        }
    }

    private void termp() {
        switch(look.tag){
            case '*':
                match(Token.mult.tag);
                fact();
                termp();
            break;
        
            case '/':
                match(Token.div.tag);
                fact();
                termp();
            break;

            case '+':
            //break;

            case '-':
            //break; 

            case ')':
            //break; 

            case Tag.EOF:
            break;

            default:
                error("Error in termp\n");

        }
    }

    private void fact() {
        switch(look.tag){
            case '(':
                match(Token.lpt.tag);
                expr();
                match(Token.rpt.tag);
            break;

            case Tag.NUM:
                match(Tag.NUM);
            break;

            default:
                error("Error in fact\n");
        }
    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "file.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.start();
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}