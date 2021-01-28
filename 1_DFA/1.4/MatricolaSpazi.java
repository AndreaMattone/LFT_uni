public class MatricolaSpazi{
//accetta il linguaggio di stringhe che contengono 
//un numero di matricola seguito SUBITO dopo da un Cognome
//La combinazione tra matricola e cognome deve seguire le seguenti regole:
// Numero di matricola dispari -> iniziale cognome L...Z
// Numero di matricola pari    -> iniziale cognome A...K

	public static boolean scan(String s){
		int state = 0;
		int i = 0;

		while(state >= 0 && i<s.length()){
			char c = s.charAt(i);

			switch(state){

				case 0: 
					if(c==' '){
						state=0;
					}else{
						if(c=='0' || c=='2' || c=='4' || c=='6' || c=='8'){
							state=1;
						}else{
							if(c=='1' || c=='3' || c=='5' || c=='7' || c=='9'){
								state=2;
							}else{
								state=-1;
							}
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
							if(c>='A'&&c<='K'){ 
								state=3;
							}else{
								if(c==' '){
									state=4;
								}else{
									state=-1;
								}
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
							if(c>='L'&&c<='Z'){
								state=3;
							}else{
								if(c==' '){
									state=5;
								}else{
									state=-1;
								}
							}
						}
					}
				 break;

				case 3:
					if((c>='a'&&c<='z')){ //aggiungere A&&Z se voglio riconoscere GaSpErI
						state=3;
					}else{
						if(c==' '){
							state=6;
						}else{
							state=-1;
						}
					}
				 break;

				case 4: 
					if(c==' '){
						state=4;
					}else{
						if(c>='A'&&c<='K'){
							state=3;
						}else{
							state=-1;
						}
					}
				 break;

				case 5:
					if(c==' '){
						state=5;
					}else{
						if(c>='L'&&c<='Z'){
							state=3;
						}else{
							state=-1;
						}
					}
				 break;

				 case 6:
					if(c==' '){
						state=6;
					}else{
						if(c>='A'&&c<='Z'){
							state=3;
						}else{
							state=-1;
						}
					}
				 break;

			}
			i++;
		}
		return state==3 || state==6;
	}



	public static void main(String[] args){

		System.out.println("Atteso: OK");
		System.out.println(scan("123456 Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("654321 Rossi") ? "OK" : "NOPE");
		System.out.println(scan("123456De Gasperi") ? "OK" : "NOPE");
		System.out.println(scan("123456De Gasp Eri") ? "OK" : "NOPE");


		System.out.println("Atteso: NOPE");
		System.out.println(scan("1234 56Bianchi") ? "OK" : "NOPE");
		System.out.println(scan("123456De gasperi") ? "OK" : "NOPE");
		System.out.println(scan("123456de gasperi") ? "OK" : "NOPE");
		System.out.println(scan("123456De GasPEri") ? "OK" : "NOPE");
		

		
	}
}