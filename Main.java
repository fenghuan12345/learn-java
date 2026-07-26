import cat.Cat;     // 不同包必须 import
import cat.Meow;

public class Main {
  public static void main(String[] args) {
    Meow.hello();
    Cat c = new Cat("橘猫");
    c.say();
  }
}
