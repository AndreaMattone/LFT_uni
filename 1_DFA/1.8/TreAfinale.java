 public class TreAfinale{

 	public static boolean scan(String s){
 		int state = 0;
 		int i = 0;

 		while(state>=0 && i<s.length()){

 			char c = s.charAt(i);

 			switch(state){
 				case 0:
 					if(c=='a'){
 						state=1;
 					}else{
 						if(c=='b'){
 							state=4;
 						}else{
 							state=-1;
 						}
 					}
 				 break;

 				case 1:
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

 				case 4:
 					if(c=='a'){
 						state=1;
 					}else{
 						if(c=='b'){
 							state=4;
 						}else{
 							state=-1;
 						}
 					}
 				 break;
 			}
 			i++;
 		}

 		return (state==1) || (state==2) || (state==3);

 	}


 	public static void main(String args[]){

 		System.out.println("Atteso: OK");
 		System.out.println(scan("abb") ? "OK" : "NOPE");
 		System.out.println(scan("abbaba") ? "OK" : "NOPE");
 		System.out.println(scan("baaaaaaaa") ? "OK" : "NOPE");
 		System.out.println(scan("aaaaaaaaa") ? "OK" : "NOPE");
 		System.out.println(scan("a") ? "OK" : "NOPE");
 		System.out.println(scan("ba") ? "OK" : "NOPE");
 		System.out.println(scan("bba") ? "OK" : "NOPE");
 		System.out.println(scan("aa") ? "OK" : "NOPE");
 		System.out.println(scan("bbbababab") ? "OK" : "NOPE");

 		System.out.println("Atteso:NOPE");
 		System.out.println(scan("abbbbbb") ? "OK" : "NOPE");
 		System.out.println(scan("bbabbbbbb") ? "OK" : "NOPE");
 		System.out.println(scan("b") ? "OK" : "NOPE");


 	}




 }