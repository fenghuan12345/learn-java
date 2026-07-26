package cat;

public class Cat {
  String name;

  public Cat(String name) {
    this.name = name;
  }

  public void say() {
    Meow.hello(); // 同包直接用，不需要 import
    System.out.println(name + "是一只猫");
  }
}
