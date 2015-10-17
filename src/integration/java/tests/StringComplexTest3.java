/*
 * Copyright (c) 2012, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package tests;

import catg.CATG;

public class StringComplexTest3 {
	public static void main(String[] argv) {
		//String a= "abcdef";
		//String b= "de";
		//String dummy = "def";
		String a = CATG.readString("a");


		String patternString = "[a-zA-Z]*";
		if(!(a.matches(patternString) && a.length() >= 0 && a.length() <= 100)){
            System.out.println("Branch 1");
            return;
		}
		if(a.startsWith("xyz", 3)){
			System.out.println("substringEquals(a,3,b) == true");
		}

		System.out.println("a : " + a);
	}

	// A and B are string variables, and S is integer value.
	// The constraint means A contains B and B starts at index S of A.
	private static boolean substringEquals(String A, int S, String B, String dummyString) {
		if (A.endsWith(dummyString) && dummyString.startsWith(B) && A.length() - dummyString.length() == S) {
			return true;
		} else {
			return false;
		}
	}
}
