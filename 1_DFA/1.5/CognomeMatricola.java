public class CognomeMatricola{


	public static boolean scan(String s){

		int state=0;
		int i=0;

		while(state>=0 && i<s.length()){
			char c = s.charAt(i);
			switch(state){

				case 0:
					if(c>='A'&&c<='K'){
						state=1;
					}else{
						if(c>='L'&&c<='Z'){
							state=2;
						}else{
							state=-1;
						}
					}
				 break;

				case 1:
					if(c>='a'&&c<='z'){
						state=1;
					}else{
						if(c=='0'||c=='2'||c=='4'||c=='6'||c=='8'){
							state=3;
						}else{
							if(c=='1'||c=='3'||c=='5'||c=='7'||c=='9'){
								state=4;
							}else{
								state=-1;
							}
						}
					}
				 break;

				case 2:
					if(c>='a'&&c<='z'){
						state=2;
					}else{
						if(c=='0'||c=='2'||c=='4'||c=='6'||c=='8'){
							state=5;
						}else{
							if(c=='1'||c=='3'||c=='5'||c=='7'||c=='9'){
								state=6;
							}else{
								state=-1;
							}
						}
					}
				 break;

				case 3:
					if(c=='0'||c=='2'||c=='4'||c=='6'||c=='8'){
						state=3;
					}else{
						if(c=='1'||c=='3'||c=='5'||c=='7'||c=='9'){
							state=4;
						}else{
							state=-1;
						}
					}
				 break;

				case 4:
					if(c=='0'||c=='2'||c=='4'||c=='6'||c=='8'){
						state=3;
					}else{
						if(c=='1'||c=='3'||c=='5'||c=='7'||c=='9'){
							state=4;
						}else{
							state=-1;
						}
					}
				 break;

				case 5:
					if(c=='0'||c=='2'||c=='4'||c=='6'||c=='8'){
						state=5;
					}else{
						if(c=='1'||c=='3'||c=='5'||c=='7'||c=='9'){
							state=6;
						}else{
							state=-1;
						}
					}
				 break;

				case 6:
					if(c=='0'||c=='2'||c=='4'||c=='6'||c=='8'){
						state=5;
					}else{
						if(c=='1'||c=='3'||c=='5'||c=='7'||c=='9'){
							state=6;
						}else{
							state=-1;
						}
					}
				 break;
			}
			i++;
		}
		return (state==3) || (state==6);
	}


	public static void main(String args[]){
		System.out.println("ATTESO: OK");
		System.out.println(scan("Bianchi123456") ? "OK" : "NOPE");
		System.out.println(scan("Bianchi12") ? "OK" : "NOPE");
		System.out.println(scan("B123456") ? "OK" : "NOPE");
		System.out.println(scan("Bianchi2") ? "OK" : "NOPE");
		System.out.println(scan("Rossi654321") ? "OK" : "NOPE");


		System.out.println("ATTESO: NOPE");
		System.out.println(scan("Bianchi654321") ? "OK" : "NOPE");
		System.out.println(scan("Rossi123456") ? "OK" : "NOPE");
		System.out.println(scan("bianchi123456") ? "OK" : "NOPE"); //devono iniziare con la maiuscola
		System.out.println(scan("BiaNChi123456") ? "OK" : "NOPE"); //case sensitive

	}
}