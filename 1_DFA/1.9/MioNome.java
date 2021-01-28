public class MioNome{
	public static boolean scan(String s){
		int state=0;
		int i=0;

		while(state>=0 && i<s.length()){
			char c = s.charAt(i);
			switch(state){
				case 0:
					if(c=='A'){
						state=1;
					}else{
						state=7;
					}
				 break;

				case 1:
					if(c=='n'){
						state=2;
					}else{
						state=8;
					}
				 break;

				case 2:
					if(c=='d'){
						state=3;
					}else{
						state=9;
					}
				 break;

				case 3:
					if(c=='r'){
						state=4;
					}else{
						state=10;
					}
				 break;

				case 4:
					if(c=='e'){
						state=5;
					}else{
						state=11;
					}
				 break;

				case 5:
					if(c!='\0'){ //arrivato a questo punto accetto ogni carattere quindi mi basta verificare che esista un carattere, ovvero se non sono arrivato a fine stringa
						state=6;
					}else{
						state=-1;
					}
				 break;

				case 6:
					state=-1; //perchè se arrivo qua vuol dire che ho letto un carattere dopo il mio nome e quindi non accetto la stringa
				 break;

				case 7:
					if(c=='n'){
						state=8;
					}else{
						state=-1;
					}
				 break;

				case 8:
					if(c=='d'){
						state=9;
					}else{
						state=-1;
					}
				 break;

				case 9:
					if(c=='r'){
						state=10;
					}else{
						state=-1;
					}
				 break;

				case 10:
					if(c=='e'){
						state=11;
					}else{
						state=-1;
					}
				 break;

				case 11:
					if(c=='a'){
						state=6;
					}else{
						state=-1;
					}
				 break;
			}
			i++;
		}
		return state==6;
	}
	public static void main(String args[]){
		System.out.println("Atteso: OK");
		System.out.println(scan("Andrea")  ? "OK" : "NOPE");
		System.out.println(scan("A*drea")  ? "OK" : "NOPE");
		System.out.println(scan("Andr*a")  ? "OK" : "NOPE");
		System.out.println(scan("%ndrea")  ? "OK" : "NOPE");
		System.out.println(scan("Andre7")  ? "OK" : "NOPE");
		System.out.println(scan("andrea")  ? "OK" : "NOPE");

		System.out.println("Atteso: NOPE");
		System.out.println(scan("A*dr%a")  ? "OK" : "NOPE");
		System.out.println(scan("%*drea")  ? "OK" : "NOPE");
		System.out.println(scan("Pietro")  ? "OK" : "NOPE");
		System.out.println(scan("Eva")  ? "OK" : "NOPE");
		System.out.println(scan("And")  ? "OK" : "NOPE");
		System.out.println(scan("Andreab")  ? "OK" : "NOPE");





	}
	
}