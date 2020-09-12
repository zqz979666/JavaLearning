package test_package.src.testjava;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.Arrays;
//import java.util.Scanner;
import java.util.StringJoiner;

class Person {
    private String name;// 一个变量一个field
    protected int age;
    public static int num;// 静态字段，每个实例共享
    // 最好用类名而不是用实例名访问静态字段 static field

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return this.name;
    }

    public int getAge() {
        return this.age;
    }

    public boolean setName(String name) {
        this.name = name;
        if (this.name.equals(name)) {
            return true;
        } else
            return false;
    }

    public void setAge(int age) {
        if (age <= 100 && age >= 0)
            this.age = age;
        printAge(age);
    }

    private void printAge(int age) {
        System.out.println("setAge:I've been used!");
    }

    // public abstract void printInfo();//若有抽象方法，则该类也必须为抽象类

    // static方法
    // 静态方法属于class而不属于实例，静态方法内部无法访问this变量和实例字段，只能访问静态字段
    public static void setNum(int value) {
        num = value;
    }

    // 此函数属于包的作用域（不用public、protected、private修饰的字段和方法）
    void printPackage() {
        System.out.println("this is in the package of \"test_package\"");
    }

}

// java实例方法的调用是基于运行时的实际类型的动态调用
// 多态：针对某个类型的方法调用，其真正执行的方法取决于运行时期实际类型的方法

class Student extends Person {

    private int score;

    // 子类不会继承任何父类的构造方法。
    public Student(String name, int age, int score) {// 在java中，任何class的构造方法，第一行语句必须是调用父类的构造方法；
        super(name, age);

        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override // 帮助检查是否进行了正确的覆写override
    public int getAge() {
        System.out.println("I print the age int the override function!");
        return age;
    }
}
/*
 * object几个重要方法: toString() equals() hashCode()
 * 
 */

// interface 终极抽象，不能有字段field
// 在interface里面定义的方法不需要写，默认是public abstract
interface Book {
    void printISBN();

    default void printBookname() {
        System.out.println("the book name");
    }

    public static final int SF = 1;
    // 接口的静态字段，只能是public static final
    // 因此可以省略
    int CB = 2;
}

// 接口继承
interface Ebook extends Book {
    void printeISBN();
}

class ScienceFiction implements Book {// 一个类可以使用多个interface，但只能有一个父类
    private String isbn;

    @Override
    public void printISBN() {
        System.out.println(isbn);
    }

    public ScienceFiction(String isbn) {
        this.isbn = isbn;
    }
}

// JavaBean类
// class Beantest{
// private String bean;
// private int bean_num;
// }


//枚举类
//定义的enum类型总是继承自java.lang.Enum，无法被继承，无法通过new创建
//定义的每个实例都是引用类型的唯一实例
enum Weekday {
    SUN(7),MON(1),TUE(2),WED(3),THU(4),FRI(5),SAT(6);

    //为枚举常量添加字段
    public final int dayValue;

    private Weekday(int dayValue){
        this.dayValue = dayValue;
    }
    //enum的toString()可以被覆写,但name()不行
    @Override
    public String toString(){
        return this.name();
    }
}

//记录类——不变类
record Point(int x,int y,int z){
    public Point{//在构造方法中加入检查逻辑
        if(x < 0 || y < 0 || z < 0){
            throw new IllegalArgumentException();
        }
    }
    //在记录类内仍然可以添加静态方法
    public static Point of(){
        return new Point(0,0,0);
    }
}




public class firstjava {
    public static void main(final String[] args) throws IntrospectionException {
        final int x = 233;
        //final float PI = 3.14f;
        final String str1 = """
                    test my
                    plural line of
                    strings
                    """;
        //String str2 = "zhangqingzhu";
        int[] arr = new int[] {22,33,44,55,66,77};

        //Scanner scn = new Scanner(System.in);
        //String my_name = scn.nextLine();
        //scn.close();
        //boolean b = true || (5/0 == 0); 
        System.out.println("hello world\n");
        System.out.println(x);// 常量int x
        System.out.println(str1);// 常量string str1
        //System.out.println(my_name);// 输入变量 my_name
        System.out.println(Arrays.toString(arr));// 数组转字符串,快速打印

        // if (str2.equals(my_name)) {
        //     System.out.println("that equals bro!");
        // }
        //System.out.println(b);


        //object-oriented programming
         
        Person zqz = new Person("qingzhu",21);
        //zqz.name = "zhangqingzhu";
        //zqz.age = 22;
        zqz.setAge(22);
        zqz.setName("zhangqingzhu");
        String nam = zqz.getName();
        int ag = zqz.getAge();
        System.out.printf("the name is: %s\n",nam);
        System.out.printf("the age is: %d\n",ag);
        System.out.println(zqz instanceof Person);

        //通过更抽象的类来引用
        Book bk1 = new ScienceFiction("0111-2asf-qwed");
        bk1.printISBN();
        //Student std = new Student("jjc",22,99);
        //int the_age = std.getAge();

        //包装类 包装类都是不变类
        //所有整数、浮点数的包装类都继承自Number
        //number.intValue() number.char
        int i = 99;
        //Integer it1 = new Integer(i);//最好不用new创建
        Integer it2 = Integer.valueOf(i);//静态工厂方法，尽可能返回缓存的实例以节省内存
        //创建新对象时，优先使用静态工厂方法而不是new操作符
        System.out.println(it2.intValue());
        System.out.println(Integer.toBinaryString(i));
        
        
        //StringBuilder 和 StringJoiner
        String[] str3 = {"zqz","jjc","czl","cwl","xy"};
        var sb = new StringBuilder();
        sb.append("pussyhub: ");
        for(String name:str3){
            sb.append(name);
            sb.append(" , ");
        }
        sb.delete(sb.length()-3, sb.length());
        sb.append("  --by StringBuilder");
        var sj = new StringJoiner(" , ","pussyhub: ","  --by StringJoiner");
        for(String name:str3){
            sj.add(name);
        }
        System.out.println(sb.toString());
        System.out.println(sj.toString());


        //Student是一个Java bean,用Introspector来枚举JavaBean所有属性
        BeanInfo binfo = Introspector.getBeanInfo(Student.class);
        for(PropertyDescriptor pd : binfo.getPropertyDescriptors()){
            System.out.println(pd.getName());
            System.out.println("  " + pd.getReadMethod());
            System.out.println("  " + pd.getWriteMethod());
        }


        Weekday day = Weekday.MON;
        if(day == Weekday.MON)//enum的每个常量在JVM只有一个唯一实例，因此可以用==比较
        {
            System.out.println("today is monday!");
            System.out.println(day.name());//显示enum名称
            System.out.println(day.ordinal());//显示enum顺序，从0开始计数
            System.out.println(day.dayValue);//显示enum常量自定义字段
        }



    }
}