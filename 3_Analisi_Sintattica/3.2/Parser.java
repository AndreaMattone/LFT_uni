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



    public void prog() { //cambiare qua e nel main il simbolo di start
        if(look==Token.lpt){
            stat();
            match(Tag.EOF);
        }else{
            error("Error in prog\n");
        }
    }

    public void statlist() { 
        if(look==Token.lpt){
            stat();
            statlistp();
        }else{
            error("Error in statlist\n");
        }
    }

    public void statlistp() { 
        switch(look.tag){
            case '(':
                stat();
                statlistp();
            break;

            case ')':
            	//epsilon
            break;

            default:
                error("Error in statlistp\n");
        }
    }

    public void stat() { 
        if(look==Token.lpt){
            match(Token.lpt.tag);
            statp();
            match(Token.rpt.tag);
        }else{
            error("Error in stat\n");
        }
    }

    public void statp() { 
        switch(look.tag){
            case '=':
                match(Token.assign.tag);
                match(Tag.ID);
                expr();
            break;

            case Tag.COND:
                match(Tag.COND);
                bexpr();
                stat();
                elseopt();
            break;

            case Tag.WHILE:
                match(Tag.WHILE);
                bexpr();
                stat();
            break;

            case Tag.DO:
                match(Tag.DO);
                statlist();
            break;

            case Tag.PRINT:
                match(Tag.PRINT);
                exprlist();
            break;

            case Tag.READ:
                match(Tag.READ);
                match(Tag.ID);
            break;

            default:
                error("Error in statp\n");
        }
    }

    public void elseopt() { 
        switch(look.tag){
            case '(':
                match(Token.lpt.tag);
                match(Tag.ELSE);
                stat();
                match(Token.rpt.tag);
            break;

            case ')':
            	//epsilon
            break;

            default:
                error("Error in elseopt\n");
        }
    }

    public void bexpr() { 
        if(look==Token.lpt){
            match(Token.lpt.tag);
            bexprp();
            match(Token.rpt.tag);
        }else{
            error("Error in bexpr\n");
        }
    }

    public void bexprp() { 
        switch(look.tag){
            case Tag.RELOP:
                match(Tag.RELOP);
                expr();
                expr();
            break;

            default:
                error("Error in bexprp\n");
        }
    }


    public void expr() { 
        switch(look.tag){
            case Tag.NUM:
                match(Tag.NUM);
            break;

            case Tag.ID:
                match(Tag.ID);
            break;

            case '(':
                match(Token.lpt.tag);
                exprp();
                match(Token.rpt.tag);
            break;

            default:
                error("Error in expr\n");
        }
    }


    public void exprp() { 
        switch(look.tag){
            case '+':
                match(Token.plus.tag);
                exprlist();
            break;

            case '-':
                match(Token.minus.tag);
                expr();
                expr();
            break;

            case '*':
                match(Token.mult.tag);
                exprlist();
            break;

            case '/':
                match(Token.div.tag);
                expr();
                expr();
            break;

            default:
                error("Error in exprp\n");
        }
    }


    public void exprlistp() { 
        switch(look.tag){
            case Tag.NUM:
                expr();
                exprlistp();
            break;

            case Tag.ID:
                expr();
                exprlistp();
            break;

            case '(':
                expr();
                exprlistp();
            break;

            case ')':
            	//epsilon
            break;

            default:
                error("Error in exprlistp\n");
        }
    }


    public void exprlist() { 
        switch(look.tag){
            case Tag.NUM:
                expr();
                exprlistp();
            break;

            case Tag.ID:
                expr();
                exprlistp();
            break;

            case '(':
                expr();
                exprlistp();
            break;

            default:
                error("Error in exprlist\n");
        }
    }



		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "file.txt"; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Parser parser = new Parser(lex, br);
            parser.prog(); //ho modificato il simbolo iniziale da cui partire
            System.out.println("Input OK");
            br.close();
        } catch (IOException e) {e.printStackTrace();}
    }
}