public class TreZeriComplementare
{
	//Automa che riconosce stringhe che NON hanno 3 zeri consecutivi
    public static boolean scan(String s)
    {
	int state = 0;
	int i = 0;

	while (state >= 0 && i < s.length()) {
	    final char ch = s.charAt(i++);

	    switch (state) {
	    case 0:
		if (ch == '0')
		    state = 1;
		else if (ch == '1')
		    state = 0;
		else
		    state = -1;
		break;

	    case 1:
		if (ch == '0')
		    state = 2;
		else if (ch == '1')
		    state = 0;
		else
		    state = -1;
		break;

	    case 2:
		if (ch == '0')
		    state = 3;
		else if (ch == '1')
		    state = 0;
		else
		    state = -1;
		break;

	    case 3:
		if (ch == '0' || ch == '1')
		    state = 3;
		else
		    state = -1;
		break;
	    }
	}
	return state==0 || state==1 || state==2;
    }

    public static void main(String[] args)
    {
    	//Automa che riconosce stringhe che NON hanno 3 zeri consecutivi
    	//Output supposto: NOPE
		System.out.println(scan("01010001") ? "OK" : "NOPE");
		System.out.println(scan("000") ? "OK" : "NOPE");
		System.out.println(scan("abc") ? "OK" : "NOPE");
		
		//Output supposto: OK
		System.out.println(scan("011110010") ? "OK" : "NOPE");
		System.out.println(scan("00") ? "OK" : "NOPE");
		System.out.println(scan("110010") ? "OK" : "NOPE");
    }
}