public class Matricola{
//accetta il linguaggio di stringhe che contengono 
//un numero di matricola seguito SUBITO dopo da un Cognome
//La combinazione tra matricola e cognome deve seguire le seguenti regole:
// Numero di matricola dispari -> iniziale cognome L...Z
// Numero di matricola pari    -> iniziale cognome A...K
//QUA SI PUO TOGLIERE LO STATO 3 (q3) e mettere semplicemente -1 come stringa non accettata ##############
//NON è case sensitive, accetta 123456BiaNChi


	public static boolean scan(String s){
		int state = 0;
		int i = 0;

		while(state >= 0 && i<s.length()){
			char c = s.charAt(i);

			switch(state){

				case 0: 
					if(c=='0' || c=='2' || c=='4' || c=='6' || c=='8'){ //c MOD 2 == 0
						state=1;
					}else{
						if(c=='1' || c=='3' || c=='5' || c=='7' || c=='9'){ // c MOD 2 != 0
							state=2;
						}else{
							state=-1;
						}
					}
				 break;

				case 1:
					if(c=='0' || c=='2' || c=='4' || c=='6' || c=='8'){
						state=1;
					}else{
						if(c=='1' || c=='3' || c=='5' || c=='7' || c=='9'){
							state=2;
						}else{
							if((c>='A'&&c<='K') || (c>='a'&&c<='k')){ 
								state=3;
							}else{
								state=-1;
							}
						}
					}
				 break;

				case 2: 
					if(c=='1' || c=='3' || c=='5' || c=='7' || c=='9'){
						state=2;
					}else{
						if(c=='0' || c=='2' || c=='4' || c=='6' || c=='8'){
							state=1;
						}else{
							if((c>='L'&&c<='Z') || (c>='l'&&c<='z')){
								state=3;
							}else{
								state=-1;
							}
						}
					}
				 break;

				case 3:
					if((c>='A'&&c<='Z') || (c>='a'&&c<='z')){
						state=3;
					}else{
						state=-1;
					}
				 break;

			}
			i++;
		}

		return state==3;
	}



	public static void main(String[] args){

		System.out.println("Atteso: OK");
		System.out.println(scan("123456Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("123456BiaNchi") ? "OK" : "NOPE");
		System.out.println(scan("654321Rossi") ? "OK" : "NOPE");
		System.out.println(scan("2Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("122B") ? "OK" : "NOPE");
		System.out.println(scan("2B") ? "OK" : "NOPE");

		System.out.println("Atteso: NOPE");
		System.out.println(scan("654321Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("123456Rossi") ? "OK" : "NOPE");
		System.out.println(scan("654322") ? "OK" : "NOPE");
		System.out.println(scan("Rossi") ? "OK" : "NOPE");
	}
}