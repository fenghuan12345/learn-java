# Java 包（Package）详解

## 一、java.lang 包自动导入

### 什么是 java.lang

`java.lang` 是 Java 最基础的包，包含了语言核心类：`String`、`System`、`Math`、`Integer`、`Object` 等。

Java 编译器**默认自动导入** `java.lang` 包下的所有类，所以你不需要手动写 `import`：

```java
// 不需要写 import java.lang.String;
// 不需要写 import java.lang.System;
// 不需要写 import java.lang.Math;

public class Demo {
    public static void main(String[] args) {
        String s = "hello";              // ✅ 直接用，不需要 import
        System.out.println(s);           // ✅ 直接用
        int n = Math.max(1, 2);          // ✅ 直接用
        Integer i = 100;                 // ✅ 直接用
    }
}
```

### java.lang 包常见类一览

| 类名 | 用途 | 示例 |
|------|------|------|
| `String` | 字符串 | `"hello".length()` |
| `System` | 系统输入输出 | `System.out.println()` |
| `Math` | 数学运算 | `Math.max(1, 2)` |
| `Integer` | int 的包装类 | `Integer.parseInt("100")` |
| `Object` | 所有类的父类 | 所有类默认继承它 |
| `Exception` | 异常基类 | `try-catch` 中使用 |

### 其他包必须手动 import

`java.lang` 之外的包都需要手动 import：

```java
// java.util.Date 需要手动 import
import java.util.Date;    // ✅ 必须写

// java.io.File 需要手动 import
import java.io.File;      // ✅ 必须写

public class Demo {
    public static void main(String[] args) {
        Date now = new Date();     // ✅ import 后能用
        File f = new File(".");    // ✅ import 后能用
    }
}
```

```java
// 没有 import
public class Demo {
    public static void main(String[] args) {
        Date now = new Date();     // ❌ 编译报错：找不到 Date
    }
}
```

> **总结：** 只有 `java.lang` 包自动导入，其他所有包都要手动 import。

---

## 二、通配符导入 import *

### 基本用法

`import cat.*;` 表示导入 `cat` 包下的**所有 public 类**：

```java
// 逐个导入（繁琐）
import cat.Cat;
import cat.Meow;
import cat.Puppy;
import cat.Kitten;

// 通配符导入（简洁，效果一样）
import cat.*;

public class Main {
    public static void main(String[] args) {
        Cat c = new Cat("橘猫");    // ✅
        Meow.hello();               // ✅
        Puppy.bark();               // ✅
        Kitten.purr();              // ✅
    }
}
```

### 通配符导入 vs 逐个导入

| 对比项 | `import cat.*` | `import cat.Cat` |
|--------|----------------|------------------|
| 写法 | 一行搞定 | 每个类一行 |
| 导入范围 | 包下所有 public 类 | 只导入指定类 |
| 性能 | 无区别（编译时自动解析） | 无区别 |
| 可读性 | 不知道具体用了哪些类 | 一目了然 |

### 注意事项

**1. 只导入当前包，不包含子包**

```java
import cat.*;       // ✅ 导入 cat 包的所有类
import cat.dog.*;   // ✅ 导入 cat.dog 子包的所有类（需要单独写）

// cat.* 不包含 cat.dog 下的类
```

**2. 导入多个包的同名类会冲突**

```java
import java.util.Date;   // java.util 包的 Date
import java.sql.Date;     // java.sql 包的 Date

public class Demo {
    public static void main(String[] args) {
        Date d = new Date();  // ❌ 编译报错：不知道用哪个 Date
    }
}

// 解决：用全限定名明确指定
java.util.Date d = new java.util.Date();  // ✅
```

**3. 建议：初学用逐个导入，熟练后可用通配符**

逐个导入能让你清楚地知道用了哪些类，方便学习。通配符导入代码更简洁，但初学时不容易看出依赖了哪些类。

---

## 三、静态导入 import static

### 基本用法

`import static` 可以直接使用类中的**静态方法和静态变量**，不需要加类名：

```java
// 普通导入
import java.lang.Math;

public class Demo {
    public static void main(String[] args) {
        double r = Math.random();         // 必须写 Math.random()
        double m = Math.max(1, 2);        // 必须写 Math.max()
        double pi = Math.PI;              // 必须写 Math.PI
    }
}
```

```java
// 静态导入
import static java.lang.Math.random;
import static java.lang.Math.max;
import static java.lang.Math.PI;

public class Demo {
    public static void main(String[] args) {
        double r = random();              // ✅ 不需要写 Math.
        double m = max(1, 2);             // ✅ 不需要写 Math.
        double pi = PI;                   // ✅ 不需要写 Math.
    }
}
```

### 通配符静态导入

```java
// 导入 Math 类的所有静态成员
import static java.lang.Math.*;

public class Demo {
    public static void main(String[] args) {
        double r = random();   // ✅
        double m = max(1, 2);  // ✅
        double pi = PI;        // ✅
        double s = sqrt(4);    // ✅
    }
}
```

### 静态导入 vs 普通导入

| 对比项 | `import static java.lang.Math.max` | `import java.lang.Math` |
|--------|-------------------------------------|-------------------------|
| 使用方式 | `max(1, 2)` | `Math.max(1, 2)` |
| 优点 | 代码简洁 | 一眼看出方法来自哪个类 |
| 缺点 | 不知道 `max` 是哪个类的方法 | 写法稍长 |

### 常见使用场景

**1. JUnit 测试中的断言**

```java
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Test {
    @Test
    public void test() {
        assertEquals(4, 2 + 2);    // 比 Assert.assertEquals 更简洁
        assertTrue(true);
    }
}
```

**2. 常量使用**

```java
import static java.lang.Math.PI;
import static java.lang.Math.E;

public class Circle {
    double area(double r) {
        return PI * r * r;          // 比 Math.PI * r * r 简洁
    }
}
```

> **建议：** 静态导入适合常量和频繁调用的静态方法（如 `assertEquals`）。对于偶尔用一次的方法，保留类名更清晰。

---

## 四、包命名惯例

### 域名倒写规范

Java 包名的惯例是**把公司域名倒过来写**，保证全球唯一：

| 公司域名 | 包名 |
|----------|------|
| google.com | `com.google` |
| apache.org | `org.apache` |
| oracle.com | `com.oracle` |

### 完整包名结构

```
com.公司名.项目名.模块名
```

示例：

```
com.mycompany.shop         ← 电商平台
com.mycompany.shop.order   ← 订单模块
com.mycompany.shop.user    ← 用户模块
```

对应目录结构：

```
src/
└── com/
    └── mycompany/
        └── shop/
            ├── order/
            │   └── Order.java      ← package com.mycompany.shop.order;
            └── user/
                └── User.java       ← package com.mycompany.shop.user;
```

### 为什么用域名倒写

```
com.google.common.collect.Lists
│   │       │       │       │
│   │       │       │       └── 类名
│   │       │       └── 模块
│   │       └── 项目
│   └── 公司
└── 顶级域名
```

倒写的原因：域名在全球是唯一的（`google.com` 只属于 Google），倒过来后包名也就全球唯一了。如果直接写 `google.com`，万一有人也用 `com.google` 就冲突了。

### 常见顶级域名含义

| 顶级域名 | 用途 | 示例 |
|----------|------|------|
| `com` | 商业组织 | `com.baidu` |
| `org` | 非营利组织 | `org.apache` |
| `net` | 网络组织 | `net.sourceforge` |
| `edu` | 教育机构 | `edu.mit` |
| `gov` | 政府机构 | `gov.cn` |

### 学习项目怎么写

学习项目没有公司域名，简单写就行：

```java
package learn;              // 最简单
package learn.day01;        // 按天分
package learn.collections;  // 按主题分
```

---

## 五、包防止命名冲突

包存在的核心意义之一：**不同包中可以有同名类，互不干扰。**

```java
// java.util.Date（Java 自带的日期类）
package java.util;
public class Date { }

// com.mycompany.Date（你自己写的日期类）
package com.mycompany;
public class Date { }
```

```java
import java.util.Date;      // Java 的 Date
import com.mycompany.Date;   // 你的 Date

// 两个 Date 共存，不会冲突
// 但使用时需要明确指定
java.util.Date d1 = new java.util.Date();
com.mycompany.Date d2 = new com.mycompany.Date();
```

> **如果没有包，两个叫 `Date` 的类就冲突了，编译器不知道用哪个。** 包让同名类可以在不同命名空间中共存。

---

## 六、同包下的访问规则

同一个包内的类，互相访问**不需要 import**，而且**不写 public 也能互相访问**：

```java
// cat/Cat.java
package cat;

class Cat {                  // 没有 public → 包访问级别
    String name;             // 没有 public → 包访问级别

    void say() {             // 没有 public → 包访问级别
        System.out.println(name);
    }
}
```

```java
// cat/Meow.java（同一个 cat 包）
package cat;

public class Meow {
    void test() {
        Cat c = new Cat();   // ✅ 同包，能创建 Cat 对象（即使 Cat 没有 public）
        c.name = "橘猫";     // ✅ 同包，能访问 name（即使 name 没有 public）
        c.say();             // ✅ 同包，能调用 say（即使 say 没有 public）
    }
}
```

```java
// Main.java（默认包，不同包）
import cat.Cat;    // ❌ 编译报错：Cat 不是 public，跨包访问不了
import cat.Meow;   // ✅ Meow 是 public，能 import

public class Main {
    public static void main(String[] args) {
        Meow m = new Meow();  // ✅ Meow 是 public，能用
        Cat c = new Cat();    // ❌ Cat 不是 public，用不了
    }
}
```

**总结：** 包访问级别（不写修饰符）= 同包随便用，跨包不行。这是封装的重要手段——把不需要对外暴露的类和方法设为包访问级别，只让同包的类使用。

---

## 七、子包和父包是独立的

`cat` 和 `cat.dog` 是**两个独立的包**，不能直接互相访问：

```java
// cat/Cat.java
package cat;

public class Cat {
    public static void hello() {
        System.out.println("我是 cat 包的 Cat");
    }
}
```

```java
// cat/dog/Puppy.java
package cat.dog;

// ❌ 没有 import，不能直接用父包的类
public class Puppy {
    public static void test() {
        Cat.hello();  // ❌ 编译报错：找不到 Cat
    }
}
```

```java
// cat/dog/Puppy.java（修正）
package cat.dog;

import cat.Cat;  // ✅ 必须显式 import 父包的类

public class Puppy {
    public static void test() {
        Cat.hello();  // ✅ import 后能用
    }
}
```

> **重点：** `cat.dog` 不是 `cat` 的"子集"，它们是两个完全独立的包。Java 中没有"子包自动继承父包"的概念。

---

## 八、一个文件多个类的规则

一个 `.java` 文件中可以定义多个类，但有严格限制：

```java
// Cat.java
package cat;

public class Cat { }       // ✅ 可以有 public，文件名必须和这个类名一致
class Dog { }              // ✅ 可以有，但不能是 public（包访问级别）
class Bird { }             // ✅ 可以有，也是包访问级别
```

**规则：**

| 限制 | 说明 |
|------|------|
| 只能有一个 `public` 类 | 且文件名必须和这个 public 类名一致 |
| 其他类不能是 `public` | 只能是包访问级别（不写修饰符） |
| 其他类只有同包能访问 | 跨包访问不了 |

```java
// cat/Test.java（cat 包）
package cat;

public class Test {
    public static void main(String[] args) {
        Cat c = new Cat();   // ✅ 同包，能用
        Dog d = new Dog();   // ✅ 同包，能用
        Bird b = new Bird(); // ✅ 同包，能用
    }
}
```

```java
// Main.java（默认包，不同包）
import cat.Cat;   // ✅ Cat 是 public，能 import
import cat.Dog;   // ❌ Dog 不是 public，编译报错
import cat.Bird;  // ❌ Bird 不是 public，编译报错

public class Main {
    public static void main(String[] args) {
        Cat c = new Cat();   // ✅
        Dog d = new Dog();   // ❌ 用不了
    }
}
```

> **实际开发中很少在一个文件里放多个类。** 通常一个文件一个 public 类，便于管理和查找。但了解这个规则有助于理解为什么有些类"莫名其妙"访问不了。

---

## 九、什么是默认包

一个 `.java` 文件中**没有写 `package` 声明**，这个类就属于默认包（也叫无名包）。

```java
// A.java
// 没有 package 声明 → 这个类属于默认包
public class A {
    public static void main(String[] args) {
        System.out.println("我是默认包的 A");
    }
}
```

### 如何判断一个类是否在默认包

看文件第一行有没有 `package` 声明：

| 情况 | 所属包 |
|------|--------|
| 第一行是 `package xxx;` | xxx 包 |
| 没有 `package` 声明 | 默认包 |

```java
// cat/Cat.java
package cat;          // ← 有 package 声明 → cat 包

// 7-25/Start.java
                      // ← 没有 package 声明 → 默认包
public class Start { }
```

### 默认包的核心规则

> **前提：一个项目永远只有一个默认包。** 所有没有写 `package` 声明的类，不管放在哪个目录，都属于同一个默认包。

### 规则 1：目录不影响包名

一个文件有没有 `package` 声明，和它放在哪个目录**没有关系**。没有 `package`，不管放哪个目录都是默认包：

```
learn-java/
├── A.java              ← 没有 package → 默认包
├── 7-25/
│   ├── Start.java      ← 没有 package → 默认包（不是 7-25 包）
│   └── dog/
│       └── Keji.java   ← 没有 package → 默认包（不是 dog 包）
```

三个文件虽然在不同目录，但**都在同一个默认包**里。

> **重点：决定包名的是 `package` 声明，不是目录。**
> 目录只在你写了 `package` 之后，要求你必须对得上。

> **深入理解：包和目录是两个不同的概念**
>
> - **包（package）** 是逻辑概念，是命名空间，用于组织类、防止命名冲突。
> - **目录** 是物理概念，是文件在磁盘上的存储位置。
>
> 两者本质不同。默认包就是最好的证明：没有 `package` 声明的类，放哪个目录都是默认包，目录本身不决定包名。
>
> 那为什么总说"目录 = 包名"？因为 Java 编译器强制要求：**写了 `package` 声明后，目录必须匹配它。** 这是编译规则，不是本质关系。
> 就像你起了个名字叫"张三"，身份证上必须写"张三"——但名字不是身份证，只是身份证要求一致。

### 规则 2：默认包的类之间互相直接用

同一个默认包中的类，互相引用**不需要 import**：

```java
// A.java（默认包）
public class A {
    public static int value() {
        return 123;
    }
}
```

```java
// Start.java（默认包）
public class Start {
    public static void main(String[] args) {
        // A 和 Start 都在默认包，直接用，不需要 import
        System.out.println(A.value());  // ✅ 输出 123
    }
}
```

### 规则 3：默认包可以 import 有包名的类

```java
// Main.java（默认包）
import cat.Cat;      // ✅ 默认包可以 import cat 包的类
import cat.Meow;     // ✅

public class Main {
    public static void main(String[] args) {
        Meow.hello();            // ✅ 能用
        Cat c = new Cat("橘猫"); // ✅ 能用
    }
}
```

> **限制：被 import 的类必须是 `public` 的。** 不限包名、不限目录，项目中任意 `public` 类都能被默认包 import。但如果目标类没有 `public` 修饰（包访问级别），则无法跨包访问。

### 规则 4：有包名的类不能 import 默认包的类

这是 Java 的**硬性限制**，不是配置问题：

```java
// cat/Cat.java（cat 包）
package cat;

import A;  // ❌ 编译报错：有包名的类不能 import 默认包的类

public class Cat {
    public void say() {
        A.value();  // ❌ 用不了
    }
}
```

> **原因：** 默认包没有名字，Java 编译器无法确定 import 路径。

### 规则 5：package 声明必须和目录匹配（有包名时）

当写了 `package` 声明，目录结构必须和包名一致：

```java
// cat/Cat.java
package cat;       // ✅ 文件在 cat/ 目录下，对得上

// 7-25/dog/Keji.java
package dog;       // ❌ 文件在 7-25/dog/ 目录下，对不上（缺少 7-25 层）

// 7-25/dog/Keji.java
package 7-25.dog;  // ❌ 7-25 不是合法包名（数字开头+连字符）
```

### 规则 6：合法包名规则

包名只能包含：
- 字母（a-z, A-Z）
- 数字（0-9，但**不能以数字开头**）
- 下划线（_）
- 美元符号（$）

| 包名 | 是否合法 | 原因 |
|------|----------|------|
| `cat` | ✅ | 纯字母 |
| `dog2` | ✅ | 字母开头 |
| `_725` | ✅ | 下划线开头 |
| `7-25` | ❌ | 数字开头 + 连字符 |
| `my包` | ❌ | 包含中文 |

### 默认包小结

综合以上规则，默认包的核心要点：

1. **一个项目只有一个默认包**，不管目录结构多复杂。
2. 没有 `package` 声明的文件，不管放哪个目录，都在同一个默认包里（规则 1）。
3. 默认包的类之间互相直接用，不需要 import（规则 2）。
4. 默认包可以 import 有包名的类，但目标类必须是 `public`（规则 3）。
5. 有包名的类不能 import 默认包的类（规则 4）。

### 默认包的引用关系总结

```
默认包 ──→ 默认包       ✅ 直接用，不需要 import
默认包 ──→ 有包名的类   ✅ 需要 import
有包名的类 ──→ 默认包   ❌ 不允许，编译报错
```

示意图：

```
┌─ 默认包 ─────────────────────┐
│  A.java                      │
│  Start.java                  │
│  Keji.java                   │
│                              │
│  直接互相调用 ✅              │
│  import dog.Puppy; ✅        │
└──────────────────────────────┘
         ↑
         │ import dog.Puppy; ✅ 默认包可以 import 有包名的类

┌─ dog 包 ─────────────────────┐
│  Puppy.java                  │
│                              │
│  import A; ❌ 不能 import 默认包
└──────────────────────────────┘
```

## 十、编译与运行

### 命令行编译

默认包的类放在不同目录时，编译要列出所有文件路径：

```bash
javac -encoding UTF-8 A.java 7-25/Start.java 7-25/dog/Keji.java
```

### 命令行运行

运行时要把所有目录加到 classpath（`-cp` 参数），否则 JVM 找不到类：

```bash
java -cp ".;7-25;7-25/dog" Start
```

| 参数 | 作用 |
|------|------|
| `-cp ".;7-25;7-25/dog"` | 告诉 JVM 去这三个目录找类文件 |
| `.` | 当前目录（learn-java/） |
| `7-25` | 找 Start.class |
| `7-25/dog` | 找 Keji.class |

> **注意：** 不加 `-cp` 或者漏了目录，运行时会报 `ClassNotFoundException`。

## 十一、IDE 配置（VS Code）

IDE 的 Java 插件需要知道去哪找类，通过 `.vscode/settings.json` 配置：

```json
{
  "java.project.sourcePaths": [".", "7-25", "7-25/dog"]
}
```

| 源码根 | 能找到的类 |
|--------|-----------|
| `.` | 根目录下的默认包类（A, Main, run） |
| `7-25` | Start |
| `7-25/dog` | Keji（默认包），Puppy（dog 包） |

**不配 sourcePaths → IDE 报红线，但命令行编译不受影响。**

## 十二、常见错误与注意事项

### 1. 文件名必须和 public 类名一致（区分大小写）

```java
// Keji.java
public class Keji { }    // ✅ 文件名和类名一致

// keji.java
public class Keji { }    // ❌ 文件名小写 k，类名大写 K，编译报错
```

### 2. 默认包的类无法被有包名的类 import

这是 Java 的硬性限制。所以大型项目一般都用包名，避免被限制。

### 3. package 声明必须和目录匹配

写了 `package` 就必须和目录对得上，否则编译报错：
- `package cat;` → 文件必须在 `cat/` 目录下
- `package 7-25;` → ❌ 非法包名，编译报错

### 4. IDE 红线 ≠ 编译错误

IDE 报红线通常是因为 sourcePaths 没配对，实际用 javac 编译可能完全没问题。遇到红线先用命令行编译试试，不要慌。

#### 常见的"IDE 报错但编译没问题"的情况

**情况 1：默认包的类放在子目录，IDE 找不到**

```
learn-java/
├── Main.java          ← 默认包
└── 7-25/dog/Keji.java ← 默认包（没有 package 声明）
```

`Main.java` 里直接用 `Keji`，javac 编译没问题，但 IDE 报红线。

原因：IDE 的 sourcePaths 只配了 `["."]`（项目根），插件只在根目录找类，找不到 `7-25/dog/` 下的 `Keji`。

解决：在 `.vscode/settings.json` 中加 `"7-25/dog"` 到 sourcePaths。

**情况 2：写了 package 声明，但目录和包名对不上**

```
7-25/dog/Puppy.java   ← 写了 package dog;
```

javac 从项目根编译可能报错（路径不匹配），但单独开 `7-25/` 目录时 IDE 不报错。

原因：单独开 `7-25/` 时，源码根是 `7-25/`，`dog/Puppy.java` 对应 `package dog;`，刚好对上。

**情况 3：一个目录下有 package 声明的文件，也有没有的文件**

```
7-25/
├── Start.java         ← 没有 package（默认包）
└── dog/
    └── Puppy.java     ← 有 package dog;
```

IDE 可能会困惑：`Start.java` 是默认包还是 `7-25` 包？因为它和 `Puppy.java` 在同一个项目里，但一个有包名一个没有。

javac 编译没问题，因为它们本来就是不同包（默认包 vs dog 包），只要 classpath 配对就行。

#### 总结：IDE 和 javac 的区别

| | javac（命令行） | IDE（VS Code） |
|---|---|---|
| 找类方式 | 通过 `-cp` 参数指定 classpath | 通过 sourcePaths 配置 |
| 默认包放子目录 | ✅ 只要 classpath 配对就行 | ❌ 需要把子目录加到 sourcePaths |
| package 和目录不匹配 | ❌ 编译报错 | ❌ 也会报错 |
| 没配 sourcePaths | 不影响（用 `-cp` 代替） | ❌ 报红线 |

> **经验：遇到 IDE 红线，先用命令行编译试试。命令行能跑，说明代码没问题，只是 IDE 配置的问题。**

### 5. 不同目录的默认包类是同一个包

```
A.java（根目录）       → 默认包
7-25/Start.java       → 默认包
7-25/dog/Keji.java    → 默认包
```

三个文件**在同一个默认包**里，互相直接用，不需要 import。

## 十三、完整示例

### 文件结构

```
learn-java/
├── A.java                  ← 默认包
├── Main.java               ← 默认包
└── cat/
    ├── Cat.java            ← cat 包
    └── Meow.java           ← cat 包
```

### 代码

```java
// A.java（默认包）
public class A {
    public static int value() {
        return 123;
    }
}
```

```java
// cat/Meow.java（cat 包）
package cat;

public class Meow {
    public static void hello() {
        System.out.println("喵~");
    }
}
```

```java
// cat/Cat.java（cat 包）
package cat;

public class Cat {
    String name;

    public Cat(String name) {
        this.name = name;
    }

    public void say() {
        Meow.hello();  // 同包直接用，不需要 import
        System.out.println(name + "是一只猫");
    }
}
```

```java
// Main.java（默认包）
import cat.Cat;     // 默认包可以 import 有包名的类
import cat.Meow;

public class Main {
    public static void main(String[] args) {
        Meow.hello();              // ✅ 调用 cat 包的 Meow
        Cat c = new Cat("橘猫");   // ✅ 调用 cat 包的 Cat
        c.say();

        System.out.println(A.value());  // ✅ 同默认包，直接用
    }
}
```

### 编译与运行

```bash
javac -encoding UTF-8 A.java cat/Meow.java cat/Cat.java Main.java
java -Dfile.encoding=UTF-8 Main
```

### 输出

```
喵~
喵~
橘猫是一只猫
123
```
