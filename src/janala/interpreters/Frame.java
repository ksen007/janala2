/*
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 */

package janala.interpreters;

import java.util.ArrayList;

/**
 * Author: Koushik Sen (ksen@cs.berkeley.edu)
 * Date: 6/17/12
 * Time: 6:03 PM
 */
public class Frame {
    private ArrayList<Object> locals = new ArrayList<Object>(8);
    private ArrayList<Object> stack = new ArrayList<Object>(8);

    public void addLocal(Object o) {
        locals.add(o);
    }

    public void addLocal2(Object o) {
        locals.add(o);
        locals.add(PlaceHolder.instance);
    }

    public void setLocal(int index, Object o) {
        int sz = locals.size();
        int diff = index - sz;
        while(diff>=0) {
            locals.add(PlaceHolder.instance);
            diff--;
        }
        locals.set(index,o);
    }

    public Object getLocal(int index) {
        return locals.get(index);
    }

    public void setLocal2(int index, Object o) {
        int sz = locals.size();
        int diff = index - sz;
        while(diff>=-1) {
            locals.add(PlaceHolder.instance);
            diff--;
        }
        locals.set(index,o);
    }

    public Object getLocal2(int index) {
        return locals.get(index);
    }


    public void push(Object o) {
        stack.add(o);
    }

    public void push2(Object o) {
        stack.add(o);
        stack.add(PlaceHolder.instance);
    }

    public Object pop() {
        return stack.remove(stack.size()-1);
    }

    public Object pop2() {
        stack.remove(stack.size()-1);
        return stack.remove(stack.size()-1);
    }

    public Object peek2() {
        return stack.get(stack.size()-2);
    }

    public Object peek() {
        return stack.get(stack.size()-1);
    }
}
