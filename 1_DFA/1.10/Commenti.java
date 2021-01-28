public class Commenti{
	public static boolean scan(String s){
		int state=0;
		int i=0;

		while(state>=0 && i<s.length()){
			char c = s.charAt(i);
			switch(state){
				case 0:
					if(c=='/'){
						state=1;
					}else{
						state=-1;
					}
				 break;

				case 1:
					if(c=='*'){
						state=2;
					}else{
						state=-1;
					}
				 break;

				case 2:
					if(c=='a' || c=='/'){
						state=2;
					}else{
						if(c=='*'){
							state=3;
						}else{
							state=-1;
						}
					}
				 break;

				case 3:
					if(c=='/'){
						state=4;
					}else{
						if(c=='a'){
							state=2;
						}else{
							if(c=='*'){
								state=3;
							}else{
								state=-1;
							}
						}
					}
				 break;

				case 4:
					if(c!='\0'){
						state=-1;
					}
				 break;
			}
			i++;
		}
		return state==4;
	}




	public static void main(String args[]){
		System.out.println("Atteso: OK");
		System.out.println(scan("/****/")  ? "OK" : "NOPE");
		System.out.println(scan("/*a*a*/")  ? "OK" : "NOPE");
		System.out.println(scan("/*a/**/")  ? "OK" : "NOPE");
		System.out.println(scan("/**a///a/a**/")  ? "OK" : "NOPE");
		System.out.println(scan("/**/")  ? "OK" : "NOPE");
		System.out.println(scan("/*/*/")  ? "OK" : "NOPE");

		System.out.println("Atteso: NOPE");
		System.out.println(scan("/*/")  ? "OK" : "NOPE");
		System.out.println(scan("/**/***/")  ? "OK" : "NOPE");
	}
}