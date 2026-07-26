package  7-25;
import dog.Puppy; // 默认包可以 import 有包名的类

public class Start {
  public static void main(String[] args) {
    Keji k = new Keji("旺财", 3); // Keji 是默认包，直接用
    k.bark();

    Puppy.hello(); // Puppy 是 dog 包，import 后也能用
  }
}
