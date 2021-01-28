public class TreA{

	public static boolean scan(String s){

		int state=0;
		int i = 0;

		while(state>=0 && i<s.length()){
			char c = s.charAt(i);
			switch(state){
				case 0:
					if(c=='a'){
						state=1;
					}else{
						if(c=='b'){
							state=2;
						}else{
							state=-1;
						}
					}
				 break;

				case 1:
					state=1;
				 break;

				case 2:
				
					if(c=='a'){
						state=1;
					}else{
						if(c=='b'){
							state=3;
						}else{
							state=-1;
						}
					}
				 break;

				case 3:   
					if(c=='a'){
						state=1;
					}else{
						state=-1;
					}
				 break;
			}
			i++;
		}



		return state==1;
	}

	public static void main(String args[]){

		System.out.println("Atteso: OK");
		System.out.println(scan("abb") ? "OK" : "NOPE");
		System.out.println(scan("abbbbbbbb") ? "OK" : "NOPE");
		System.out.println(scan("baaaaaaaa") ? "OK" : "NOPE");
		System.out.println(scan("aaaaaaaaa") ? "OK" : "NOPE");
		System.out.println(scan("a") ? "OK" : "NOPE");
		System.out.println(scan("ba") ? "OK" : "NOPE");
		System.out.println(scan("bba") ? "OK" : "NOPE");
		System.out.println(scan("bbabbbbbbbb") ? "OK" : "NOPE");
		System.out.println(scan("aa") ? "OK" : "NOPE");

		System.out.println("Atteso: NOPE");
		System.out.println(scan("bbbaaaba") ? "OK" : "NOPE");
		System.out.println(scan("bbbbaaaba") ? "OK" : "NOPE");
		System.out.println(scan("b") ? "OK" : "NOPE");

	}




}