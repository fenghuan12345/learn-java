public class Keji {
  String name;
  int age;

  public Keji(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public void bark() {
    // 直接调用默认包中 A 类的静态方法，不需要 import
    System.out.println(name + "(" + age + "岁) 叫了");
  }
}
