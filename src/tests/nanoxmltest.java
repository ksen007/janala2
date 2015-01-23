/* This file is part of AUIT, the Abstract User Interface Toolkit.
 *
 * $Revision: 1.4 $
 * $Date: 2000/08/13 19:04:12 $
 * $Name: RELEASE_1_6_8 $
 *
 * Copyright (C) 2000 Marc De Scheemaecker, All Rights Reserved.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the
 * use of this software.
 *
 * Permission is granted to anyone to use this software for any purpose,
 * including commercial applications, and to alter it and redistribute it
 * freely, subject to the following restrictions:
 *
 *  1. The origin of this software must not be misrepresented; you must not
 *     claim that you wrote the original software. If you use this software in
 *     a product, an acknowledgment in the product documentation would be
 *     appreciated but is not required.
 *
 *  2. Altered source versions must be plainly marked as such, and must not be
 *     misrepresented as being the original software.
 *
 *  3. This notice may not be removed or altered from any source distribution.
 */

package tests;

import catg.CATG;
import tests.nanoxml.XMLElement;


public class nanoxmltest
{
    public static void main(String param[])
        throws Exception
    {
//        Hashtable choices = new Hashtable();
//        choices.put("one", new Integer(1));
//        choices.put("two", new Integer(2));
//        InputStream in = new FileInputStream("../example.xml");
//        String xml = "";
//
//        while (in.available() > 0) {
//            byte[] b = new byte[in.available()];
//            in.read(b);
//            xml = xml + new String(b);
//        }
//
//        in.close();
        int N = 7;
        XMLElement foo = new XMLElement();
        char[] xml = new char[N];
        for (int i = 0; i < N; i++) {
            xml[i] = CATG.readChar('a');
            System.out.print(xml[i]);
        }
        System.out.println();
        foo.parseCharArray(xml, 0, N);
//        System.out.println("<" + foo.getTagName() + ">");
//        Enumeration enum2 = foo.enumerateChildren();
//
//        while (enum2.hasMoreElements()) {
//            XMLElement bar = (XMLElement)(enum2.nextElement());
//            System.out.println("  <" + bar.getTagName() + ">");
//            System.out.println("    req: " + bar.getProperty("REQ"));
//            System.out.println("    opt: " + bar.getProperty("OPT", "blah"));
//            System.out.println("    choice: "
//                               + bar.getIntProperty("CHOICE", choices, "one"));
//            System.out.println("    contents: " + bar.getContents());
//        }
//
//        System.out.println(foo);
    }
}
