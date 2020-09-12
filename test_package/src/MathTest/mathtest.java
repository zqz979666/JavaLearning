package test_package.src.MathTest;

import java.util.Random;
//import java.security.SecureRandom;

public class mathtest {
    public static void main(String[] args){
        System.out.printf("abs of %d is : %d\n",-100,Math.abs(-100));
        System.out.printf("square of %d is : %.0f\n",4,Math.pow(4,2));//x^y
        System.out.printf("sqrt of %d is : %.0f\n",4,Math.sqrt(4));//开方
        //exp : e的x次方
        //log ：ln
        //log10 ： lg
        //random : 生成随机数x 范围是[0,1) 无法指定种子

        //SecureRandom sr = new SecureRandom();
        //SecureRandom无法指定种子，使用Random Number Generator算法
        Random r = new Random();
        System.out.println(r.nextInt(10));//生成[0,10)之间的int


        //JAVA的反射 所有class和interface都会有一个Class实例
        //JVM动态加载class，只有当class被使用时才会被加载到内存
        // printClassInfo("".getClass());
        // printClassInfo(Runnable.class);
        // printClassInfo(java.time.Month.class);
        // printClassInfo(String[].class);
        // printClassInfo(int.class);

        //class中获取字段
        //Class intcls = int.class;
        //System.out.println(intcls.getFields());//包括父类
        //getField（包括父类） getDeclaredField（不包括父类）



    }

    static void printClassInfo(Class cls){
        System.out.println("Class name: " + cls.getName());
        System.out.println("Simple name: " + cls.getSimpleName());
        if(cls.getPackage()!=null){
            System.out.println("Package name: " + cls.getPackage().getName());
        }
        System.out.println("is interface: " + cls.isInterface());
        System.out.println("is enum: " + cls.isEnum());
        System.out.println("is array: " + cls.isArray());
        System.out.println("is primitive: " + cls.isPrimitive());
    }
    


}
