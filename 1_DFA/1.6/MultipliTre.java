public class MultipliTre{
public static boolean scan(String s){
		int state=0;
		int i=0;

		while(state>=0 && i<s.length()){
			char c = s.charAt(i);
			switch(state){
				case 0:
					if(c=='0'){
						state=1;
					}else{
						if(c=='1'){
							state=2;
						}else{
							state=-1;
						}
					}
				 break;

				case 1:
					if(c=='0'){
						state=1;
					}else{
						if(c=='1'){
							state=2;
						}else{
							state=-1;
						}
					}
				 break;

				case 2:
					if(c=='1'){
						state=1;
					}else{
						if(c=='0'){
							state=3;
						}else{
							state=-1;
						}
					}
				 break;

				case 3:
					if(c=='0'){
						state=2;
					}else{
						if(c=='1'){
							state=3;
						}else{
							state=-1;
						}
					}
				 break;
			}
			i++;
		}
		return state==1;
	}



	public static void main(String args[]){
		System.out.println("Atteso: OK");
		System.out.println(scan("110")  ? "OK" : "NOPE");
		System.out.println(scan("1001")  ? "OK" : "NOPE");
		System.out.println(scan("0")  ? "OK" : "NOPE");
		System.out.println(scan("0110")  ? "OK" : "NOPE");
		System.out.println(scan("0001111")  ? "OK" : "NOPE");
		

		System.out.println("Atteso: NOPE");
		System.out.println(scan("10")  ? "OK" : "NOPE");
		System.out.println(scan("111")  ? "OK" : "NOPE");

	}
}