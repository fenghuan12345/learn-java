public class A {
  static int a() {
    int b = 123;
    b -= 1;
    return b;
  }

  public static void main(String[] args) {
    System.out.println(a());
  }
}
