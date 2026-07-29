# Java 类与方法 基本结构

## 一、类的基本结构

一个 Java 文件由三部分组成：**属性、构造方法、普通方法**。

```java
// 文件名必须和 public 类名一致：Cat.java
package cat;

public class Cat {           // 类声明
    // 1. 属性（成员变量）
    String name;
    int age;

    // 2. 构造方法（创建对象时调用）
    public Cat(String name, int age) {
        this.name = name;    // this 指当前对象
        this.age = age;
    }

    // 3. 方法（行为）
    public void say() {
        System.out.println(name + "今年" + age + "岁");
    }
}
```

```java
// 使用
Cat c = new Cat("橘猫", 3);  // 调用构造方法，创建对象
c.say();                      // 调用方法
```

---

## 二、方法的组成

```java
修饰符 返回值类型 方法名(参数列表) {
    方法体;
    return 返回值;  // void 时不写
}
```

### 四种情况

```java
// 1. 无参数、无返回值
public void bark() {
    System.out.println("汪汪");
}

// 2. 有参数、无返回值
public void eat(String food) {
    System.out.println(name + "在吃" + food);
}

// 3. 无参数、有返回值
public int getAge() {
    return age;
}

// 4. 有参数、有返回值
public int add(int a, int b) {
    return a + b;
}
```

### 返回值注意事项

- 返回值类型写 `void` 表示没有返回值，方法内不需要 `return`
- 有返回值时，必须用 `return` 返回，且返回类型必须匹配
- `return` 会立即结束方法，后面的代码不会执行

```java
public int getAge() {
    return age;       // ✅ 返回 int 类型
    System.out.println("这行不会执行");  // return 后的代码不执行
}

public void test() {
    return;           // ✅ void 方法可以写 return（不带值），提前结束方法
    // return 1;      // ❌ void 方法不能返回值
}
```

---

## 三、构造方法

构造方法在 `new` 创建对象时自动调用，用于初始化属性。

```java
public class Cat {
    String name;

    // 无参构造
    public Cat() {
        this.name = "无名猫";
    }

    // 有参构造
    public Cat(String name) {
        this.name = name;
    }

    // 多个参数的构造
    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

```java
Cat c1 = new Cat();        // 调用无参构造
Cat c2 = new Cat("橘猫");  // 调用有参构造
```

### 构造方法规则

| 规则 | 说明 |
|------|------|
| 方法名和类名一样 | `Cat` 类的构造方法叫 `Cat` |
| 没有返回类型 | 连 `void` 都不写 |
| 可以重载 | 同一个类可以有多个构造方法（参数不同） |
| 不写任何构造方法 | Java 自动提供一个无参构造 |
| 写了任何一个构造方法 | Java 不再自动提供无参构造 |

---

## 四、static 方法 vs 普通方法

```java
public class MathUtil {

    // static 方法：属于类，不需要创建对象就能用
    public static int add(int a, int b) {
        return a + b;
    }

    // 普通方法：属于对象，必须创建对象才能用
    public void say() {
        System.out.println("hello");
    }
}
```

```java
// 使用
MathUtil.add(1, 2);         // ✅ static 方法，直接用类名调用

MathUtil m = new MathUtil();
m.say();                    // ✅ 普通方法，必须创建对象

MathUtil.say();             // ❌ 编译报错：普通方法不能用类名调用
```

### 对比

| | static 方法 | 普通方法 |
|---|---|---|
| 属于 | 类 | 对象 |
| 调用方式 | `类名.方法名()` | `对象.方法名()` |
| 是否需要创建对象 | 不需要 | 需要 |
| 能否访问普通属性 | ❌ 不能（没有 this） | ✅ 能 |
| 能否访问 static 属性 | ✅ 能 | ✅ 能 |
| 能否直接调用实例方法 | ❌ 不能 | ✅ 能 |

### 注意：static 方法中不能直接调用实例方法

static 方法属于类，没有对象实例，所以不能直接调用实例方法：

```java
public class Dog {
    // 实例方法（普通方法）
    public void bark() {
        System.out.println("汪汪");
    }

    // static 方法
    public static void test() {
        bark();           // ❌ 编译报错：无法从 static 上下文引用实例方法
        new Dog().bark(); // ✅ 必须创建对象后调用
    }
}
```

```java
public class Dog {
    String name;           // 普通属性
    static int count = 0;  // static 属性

    // static 方法不能访问普通属性
    public static void test1() {
        System.out.println(name);    // ❌ 编译报错：没有 this
        System.out.println(count);   // ✅ 能访问 static 属性
    }

    // 普通方法都能访问
    public void test2() {
        System.out.println(name);    // ✅
        System.out.println(count);   // ✅
    }
}
```

---

## 五、方法重载（Overload）

同一个类中，**方法名相同，参数不同**，就是方法重载。

```java
public class Calculator {

    // 参数类型不同
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }

    // 参数个数不同
    public int add(int a, int b, int c) {
        return a + b + c;
    }
}
```

```java
Calculator c = new Calculator();
c.add(1, 2);          // 调用第一个（int, int）
c.add(1.5, 2.5);      // 调用第二个（double, double）
c.add(1, 2, 3);       // 调用第三个（int, int, int）
```

### 重载的判断标准

| 条件 | 是否影响重载 |
|------|-------------|
| 参数类型不同 | ✅ 是重载 |
| 参数个数不同 | ✅ 是重载 |
| 参数顺序不同 | ✅ 是重载 |
| 返回值不同 | ❌ 不是重载 |
| 修饰符不同 | ❌ 不是重载 |

```java
// ❌ 这不是重载，编译报错（只有返回值不同）
public int add(int a, int b) { return a + b; }
public double add(int a, int b) { return a + b; }
```

---

## 六、this 关键字

`this` 指当前对象，即调用方法的那个对象。

### 用途 1：区分属性和参数

当参数名和属性名相同时，用 `this` 区分：

```java
public class Cat {
    String name;

    public Cat(String name) {
        this.name = name;  // this.name = 属性，name = 参数
    }
}
```

### 用途 2：链式调用

方法返回 `this`，就可以连续调用：

```java
public class Cat {
    String name;
    int age;

    public Cat setName(String name) {
        this.name = name;
        return this;  // 返回当前对象
    }

    public Cat setAge(int age) {
        this.age = age;
        return this;
    }
}
```

```java
// 链式调用
Cat c = new Cat();
c.setName("橘猫").setAge(3);  // 一行设置多个属性
```

### 用途 3：在构造方法中调用另一个构造方法

```java
public class Cat {
    String name;
    int age;

    public Cat() {
        this("无名猫", 1);  // 调用有参构造，必须放在第一行
    }

    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
```

### 注意：static 方法中不能用 this

`this` 指的是"当前对象"，而 `static` 方法属于类，没有对象，所以没有 `this`：

```java
public class Cat {
    String name;

    // 普通方法：能用 this
    public void say() {
        System.out.println(this.name);  // ✅
    }

    // static 方法：不能用 this
    public static void test() {
        // System.out.println(this.name);  // ❌ 编译报错：this 在 static 上下文中无效
    }
}
```

| 场景 | 能用 this |
|------|----------|
| 构造方法 | ✅ |
| 普通方法（非 static） | ✅ |
| static 方法 | ❌ |

---

## 七、访问修饰符

> 详细的访问修饰符说明请查看 [Java访问修饰符.md](Java访问修饰符.md)

四种修饰符从宽到窄：`public` → `protected` → 无修饰（包访问） → `private`

```java
public class Cat {
    private String name;       // private：只有自己能访问
    protected int age;         // protected：同包 + 子类能访问
    String species;            // 无修饰：只有同包能访问
    public String breed;       // public：谁都能访问

    // 实际开发中常用：属性 private + getter/setter public
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}
```

---

## 八、完整示例

```java
// cat/Cat.java
package cat;

public class Cat {
    // 属性
    private String name;
    private int age;
    private static int count = 0;  // 记录猫的总数

    // 构造方法
    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
        count++;
    }

    // 无参构造（调用有参构造）
    public Cat() {
        this("无名猫", 1);
    }

    // getter/setter
    public String getName() { return name; }
    public int getAge() { return age; }
    public void setName(String name) { this.name = name; }

    // 普通方法
    public void say() {
        System.out.println(name + "今年" + age + "岁");
    }

    // 方法重载
    public void say(String greeting) {
        System.out.println(greeting + "，我是" + name);
    }

    // static 方法
    public static int getCount() {
        return count;
    }
}
```

```java
// Main.java（默认包）
import cat.Cat;

public class Main {
    public static void main(String[] args) {
        // 创建对象
        Cat c1 = new Cat("橘猫", 3);
        Cat c2 = new Cat("胖橘", 5);
        Cat c3 = new Cat();  // 无参构造

        // 调用方法
        c1.say();                  // 橘猫今年3岁
        c2.say("你好");            // 你好，我是胖橘
        c3.say();                  // 无名猫今年1岁

        // static 方法
        System.out.println(Cat.getCount());  // 3

        // getter
        System.out.println(c1.getName());    // 橘猫
        System.out.println(c1.getAge());     // 3
    }
}
```

输出：
```
橘猫今年3岁
你好，我是胖橘
无名猫今年1岁
3
橘猫
3
```

---

## 九、对象的创建与 null

### 创建对象

用 `new` 关键字创建对象：

```java
Cat c = new Cat("橘猫", 3);
```

这行代码做了三件事：

```java
Cat c;           // 1. 声明一个 Cat 类型的变量
// = new Cat("橘猫", 3);  // 2. 用 new 创建对象（调用构造方法）
                  // 3. 把对象的地址赋给变量 c
```

### null 的含义

`null` 表示"不指向任何对象"：

```java
Cat c = null;        // c 不指向任何对象
Cat c2 = new Cat();  // c2 指向一个真实的 Cat 对象
```

### 空指针异常

对 `null` 调用方法或访问属性，会报空指针异常（NullPointerException）：

```java
Cat c = null;
c.say();              // ❌ 运行时报错：NullPointerException
System.out.println(c.name);  // ❌ 同样报错
```

```java
// 安全的做法：先判断是否为 null
if (c != null) {
    c.say();          // ✅ 不为 null 才调用
}
```

### 对象的生命周期

```
Cat c = new Cat();  // 创建对象 → 对象诞生
// ... 使用对象 ...
c = null;           // 不再引用 → 对象等待垃圾回收
                    // 没有引用的对象会被 JVM 自动回收
```

---

## 十、方法重写（Override）

### 什么是重写

子类重写父类的方法，改变其行为：

```java
// 父类
package zoo;

public class Animal {
    public void speak() {
        System.out.println("动物发出声音");
    }
}
```

```java
// 子类
package cat;

import zoo.Animal;

public class Cat extends Animal {
    @Override  // 标注重写（不是必须写，但建议写）
    public void speak() {
        System.out.println("喵喵喵");
    }
}
```

```java
Cat c = new Cat();
c.speak();  // 输出：喵喵喵（调用的是子类重写后的方法）
```

### 重写 vs 重载

| | 重写（Override） | 重载（Overload） |
|---|---|---|
| 位置 | 子类重写父类的方法 | 同一个类中 |
| 方法名 | 必须相同 | 必须相同 |
| 参数 | 必须相同 | 必须不同 |
| 返回值 | 必须相同 | 可以不同 |
| 修饰符 | 不能更严格 | 无要求 |
| 运行时 | 动态绑定，运行时决定调用哪个 | 编译时决定调用哪个 |

```java
// 重写：子类覆盖父类方法
public class Cat extends Animal {
    @Override
    public void speak() {  // 参数和父类一样
        System.out.println("喵喵喵");
    }
}

// 重载：同一个类中方法名相同但参数不同
public class Calculator {
    public int add(int a, int b) { return a + b; }
    public double add(double a, double b) { return a + b; }  // 参数类型不同
}
```

### 重写的规则

```java
public class Animal {
    public void speak() { }
}

public class Cat extends Animal {
    // ✅ 合法的重写
    @Override
    public void speak() { }  // 方法名、参数、返回值都一样

    // ❌ 不能缩小访问权限
    // @Override
    // private void speak() { }  // 父类是 public，子类不能是 private

    // ❌ 不能改变返回值类型
    // @Override
    // public int speak() { return 0; }  // 父类返回 void，子类不能返回 int
}
```

---

## 十一、可变参数（varargs）

### 基本用法

方法参数数量不确定时，用 `类型... 参数名`：

```java
public class MathUtil {
    // 普通方法：参数个数固定
    public static int add(int a, int b) {
        return a + b;
    }

    // 可变参数：参数个数不固定
    public static int add(int... numbers) {
        int sum = 0;
        for (int n : numbers) {
            sum += n;
        }
        return sum;
    }
}
```

```java
System.out.println(MathUtil.add(1, 2));        // 输出：3
System.out.println(MathUtil.add(1, 2, 3));     // 输出：6
System.out.println(MathUtil.add(1, 2, 3, 4));  // 输出：10
```

### 可变参数的规则

```java
public class Demo {
    // ✅ 可变参数可以和其他参数组合
    public static void print(String prefix, int... numbers) {
        System.out.print(prefix + ": ");
        for (int n : numbers) {
            System.out.print(n + " ");
        }
        System.out.println();
    }

    // ❌ 可变参数必须是最后一个参数
    // public static void bad(int... nums, String name) { }  // 编译报错

    // ✅ 可变参数可以当数组使用
    public static int sum(int... numbers) {
        return numbers.length;  // numbers 就是 int 数组
    }
}
```

```java
Demo.print("结果", 1, 2, 3);  // 输出：结果: 1 2 3
```

### 可变参数 vs 数组参数

```java
// 可变参数：调用时可以传任意个参数
public static void test(int... nums) { }
test(1);        // ✅
test(1, 2, 3);  // ✅

// 数组参数：调用时必须传数组
public static void test2(int[] nums) { }
// test2(1, 2, 3);  // ❌ 编译报错
test2(new int[]{1, 2, 3});  // ✅ 必须传数组
```

---

## 十二、Java 程序执行流程

### 1. 程序入口：main 方法

Java 程序从 `public static void main(String[] args)` 开始执行，所以项目中**必须有一个类包含 main 方法**：

```java
public class Main {
    public static void main(String[] args) {
        System.out.println("程序从这里开始执行");
    }
}
```

main 方法的每个关键字都不能少：

| 关键字 | 作用 |
|--------|------|
| `public` | JVM 需要从外部调用这个方法 |
| `static` | 不需要创建对象就能执行 |
| `void` | 没有返回值 |
| `String[] args` | 可以接收命令行参数 |

### 2. 编译再运行

Java 文件不能直接运行，必须先编译再执行：

```bash
# 第一步：用 javac 编译（生成 .class 文件）
javac -encoding UTF-8 Main.java

# 第二步：用 java 运行
java Main
```

如果有中文，JDK 8 必须指定编码：

```bash
# JDK 8 默认用 GBK，源码是 UTF-8 时必须加 -encoding
javac -encoding UTF-8 Main.java

# JDK 9+ 默认用 UTF-8，一般不用加
javac Main.java
```

### 3. 借助 IDE 运行

IDE 帮你自动做了两件事：

| 手动操作 | IDE 自动完成 |
|---------|-------------|
| `javac Main.java` 编译 | 保存时自动编译 |
| `java -cp ".;..." Main` 配 classpath | 自动配置 classpath |

所以 IDE 里点运行就能直接跑，但本质还是**先编译再执行**。
