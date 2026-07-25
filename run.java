public class run {
    // public static int b = 123;
    public int a(int b1, int b2) {
        // 和Javascript，一个方法内部变量，固定就只有方法内部能访问
        // 由此可以看出，java也有方法作用域，方法内部的变量，外部无法访问。其实他也块极作用域
        int b = 123;
        b -= 1;
        return b;
    }
}
