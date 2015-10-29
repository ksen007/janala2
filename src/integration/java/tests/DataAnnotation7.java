package tests;

import catg.CATG;

public class DataAnnotation7 {
	public static void main(String[] args) {
		boolean x = CATG.readBool(true);
       System.out.println("x = " + x);

		boolean a, b;
		CATG.BeginScope();
        System.out.println("1");
        a = !x;
		b = a;
        System.out.println("2");

		CATG.EndScope();
		a = CATG.abstractBool("test", a);

       System.out.println("a = " + a);
       System.out.println("b = " + b);

		if(a) {
            System.out.println("3");
			if(b) {
				System.out.println("a && b");
			}
		}
	}
}

