import java.io.*;

public class Translator {
    private Lexer lex;
    private BufferedReader pbr;
    private Token look;
    
    SymbolTable st = new SymbolTable();
    CodeGenerator code = new CodeGenerator();
    int count=0;

    public Translator(Lexer l, BufferedReader br) {
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


    /*
    <prog> -> <stat> EOF
    guida(<prog> -> <stat> EOF) = {(}
    */
    public void prog() {        
        if(look==Token.lpt){
                int lnext_prog = code.newLabel(); /*Crea una nuova etichetta, ad esempio L0: */
                stat(lnext_prog);                 /*passo l'etichetta come attributo a stat, così saprò la posizione a cui "saltare"*/
                code.emitLabel(lnext_prog);       /*emetto l'etichetta dopo stat*/
                match(Tag.EOF);
            try {
            	code.toJasmin(); /*Chiamo toJasmin per creare il file Output.j*/
            }
            catch(java.io.IOException e) {
            	System.out.println("IO error\n");
            };
        }else{
            error("Error in prog\n");
        }
    }




    /*
    <statlist> -> <stat><statlistp>
    guida(<statlist> -> <stat><statlistp>) = {(}
    */
    public void statlist(int lnext){ /*lnext è l'etichetta a cui andare dopo aver eseguito l'istruzione*/
        if(look==Token.lpt){
            stat(lnext); /*passo ricorsivamente l'etichetta ai metodi, emetterano loro il tutto*/
            statlistp(lnext);
        }else{
            error("Error in statlist\n");
        }
    }




    /*
    <statlistp> -> <stat><statlistp>
    <statlistp> -> epsilon
    guida(<statlistp> -> <stat><statlistp>) = {(}
    guida(<statlistp> -> epsilon) = {)}
    */
    public void statlistp(int lnext) { 
        switch(look.tag){
            case '(':
                stat(lnext);
                statlistp(lnext);
            break;

            case ')':
                /*caso epsilon, non faccio nulla*/
            break;

            default:
                error("Error in statlistp\n");
        }
    }





    /*
    <stat> -> ( <statp> )
    guida(<stat> -> ( <statp> ) ) = {(}
    */
    public void stat(int lnext) { 
        if(look==Token.lpt){
            match(Token.lpt.tag);
            statp(lnext);
            match(Token.rpt.tag);
        }else{
            error("Error in stat\n");
        }
    }




    /*
    <statp> -> = ID <expr>
    <statp> -> cond <bexpr><stat><elseopt>
    <statp> -> while <bexpr><stat>
    <statp> -> do <statlist>
    <statp> -> print <exprlist>
    <statp> -> read ID
    guida(<statp> -> = ID <expr>) = {=}
    guida(<statp> -> cond <bexpr><stat><elseopt>) = {cond}
    guida(<statp> -> while <bexpr><stat>) = {while}
    guida(<statp> -> do <statlist>) = {do}
    guida(<statp> -> print <exprlist>) = {print}
    guida(<statp> -> read ID) = {read}

    */
    public void statp(int lnext) {
        switch(look.tag) {

            /*
            <statp> -> = ID <expr>
            istore ID
            (di un numero sulla pila)
            */
            case '=': match('=');
                if (look.tag==Tag.ID){
                    int read_id_addr = st.lookupAddress(((Word)look).lexeme); /*guardo se ho gia l'ID salvato altrimenti ne aggiungo uno*/
                    if (read_id_addr==-1) {
                        read_id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }
                    match(Tag.ID); /*riconosco ID*/
                    expr(); /*A questo punto metto sulla pila il numero/ID*/
                    code.emit(OpCode.istore,read_id_addr); /*Successivamente stampo istore ID*/
                }else{
                    error("Error in grammar (statp) after read with " + look);
                }
            break;

            /*
            <statp> -> cond <bexpr><stat><elseopt>
            //TODO
            */
            case Tag.COND:
                match(Tag.COND);
                {
                    int lblTrue = code.newLabel(); /*creo due nuove etichette che mi serviranno per saltare nei casi Vero e Falso*/
                    int lblFalse= code.newLabel();
                    int ln = code.newLabel();

                    bexpr(lblTrue,lblFalse); /*bexpr emetterà ricorsivamente il tutto*/


                    stat(lnext);
                    code.emit(OpCode.GOto,ln); /*qua c'è la goto alla lnext (ovvero la etichetta che salta il caso falso )*/
                    
                    code.emitLabel(lblFalse);     /*qua c'è l'etichetta che indica il punto dove inizia il caso falso*/
                    elseopt(lnext,ln);               /*istruzioni del caso falso*/
                    
                }
            break;

            /*
            <statp> -> while <bexpr><stat>
            */
            case Tag.WHILE:

                match(Tag.WHILE);
                {

                    int lblIf   = code.newLabel(); /*label che mi serve per la condizione del while*/
                    int lblTrue = code.newLabel(); /*se la condizione è verificata*/
                    int lblFalse= code.newLabel(); /*se la condizione non è verificata*/
                    
                    code.emitLabel(lblIf);
                    bexpr(lblTrue,lblFalse); /*vedo dove saltare*/

                    stat(lnext); /*se la condizione del while è verificata vengo qua (ho emesso l'etichetta true in bexpr (ricorisvamente in bexprp)*/
                    code.emit(OpCode.GOto,lblIf);/*Qua torno "su" ed eseguo di nuovo la condizione del while*/
                    
                    code.emitLabel(lblFalse); /*Finito il "ciclo" while posso saltare qua*/
               
                } 
            break;


            /*
            <statp> -> do <statlist>
            */
            case Tag.DO:
                match(Tag.DO);
                statlist(lnext);
            break;

            /*
            <statp> -> print <exprlist>
            */
            case Tag.PRINT:
                match(Tag.PRINT);
                exprlist(0); /*Metto sulla pila ciò di cui voglio fare la print*/
            break;

            /*
            <statp> -> read ID
            */
            case Tag.READ:
                match(Tag.READ);
                if(look.tag==Tag.ID){
                    int read_id_addr = st.lookupAddress(((Word)look).lexeme); /*Controllo se ho gia quell'"ID" */
                    if (read_id_addr==-1) {
                        read_id_addr = count;
                        st.insert(((Word)look).lexeme,count++);
                    }                    
                    match(Tag.ID);
                    code.emit(OpCode.invokestatic,0); /*Istruzione che chiama la read*/
                    code.emit(OpCode.istore,read_id_addr); /*Prende da tastiera ed inserisce nell'ID indicato cio che ho preso tramite la istore*/
                }else{
                    error("Error in grammar (statp) after read with " + look);
                }
            break;
        }
    }



    /*
    <elseopt> -> ( else <stat> ) 
    <elseopt> -> epsilon
    guida(<elseopt> -> ( else <stat> ) ) = {(}
    guida(<elseopt> -> epsilon) = {)};
    */
    public void elseopt(int lnext,int ln){
        switch(look.tag){
            case '(':
                match(Token.lpt.tag);
                match(Tag.ELSE);
                stat(lnext);

                code.emitLabel(ln);
                match(Token.rpt.tag);/*Il match lo faccio dopo perchè controllo solo che sintatticamente sia giusto*/
            break;

            case ')':
                code.emitLabel(ln);
                /*Caso epsilon, non faccio nulla*/
            break;

            default:
                error("Error in elseopt\n");
        }
    }



    /*
    <bexpr> -> ( <bexprp> )
    guida(<bexpr> -> ( <bexprp> ) ) = {(}
    E' la condizione di while o cond
    */
    public void bexpr(int lblTrue,int lblFalse) {
        if(look==Token.lpt){
            match(Token.lpt.tag);
            bexprp(lblTrue,lblFalse);
            match(Token.rpt.tag);
        }else{
            error("Error in bexpr\n");
        }
    }




    /*
    <bexprp> -> RELOP <expr><expr>
    guida(<bexprp> -> RELOP <expr><expr>) = {RELOP}

    if_icmp ...
    goto labelFalse
    lblTrue
        qua c'è <stat> di statp

    */
    public void bexprp(int lblTrue, int lblFalse){

        if(look.tag==Tag.RELOP){
            OpCode myOpcode = OpCode.if_icmpeq; /*Inizializzo l'opcode, metto if_icmpeq a caso potevo inizializzarlo in qualsiasi modo*/
            if(look instanceof Word){ /*Verifico che look sia una word e non una lexeme*/
                if( ((Word)look).lexeme == "=="){
                    myOpcode = OpCode.if_icmpeq;
                }else if( ((Word)look).lexeme == "<="){
                    myOpcode = OpCode.if_icmple;
                }else if( ((Word)look).lexeme == "<>"){
                    myOpcode = OpCode.if_icmpne;
                }else if( ((Word)look).lexeme == ">="){
                    myOpcode = OpCode.if_icmpge;
                }else if( ((Word)look).lexeme == "<"){
                    myOpcode = OpCode.if_icmplt;
                }else if( ((Word)look).lexeme == ">"){
                    myOpcode = OpCode.if_icmpgt;
                }
                /*switch(((Word)look).lexeme){
                        case "==" : opc = OpCode.if_icmpeq; break;
                        case "<=" : opc = OpCode.if_icmple; break;
                        case "<>" : opc = OpCode.if_icmpne; break;
                        case ">=" : opc = OpCode.if_icmpge; break;
                        case "<" :  opc = OpCode.if_icmplt; break;
                        case ">" :  opc = OpCode.if_icmpgt; break;
                }*/
            }else{/*se è un lexeme è errato*/
                error("Error in bexprp\n");
            }

            match(Tag.RELOP);

            expr(); /*Inserisco sulla pila i dati*/
            expr();

            code.emit(myOpcode,lblTrue); /*Caso condizione vera*/
            code.emit(OpCode.GOto,lblFalse); 
            code.emitLabel(lblTrue); /*Dopo lblTrue c'è <stat> (sono in statp cond)*/
        }else{
            error("Error in bexprp\n");
        }
    } 



    
    /*
    <expr> -> NUM
    <expr> -> ID
    <expr> -> ( <exprp> )
    guida(<expr> -> NUM) = {NUM}
    guida(<expr> -> ID) = {ID}
    guida(<expr> -> ( <exprp> )) = {(}
    */
    public void expr(){
        switch(look.tag){
            case Tag.NUM:
                code.emit(OpCode.ldc, ((NumberTok)look).int_const ); /*Inserisco il numero*/
                match(Tag.NUM);
            break;

            case Tag.ID:
                 int read_id_addr = st.lookupAddress(((Word)look).lexeme); /*Controllo se ho gia l'id*/
                        if (read_id_addr==-1) {
                            read_id_addr = count;
                            st.insert(((Word)look).lexeme,count++);
                }
                match(Tag.ID);
                code.emit(OpCode.iload,read_id_addr); /*Faccio la iload di quell'ID*/
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


    /*
    <exprp> -> -<expr><expr>
    <exprp> -> /<expr><expr>
    <exprp> -> +<exprlist>
    <exprp> -> *<exprlist>
    guida(<exprp> -> -<expr><expr>) = {-}
    guida(<exprp> -> /<expr><expr>) = {/}
    guida(<exprp> -> +<exprlist>) = {+}
    guida(<exprp> -> *<exprlist>) = {*}
    */
    private void exprp() {
        switch(look.tag) {
            case '-':
                match('-');
                expr();
                expr();
                code.emit(OpCode.isub);
                break;
            case '/':
                match('/');
                expr();
                expr();
                code.emit(OpCode.idiv);
            break;
            case '+':
                match('+');
                exprlist(1);
                //code.emit(OpCode.iadd);
            break;
            case '*':
                match('*');
                exprlist(2);
                //code.emit(OpCode.imul);
            break;

	        default:
                System.out.println("Error in exprp\n");
        }
    }



    /*
    <exprlist> -> <expr><exprlistp>
    guida(<exprlist> -> <expr><exprlistp>) = {NUM,ID,(}
    */
    public void exprlist(int i) {
        switch(look.tag){
            case Tag.NUM:
                expr();
                if(i==0){
                    code.emit(OpCode.invokestatic,1); /*Invocazione della print*/
                }
                exprlistp(i);
            break;

            case Tag.ID:
                expr();
                if(i==0){
                    code.emit(OpCode.invokestatic,1); /*Invocazione della print*/
                }
                exprlistp(i);
            break;

            case '(':
                expr();
                if(i==0){
                    code.emit(OpCode.invokestatic,1); /*Invocazione della print*/
                }
                exprlistp(i);
            break;

            default:
                error("Error in exprlist\n");
        }
    }



    /*
    <exprlistp> -> <expr><exprlistp>
    <exprlistp> -> epsilon
    guida(<exprlistp> -> <expr><exprlistp>) = {NUM,ID,(}
    guida(<exprlistp> -> epsilon) = {)}
    */
    public void exprlistp(int i) {
        switch(look.tag){
            case Tag.NUM:
                expr();
                if(i==0){
                    code.emit(OpCode.invokestatic,1); /*Invocazione della print*/
                }else if(i==1){
                    code.emit(OpCode.iadd);
                }else{
                    code.emit(OpCode.imul);
                }
                exprlistp(i);
            break;

            case Tag.ID:
                expr();
                if(i==0){
                    code.emit(OpCode.invokestatic,1); /*Invocazione della print*/
                }else if(i==1){
                    code.emit(OpCode.iadd);
                }else{
                    code.emit(OpCode.imul);
                }
                exprlistp(i);
            break;

            case '(':
                expr();
                if(i==0){
                    code.emit(OpCode.invokestatic,1); /*Invocazione della print*/
                }else if(i==1){
                    code.emit(OpCode.iadd);
                }else{
                    code.emit(OpCode.imul);
                }
                exprlistp(i);
            break;

            case ')':
                /*epsilon*/
            break;

            default:
                error("Error in exprlistp\n");
        }

    }


    public static void main(String[] args) {
            Lexer lex = new Lexer();
            String path = "file.lft"; // il percorso del file da leggere
            try {
                BufferedReader br = new BufferedReader(new FileReader(path));
                Translator translator = new Translator(lex, br);
                translator.prog(); //ho modificato il simbolo iniziale da cui partire
                br.close();
            } catch (IOException e) {e.printStackTrace();}
    }
}


/*
il CodeGenerator crea l'output.j

javac Translator.java
java Translator
java -jar jasmin.jar Output.j
java Output
*/