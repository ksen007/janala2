package janala.interpreters;

import java.util.ArrayList;

/** Stack frame that stores Value objects */
public class Frame {
  private ArrayList<Value> locals = new ArrayList<Value>(8);
  private ArrayList<Value> stack = new ArrayList<Value>(8);

  public int getStackSize() {
    return stack.size();
  }

  public final int nReturnWords;


  private Value ret;
  public void setRet(Value v) { ret = v; }
  public Value getRet() { return ret; }

  public Frame(int nReturnWords) {
    this.nReturnWords = nReturnWords;
    ret = PlaceHolder.instance;
  }

  public void addLocal(Value o) {
    locals.add(o);
  }

  public void addLocal2(Value o) {
    locals.add(o);
    locals.add(PlaceHolder.instance);
  }

  public void setLocal(int index, Value o) {
    int diff = index - locals.size();
    while (diff >= 0) {
      locals.add(PlaceHolder.instance);
      diff--;
    }
    locals.set(index, o);
  }

  public Value getLocal(int index) {
    if (index < locals.size()) {
      return locals.get(index);
    }
    return PlaceHolder.instance;
  }

  public void setLocal2(int index, Value o) {
    int diff = index - locals.size();
    while (diff >= -1) {
      locals.add(PlaceHolder.instance);
      diff--;
    }
    locals.set(index, o);
  }

  public Value getLocal2(int index) {
    if (index < locals.size()) {
      return locals.get(index);
    }
    return PlaceHolder.instance;
  }

  public void push(Value o) {
    stack.add(o);
  }

  public void push2(Value o) {
    stack.add(o);
    stack.add(PlaceHolder.instance);
  }

  public Value pop() {
    return stack.remove(stack.size() - 1);
  }

  public Value pop2() {
    stack.remove(stack.size() - 1);
    return stack.remove(stack.size() - 1);
  }

  public Value peek() {
    return stack.get(stack.size() - 1);
  }

  public Value peek2() {
    return stack.get(stack.size() - 2);
  }

  public Value peek3() {
    return stack.get(stack.size() - 3);
  }

  public Value peek4() {
    return stack.get(stack.size() - 4);
  }

  public void clear() {
    stack.clear();
  }

  @Override
  public String toString() {
    return "Stack: " + stack.toString();
  }
}
