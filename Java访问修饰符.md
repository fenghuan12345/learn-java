# Java 访问修饰符详解

## 一、什么是访问修饰符

访问修饰符控制**谁能访问**这个类、属性或方法。Java 有四种修饰符，从宽到窄：

| 修饰符 | 含义 |
|--------|------|
| `public` | 公开的，谁都能用 |
| `protected` | 受保护的，同包 + 子类能用 |
| 无修饰（默认） | 包访问级别，只有同包能用 |
| `private` | 私有的，只有自己能用 |

---

## 二、谁能被修饰

不是所有东西都能用所有修饰符：

| 能被修饰的东西 | 可用的修饰符 |
|----------------|-------------|
| 类 | `public`、无修饰 |
| 属性 | 四种都可以 |
| 方法 | 四种都可以 |
| 构造方法 | 四种都可以 |

```java
public class Cat {              // ✅ 类用 public
class Dog { }                   // ✅ 类用无修饰
// private class Bird { }       // ❌ 类不能用 private
// protected class Fish { }     // ❌ 类不能用 protected

public String name;             // ✅ 属性四种都可以
protected int age;
String species;
private int id;
```

---

## 三、四种修饰符详解

### 1. public（公开）

谁都能访问，不限包、不限继承关系。

```java
// 动物园包
package zoo;

public class Animal {
    public String name;     // 任何地方都能访问
}
```

```java
// 猫包（不同包，子类）
package cat;

import zoo.Animal;

public class Cat extends Animal {
    public void test() {
        System.out.println(name);  // ✅
    }
}
```

```java
// 狗包（不同包，非子类）
package dog;

import zoo.Animal;

public class Dog {
    public void test() {
        Animal a = new Animal();
        System.out.println(a.name);  // ✅
    }
}
```

### 2. protected（受保护）

同包随便用 + 不同包只有子类能用。

```java
// 动物园包
package zoo;

public class Animal {
    protected int age;  // 同包能用，不同包子类也能用
}
```

```java
// 同包，非子类
package zoo;

public class Test {
    public void test() {
        Animal a = new Animal();
        System.out.println(a.age);  // ✅ 同包，能用
    }
}
```

```java
// 不同包，子类
package cat;

import zoo.Animal;

public class Cat extends Animal {
    public void test() {
        System.out.println(age);  // ✅ 不同包但我是子类，能用
    }
}
```

```java
// 不同包，非子类
package dog;

import zoo.Animal;

public class Dog {
    public void test() {
        Animal a = new Animal();
        System.out.println(a.age);  // ❌ 不同包且不是子类，用不了
    }
}
```

### 3. 无修饰（包访问级别）

不写任何修饰符，只有同一个包内的类能访问。

```java
// 动物园包
package zoo;

public class Animal {
    String species;  // 没有修饰符 → 包访问级别
}
```

```java
// 同包
package zoo;

public class Test {
    public void test() {
        Animal a = new Animal();
        System.out.println(a.species);  // ✅ 同包，能用
    }
}
```

```java
// 不同包，子类
package cat;

import zoo.Animal;

public class Cat extends Animal {
    public void test() {
        System.out.println(species);  // ❌ 不同包，用不了
    }
}
```

```java
// 不同包，非子类
package dog;

import zoo.Animal;

public class Dog {
    public void test() {
        Animal a = new Animal();
        System.out.println(a.species);  // ❌ 不同包，用不了
    }
}
```

### 4. private（私有）

只有同一个类内部能访问，其他地方都用不了。

```java
// 动物园包
package zoo;

public class Animal {
    private int id;  // 只有 Animal 类内部能用

    public void test() {
        System.out.println(id);  // ✅ 自己类内部，能用
    }
}
```

```java
// 同包
package zoo;

public class Test {
    public void test() {
        Animal a = new Animal();
        System.out.println(a.id);  // ❌ private，同包也用不了
    }
}
```

```java
// 不同包，子类
package cat;

import zoo.Animal;

public class Cat extends Animal {
    public void test() {
        System.out.println(id);  // ❌ private，子类也用不了
    }
}
```

---

## 四、父类与子类的访问规则

### 什么是继承

子类通过 `extends` 关键字继承父类，自动拥有父类的属性和方法：

```java
// 父类
package zoo;

public class Animal {
    public String name;
    protected int age;
    String species;
    private int id;

    public void eat() {
        System.out.println(name + "在吃东西");
    }
}
```

```java
// 子类（继承了 Animal）
package cat;

import zoo.Animal;

public class Cat extends Animal {
    public void test() {
        System.out.println(name);     // ✅ public，能用
        System.out.println(age);      // ✅ protected，子类能用
        System.out.println(species);  // ❌ 包访问，不同包用不了
        System.out.println(id);       // ❌ private，用不了
        eat();                        // ✅ public 方法，能用
    }
}
```

### 父类成员在子类中的可访问性

| 修饰符 | 同包子类 | 不同包子类 | 同包非子类 | 不同包非子类 |
|--------|---------|-----------|-----------|-------------|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| 包访问 | ✅ | ❌ | ✅ | ❌ |
| `private` | ❌ | ❌ | ❌ | ❌ |

### 重点理解 protected

`protected` = 同包的都能用 + 不同包只有子类能用。

```java
// zoo/Animal.java
package zoo;

public class Animal {
    protected void breathe() {
        System.out.println("呼吸");
    }
}
```

```java
// zoo/Test.java（同包，非子类）
package zoo;

public class Test {
    public void test() {
        Animal a = new Animal();
        a.breathe();  // ✅ 同包，能用
    }
}
```

```java
// cat/Cat.java（不同包，子类）
package cat;

import zoo.Animal;

public class Cat extends Animal {
    public void test() {
        breathe();  // ✅ 不同包但我是子类，能用
    }
}
```

```java
// dog/Dog.java（不同包，非子类）
package dog;

import zoo.Animal;

public class Dog {
    public void test() {
        Animal a = new Animal();
        a.breathe();  // ❌ 不同包且不是子类，用不了
    }
}
```

### 继承的规则

```java
// 父类
package zoo;

public class Animal {
    public String name;

    // public 方法：子类继承并可以直接调用
    public void eat() {
        System.out.println(name + "在吃东西");
    }

    // private 方法：子类完全看不到
    private void secret() {
        System.out.println("秘密");
    }
}
```

```java
// 子类
package cat;

import zoo.Animal;

public class Cat extends Animal {
    public void test() {
        eat();      // ✅ 继承了父类的 public 方法
        // secret(); // ❌ 父类的 private 方法，子类看不到

        // 子类可以重写父类的方法
    }

    // 重写父类的 eat 方法
    @Override
    public void eat() {
        System.out.println(name + "在吃鱼");  // 改变了行为
    }
}
```

### 方法重写（Override）

子类可以重写父类的方法，改变其行为：

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
    @Override  // 标注重写，不是必须写，但建议写
    public void speak() {
        System.out.println("喵喵喵");  // 猫的叫声
    }
}
```

```java
// 使用
Cat c = new Cat();
c.speak();  // 输出：喵喵喵（调用的是子类重写后的方法）
```

> **重写 vs 重载：**
> - **重写（Override）**：子类重写父类的同名同参数方法，运行时决定调用哪个
> - **重载（Overload）**：同一个类中方法名相同但参数不同，编译时决定调用哪个

---

## 五、完整对比表

```java
// zoo/Animal.java
package zoo;

public class Animal {
    public String name;        // 谁都能用
    protected int age;         // 同包 + 子类能用
    String species;            // 只有同包能用
    private int id;            // 只有 Animal 类内部能用
}
```

| 修饰符 | 同包同类 | 同包其他类 | 不同包子类 | 不同包非子类 |
|--------|---------|-----------|-----------|-------------|
| `public` | ✅ | ✅ | ✅ | ✅ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| 包访问 | ✅ | ✅ | ❌ | ❌ |
| `private` | ✅ | ❌ | ❌ | ❌ |

---

## 六、实际开发中的使用建议

| 场景 | 推荐修饰符 | 原因 |
|------|-----------|------|
| 属性 | `private` | 封装，防止外部直接修改 |
| getter/setter 方法 | `public` | 提供外部访问属性的途径 |
| 对外提供的功能 | `public` | 任何人都能用 |
| 只在内部使用的工具方法 | `private` | 不暴露实现细节 |
| 允许子类重写的方法 | `protected` | 子类可以覆盖 |
| 不希望被继承的类 | `final class` | 配合 `final` 使用 |

### 典型的封装写法

```java
package zoo;

public class Cat {
    // 属性全部私有
    private String name;
    private int age;

    // 构造方法公开
    public Cat(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // getter/setter 公开（提供访问途径）
    public String getName() { return name; }
    public void setName(String name) {
        // 可以在这里加验证逻辑
        if (name != null && !name.isEmpty()) {
            this.name = name;
        }
    }

    public int getAge() { return age; }
    public void setAge(int age) {
        if (age > 0 && age < 30) {
            this.age = age;
        }
    }

    // 内部方法私有
    private void init() {
        this.name = "无名猫";
        this.age = 1;
    }
}
```

```java
// 外部使用
Cat c = new Cat("橘猫", 3);
c.setName("胖橘");       // ✅ 通过 setter 修改
c.name = "xxx";          // ❌ 不能直接修改（private）
System.out.println(c.getName());  // ✅ 通过 getter 读取
```

---

## 七、类的访问修饰符

类只能用 `public` 或无修饰（不能用 `private` 和 `protected`）：

```java
public class Cat { }      // ✅ 公开类，任何地方都能用
class Dog { }             // ✅ 包访问类，只有同包能用
// private class Bird { } // ❌ 类不能用 private
```

### 为什么类不能用 private

类的作用是给别人用的。如果一个类是 `private`，只有它所在的文件能用，那这个类就没有存在的意义了——还不如直接写在文件里。

`private` 用在类内部的属性和方法上才有意义——限制别人访问你的内部实现细节。

### 为什么类不能用 protected

`protected` 的含义是"同包 + 子类能用"。但类的继承关系在编译时就确定了，如果一个类是 `protected`，那它只能被同包的类或它的子类访问，这和包访问级别几乎一样，没有实际意义。
