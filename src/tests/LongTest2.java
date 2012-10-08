package tests;

import catg.CATG;

public class LongTest2 {
	public static void main(String[] args) {
		long l = CATG.readLong(1);
		if(l > 2){
			 System.out.println("l is more than 3");
		}
	    System.out.println("l="+l);
	}
}
