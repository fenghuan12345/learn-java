public class A {
  int count1 = 1;
  static int count = 0;

  static int a() {
    int b = 123;
    return b + count;
  }

  public static void main(String[] args) {
    System.out.println(a());
    // System.out.println(this.count1); // this is not allowed in static context
  }
}
