public class Identificatori{
	//Automa che rinonosce gli identificatori
	//Stringhe non vuote composte da lettere, numeri e "_"
	//Non possono iniziare con numeri
	//Non possono contenere solamente "_"
	public static boolean scan(String s){
		int state = 0;
		int i = 0;

		while(state >= 0 && i < s.length()){
			char c = s.charAt(i);
			switch(state){

				case 0: 
					if(c=='_'){
						state=2;
					}else{
						if(Character.isLowerCase(c) || Character.isUpperCase(c)){
								state=1;
							}else{
								state=-1; //il carattere inserito non è nel linguaggio atteso
							}
							//risolvo anche il problema del Character.isDigit						
					}
				 break;

				case 1: 
					if(Character.isDigit(c) || Character.isLowerCase(c) || Character.isUpperCase(c) || c=='_'){
						state=1;
					}else{
						state=-1; //il carattere inserito non è nel linguaggio atteso
					}
					
				 break;

				case 2: 
					if(c=='_'){
						state=2;
					}else{
						if(Character.isLowerCase(c) || Character.isUpperCase(c) || Character.isDigit(c)){
							state=1;
						}else{
							state=-1; //il carattere inserito non è nel linguaggio atteso
						}
					}
				 break;
			}
			i++;
		}

		return state == 1;
	}



	public static void main(String[] args){
		
		System.out.println("Atteso: OK");
		System.out.println(scan("x") ? "OK" : "NOPE");
		System.out.println(scan("flag1") ? "OK" : "NOPE");
		System.out.println(scan("x2y2") ? "OK" : "NOPE");
		System.out.println(scan("x_1") ? "OK" : "NOPE");
		System.out.println(scan("lft_lab") ? "OK" : "NOPE");
		System.out.println(scan("_temp") ? "OK" : "NOPE");
		System.out.println(scan("x_1_y_2") ? "OK" : "NOPE");
		System.out.println(scan("x___") ? "OK" : "NOPE");

		System.out.println("Atteso: NOPE");
		System.out.println(scan("5") ? "OK" : "NOPE");
		System.out.println(scan("221B") ? "OK" : "NOPE");
		System.out.println(scan("123") ? "OK" : "NOPE");
		System.out.println(scan("9_to_5") ? "OK" : "NOPE");
		System.out.println(scan("___") ? "OK" : "NOPE");
		System.out.println(scan("x&y") ? "OK" : "NOPE");
		System.out.println(scan("&") ? "OK" : "NOPE");
		System.out.println(scan("") ? "OK" : "NOPE");


	}


}