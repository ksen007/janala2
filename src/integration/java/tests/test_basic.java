/*  * Bug report from Jason Vallet
*/

package tests;

import catg.CATG;

public class test_basic {
	
  public static void main (String[] args) {
    boolean a = CATG.readBool(false);
    if (a) {
      if (!a){
        System.out.println("Hello");
      }
    }
  }
}
