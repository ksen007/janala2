package tests;

import catg.CATG;

public class DataAnnotation6 {
	public static void main(String[] args) {
		int x = CATG.readInt(0);
		int y = CATG.readInt(0);
		int z = CATG.readInt(0);

		System.out.println("x = " + x);
		System.out.println("y = " + y);
		System.out.println("z = " + z);

		boolean flag, flagRuntime;
		CATG.BeginScope();
		{
			flag = someComplexLogic(x, y, z);

			flagRuntime = flag;
			System.out.println("flagRuntime = " + flagRuntime);
		}
		CATG.EndScope();
		flag = CATG.abstractBool("test", flag);

        System.out.println("flag = " + flag);
		if(flag) {
			if(flagRuntime) {
				// The motivation of this condition is detecting
				// a goal-reaching at runtime of concolic execution,
				// excluding intermediate inputs.
				System.out.println("goal");
			}
		}
	}

	private static boolean someComplexLogic(int x, int y, int z) {
		if(x != 0) {
			if(y != 0) {
				if(x*5 > y) {
					if(2*(x-1) > y*13) {
						if(x % y < z) {
							return true;
						}
					}
				}
			}
		}
		return false;
	}
}
