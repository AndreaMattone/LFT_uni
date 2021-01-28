import java.io.*; 
import java.util.*;

public class Lexer {

    public static int line = 1; //inizio dalla prima linea del file
    private char peek = ' ';    //carattere letto, inizializzato a carattere vuoto
    
    private void readch(BufferedReader br) {//leggo il carattere successivo
        try {
            peek = (char) br.read();//metto in peek il carattere letto
        } catch (IOException exc) {
            peek = (char) -1; //ERROR
        }
    }

    public Token lexical_scan(BufferedReader br) { //genera il singolo token

        while (peek == ' ' || peek == '\t' || peek == '\n'  || peek == '\r') {//il lexer salta gli spazi vuoti e gli a capo fine riga 
            if (peek == '\n') line++;
            readch(br); // se trovo uno spazio o un carattere vuoto lo salto leggendo il carattere successivo
        }
        
        switch (peek) {

            /**
            ######### CASI IN CUI RICONOSCO UN TOKEN di Token.java ###########
                not   = '!' --------OK
                lpt   = '(' --------OK
                rpt   = ')' --------OK
                lpg   = '{' -------OK
                rpg   = '}' -------OK
                plus  = '+' -------OK
                minus = '-' -------OK
                mult  = '*' -------OK
                div   = '/' -------OK
                semicolon = ';' ---OK
            */
            case '!':
                peek = ' ';//"resetto" peek a ' ' così quando entro nel while sopra legge il prossimo carattere
                return Token.not;

            case '(':
                peek = ' ';
                return Token.lpt;

            case ')':
                peek = ' ';
                return Token.rpt;

            case '{':
                peek = ' ';
                return Token.lpg;

            case '}':
                peek = ' ';
                return Token.rpg;

            case '+':
                peek = ' ';
                return Token.plus;

            case '-':
                peek = ' ';
                return Token.minus;

            case '*':
                peek = ' ';
                return Token.mult;

            case ';':
                peek = ' ';
                return Token.semicolon;



            /**
                2.3 RICONOSCERE I COMMENTI ED IGNORARLI

                i commenti sono di due tipi:

                // e terminano con a capo o EOF
                /* * /

                quindi inglobo il caso
                case '/':
                peek = ' ';
                return Token.div;
                in questa parte di lexer

            */
            case '/':
                readch(br);
                if(peek=='/'){ //ho riconosciuto il caso dei commenti //
                    while( (peek != (char)-1 ) && peek!='\n'){ 
                        readch(br);
                        //finchè il carattere è diverso da a capo o EOF ignoro quello che trovo
                    }
                    peek = ' ';
                    return lexical_scan(br);// ho igonorato il commento e passo a riconoscere il prossimo token
                }else{
                    if(peek=='*'){ //ho riconosciuto il caso dei commenti /* */
                        readch(br);
                        boolean fine_commento = false;
                        while(fine_commento==false && (peek!=(char)-1) ){
                            if(peek=='*'){
                                readch(br);
                                if(peek=='/'){
                                    fine_commento=true; //se trovo la sequenza */ il commento è stato chiuso
                                }
                            }else{
                                readch(br);
                            }
                        }
                        if(fine_commento==false){
                            System.out.println("Error: commento non chiuso\n");
                            return null;
                        }
                        peek = ' ';
                        return lexical_scan(br);// ho igonorato il commento e passo a riconoscere il prossimo token
                    }else{
                        //peek = ' ';
                        return Token.div;
                    }
                }
            /**
            ########### CASI IN CUI RICONOSCO UNA WORD COMPOSTA DA SIMBOLI #################
            Gestisco i seguenti casi
                and      = "&&"
                or       = "||"    
                lt       = "<"
                gt       = ">"
                caso uguale ('=') -> Token.assign
                eq       = "=="   -> Word.eq;
                le       = "<="
                ne       = "<>"
                ge       = ">="
            */
            case '&':
                readch(br);
                if (peek == '&') {
                    peek = ' ';
                    return Word.and;
                } else {
                    System.err.println("Erroneous character" + " after & : "  + peek );
                    return null;
                }

            case '|':
                readch(br);
                if (peek == '|') {
                    peek = ' ';
                    return Word.or;
                } else {
                    System.err.println("Erroneous character" + " after | : "  + peek );
                    return null;
                }

            //devo riconoscere <, <= o <>
            case '<':      //se riconosco '<' ho 3 casi possibili da riconoscere
                readch(br);//leggo il carattere successivo per sapere in quale dei casi ricado
                if (peek == '=') {   //caso <=
                    peek = ' ';
                    return Word.le;
                } else {
                    if(peek == '>'){ //caso <>
                        peek = ' ';
                        return Word.ne;
                    }else{
                        return Word.lt;
                    }
                }

            //devo riconoscere > o >=
            case '>':      //se riconosco '>' ho 2 casi possibili da riconoscere
                readch(br);//leggo il carattere successivo per sapere in quale dei casi ricado
                if (peek == '=') {   //caso >=
                    peek = ' ';
                    return Word.ge;
                } else {
                    return Word.gt;
                }
              
            case '=':      //se riconosco '=' ho 2 casi possibili da riconoscere
                readch(br);//leggo il carattere successivo per sapere in quale dei casi ricado
                if (peek == '=') {   //caso ==
                    peek = ' ';
                    return Word.eq;
                } else { //caso =
                    peek = ' ';
                    return Token.assign;
                }
              





            /**########### CASO PARTICOLARE DI FINE FILE ############*/
            case (char)-1:
                return new Token(Tag.EOF); //arrivo ad EndOfFile


            /**########## ANALIZZO SEMPRE LE LETTERE ED I NUMERI PER IDENTIFICARE LE WORD COMPOSTE DA LETTERE O I NUMERI ########### */ 
            default:
                if (Character.isLetter(peek) || peek=='_') { //caso in cui peek = 'lettera' potrei riconoscere delle Word string
                    /**
                    Gestisco i casi degli identificatori e delle Word composte da parole
                        cond     = "cond"    -> Word.cond  //le parole le ho fatte sotto in is.Letter
                        when     = "when"    -> Word.when 
                        then     = "then"    -> Word.then 
                        elsetok  = "else"    -> Word.elsetok 
                        whiletok = "while"   -> Word.whiletok 
                        dotok    = "do"        
                        seq      = "seq"
                        print    = "print"
                        read     = "read"
                    */

                    /**Mi salvo la stringa*/
                    String to_recognize = "";
                    to_recognize = to_recognize + peek; //inizializzo la stringa e ci metto dentro la lettera che ho appena riconosciuto
                    readch(br); //leggo la prossima lettera
                    while(Character.isLetter(peek) || Character.isDigit(peek) || peek=='_'){ //finchè trovo lettere continuo ad aggiungerle alla stringa, controllo anche i numeri perchè gli "Identificatore" è una lettera seguita da lettere o numeri
                        to_recognize = to_recognize + peek;
                        readch(br);
                    }


                    /**Controlli*/
                    if(to_recognize.equals("cond")){
                        return Word.cond;
                    }
                    else if(to_recognize.equals("when")){
                        return Word.when;
                    }
                    else if(to_recognize.equals("then")){
                        return Word.then;
                    }
                    else if(to_recognize.equals("else")){
                        return Word.elsetok;
                    }
                    else if(to_recognize.equals("while")){
                        return Word.whiletok;
                    }
                    else if(to_recognize.equals("do")){
                        return Word.dotok;
                    }
                    else if(to_recognize.equals("seq")){
                        return Word.seq;
                    }
                    else if(to_recognize.equals("print")){
                        return Word.print;
                    }
                    else if(to_recognize.equals("read")){
                        return Word.read;
                    }else{
                        /**Se sono arrivato qua non ho riconosciuto nessuna delle Word quindi la parola è una nuova Word, è un identificatore*/
                        //Word ident = new Word(Tag.ID, to_recognize);
                        //return ident;
                        /**
                        2.2
                        Nuova definizione di identificatore: è una sequenza non vuota di lettere numeri ed il simbolo "_"
                        la sequenza non comincia con un numero e non puo essere composta solo da _

                        Sono gia sicuro che non inizi con un numero perchè altrimenti sarei andato nel campo "isDigit"
                        */
                        boolean accepted = false;
                        for(int i=0;i<to_recognize.length();i++){ /**scorro tutta la stringa*/
                            if(to_recognize.charAt(i)!= '_'){
                                accepted=true;
                            }
                        }
                        if(accepted==true){
                            Word ident = new Word(Tag.ID, to_recognize);
                            return ident;
                        }else{ /**La stringa è composta solo da underscore*/
                            System.err.println("Errore nella definizione di Identificatore,trovata stringa composta solo da underscore: " + to_recognize);
                            return null;
                        }

                        
                    }


                /**
                Gestisco i casi delle costanti numeriche attraverso la classe NumberTok
                */
                } else if (Character.isDigit(peek)) {
	               
                    /**Dato che so di aver riconosciuto un numero trasformo il carattere in un intero e leggo il resto del numero*/
                    int const_int = Character.getNumericValue(peek);
                    readch(br);
                    while(Character.isDigit(peek)){ //controllo solo numeri perchè è una costante numerica
                        const_int = const_int*10 + Character.getNumericValue(peek); 
                        readch(br);
                    }
                    NumberTok const_int_tok = new NumberTok(Tag.NUM, const_int);
                    return const_int_tok;

                    /*
                    ## OLD ## FATTO CON LE STRINGHE senza NumberTok
                        String to_recognize = "";
                        to_recognize = to_recognize + peek; //inizializzo la stringa e ci metto dentro la lettera che ho appena riconosciuto
                        readch(br); //leggo il prossimo numero
                        while(Character.isDigit(peek)){ //controllo solo numeri perchè è una costante numerica
                            to_recognize = to_recognize + peek;
                            readch(br);
                        }
                        Word num_const = new Word(Tag.NUM, to_recognize); //creo una nuova word per la costante numerica
                        return num_const;
                    */
                } else {
                        System.err.println("Erroneous character: " + peek );
                        return null;
                }

         }

    }
		
    public static void main(String[] args) {
        Lexer lex = new Lexer();
        String path = "...path..."; // il percorso del file da leggere
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            Token tok;
            do {
                tok = lex.lexical_scan(br);
                System.out.println("Scan: " + tok);
            } while (tok.tag != Tag.EOF);
            br.close();
        } catch (IOException e) {e.printStackTrace();}    
    }

}