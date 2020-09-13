package test_package.src.OtherTest;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

//使用class ArrayList<T> implements List<T>来使用List<T>接口
class ArrayList<T>{
    private T[] array;
    private int size = 0;
    public void add(T e){
        array[size] = e;
        size++;
    }
    public void remove(int index){

    }
    public T get(int index){
        return array[index];
    }

    public ArrayList(T first,T last){
        //构造方法
    }


    //泛型类型T不能用于静态方法
    //静态泛型方法应该使用其他类型区分，这里的K和T没有任何关系
    public static <K> ArrayList<K> create(K first,K last){
        return new ArrayList<K>(first, last);
    }

    //java泛型的局限：1.<T>不能是基本类型。因为擦拭法，JVM看不到泛型的存在，编译器将T视为object
    //2.无法取得带泛型的class
    //3.无法判断带泛型的类型
    //4.不能实例化T类型

    //实例化T类型的方法
    //ArrayList<String> al = new ArrayList<>(String.class);
    public ArrayList(Class<T> cls){
        //first = cls.newInstance();
    }

    //静态方法通过? extends T ，将参数范围扩大到T以及T的子类
    //extends通配符
    //super通配符
    static int add(ArrayList<? extends Number> n){
        System.out.println("I'm the super");
        return 0;
    }



}


//泛型类的继承

class ArrayListSon extends ArrayList<Integer> {

    public ArrayListSon(Class<Integer> cls) {
        super(cls);
        // 构造方法
    }

}


public class template {
    public static void main(String[] args){
        Class<ArrayListSon> clazz = ArrayListSon.class;
        Type t = clazz.getGenericSuperclass();
        if(t instanceof ParameterizedType){
            ParameterizedType pt = (ParameterizedType) t;
            Type[] types = pt.getActualTypeArguments();
            Type firstType = types[0];
            Class<?> typeClass = (Class<?>) firstType;
            System.out.println(typeClass);
        }
    }
}
