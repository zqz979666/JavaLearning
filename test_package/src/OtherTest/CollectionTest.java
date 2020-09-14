package test_package.src.OtherTest;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.logging.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

//java.util定义了List、Set、Map三种类型的集合，都是接口，如ArrayList、LinkedList是List的具体实现
//List有序列表 Set无重复元素 Map键值表


//自定义类
class People{
    public String name;
    public int age;

    //重新编写equals()方法
    //equals()必须具有自反性、对称性、传递性、一致性
    //并且equals(null)永远返回false
    public boolean equals(Object o){
        if(o instanceof People){
            People p = (People) o;
            // boolean nameEquals = false;
            // if(this.name == null && p.name == null){
            //     nameEquals = true;
            // }
            // if(this.name != null){
            //     nameEquals = this.name.equals(p.name);
            // }
            // return nameEquals && this.age == p.age;

            //上述方法过于复杂，对引用类型，使用Objects.equals()静态方法更方便
            return Objects.equals(this.name, p.name) && this.age == p.age;

        }
        return false;
    }

    // 如果自定义类作为Map的Key，那么应该正确覆写equals（同上）和hashCode()
    // 必须保证两个实例相等，那么它们的hashCode也必须相同
    @Override
    public int hashCode() {
        // int h = 0;
        // h = 31 * h + name.hashCode();
        // h = 31 * h + age;
        // return h;
        //上面的方法没有考虑到如果name是null，那么就会有NullPointerException
        return Objects.hash(name,age);
    }



}


//枚举类
enum DayOfWeek {
    MON,TUE,WED,THU,FRI,SAT,SUN;
}



public class CollectionTest {
    public static void main(String[] args){
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("pear");
        list.add("banana");
        list.add("apple");
        list.add(null);
        System.out.println(list.size());
        System.out.println(list.get(2));
        System.out.println(list.get(4));

        //直接创建List
        //这种方法不能传入null，否则会throw NullPointerException
        List<Integer> list1 = List.of(1,2,3,4,5);
        Logger log = Logger.getGlobal();
        log.info("this is accessed by for: ");
        for(int i : list1){//使用for循环来遍历过于复杂，并且不够高效
            System.out.println(i);
        }
        log.info("this is accessed by iterator");
        for(Iterator<Integer> it = list1.iterator(); it.hasNext() ; ){
            int tmp = it.next();
            System.out.println(tmp);
        }


        //List和Array的转换
        //List->Array
        Integer[] array1 = list1.toArray(new Integer[list1.size()]);
        //写法2
        Integer[] array2 = list1.toArray(Integer[]::new);

        //Array->List
        Integer[] array3 = {6,7,8};
        List<Integer> list2 = List.of(array3);
        //list2.add(999);//UnsupportedOperationException，List只是一个接口，返回的是一个只读List。



        //Map接口，常用实现类是HashMap
        Map<String,Integer> map1 = new HashMap<>();
        map1.put("Apple",1);
        map1.put("Banana",2);
        map1.put("Orange",3);
        System.out.println(map1.get("Orange"));
        System.out.println(map1.get("Apple"));
        System.out.println(map1.get("Strawberry"));//对不存在的返回null
        //Map中键值一一对应，如果再度使用put，将会覆盖原先的value


        //Map的遍历 for each循环遍历keySet()
        //通过输出可以看出map是不保证顺序的，使用Map时，任何依赖顺序的逻辑都是不可靠的
        log.info("map access by for each: ");
        for(String key : map1.keySet()){
            Integer value = map1.get(key);
            System.out.println(key + " = " + value);
        }

        log.info("map access of entrySet: ");
        for(Map.Entry<String,Integer> entry : map1.entrySet()){
            String key = entry.getKey();
            Integer value = entry.getValue();
            System.out.println(key + " = " + value);
        }







        //枚举类EnumMap
        Map<DayOfWeek,String> map2 = new EnumMap<>(DayOfWeek.class);
        map2.put(DayOfWeek.MON, "月曜日");
        map2.put(DayOfWeek.TUE, "火曜日");
        map2.put(DayOfWeek.WED, "水曜日");
        map2.put(DayOfWeek.THU, "木曜日");
        map2.put(DayOfWeek.FRI, "金曜日");
        map2.put(DayOfWeek.SAT, "土曜日");
        map2.put(DayOfWeek.SUN, "日曜日");
        log.info("EnumMap established! ");
        System.out.println(map2);
        System.out.println(map2.get(DayOfWeek.MON));



        //配置文件使用String-String Map,JAVA集合库提供了Properties来表示一组配置,其本质上是Hashtable
        //String file = "D:\\javawork\\test_package\\src\\OtherTest\\testsetting.properties";//绝对路径访问
        String file = "test_package/src/OtherTest/testsetting.properties";//相对路径访问
        Properties props = new Properties();
        try{

            //load方法默认以ASCII编码读取字节流，中文会读到乱码，因此需要另一个重载方法load(Reader读取)
            //props.load(new FileReader(file,StandardCharsets.UTF_8));//中文配置读取
            props.load(new java.io.FileInputStream(file));
            //也可以是从jar包中读取的资源流
            //props.load(getClass().getResourceAsStream("/common/setting.properties"))//虚构文件

        }catch(IOException e){
            e.printStackTrace();
        }
        String filepath = props.getProperty("last_open_file");
        String interval = props.getProperty("auto_save_interval","120");

        log.warning("trying to read properties file: ");
        System.out.println("filepath: " + filepath);
        System.out.println("interval: " + interval);


        //设置属性并将其写回
        props.setProperty("url", "https://www.pornhub.com");
        props.setProperty("language", "Java");
        try{
            props.store(new FileOutputStream(file),"");
            log.warning("new properties have been written! ");
        }catch(IOException e){
            e.printStackTrace();
        }







        //不重复的集合set
        log.info("Operation of set: ");
        Set<String> set = new HashSet<>();
        System.out.println(set.add("abc"));
        System.out.println(set.add("def"));
        System.out.println(set.add("def"));
        System.out.println("does set contain abc? " + set.contains("abc"));


        //FIFO队列 Queue
        log.info("Operation of Queue: ");
        Queue<String> q = new LinkedList<>();
        q.offer("jonason");//也可以用add，add添加失败会抛出exception
        q.offer("joseph");//offer添加失败则会返回boolean
        q.offer("jotaro");
        q.offer("josuke");
        q.offer("joruno");

        System.out.println(q.poll());//取出队列头元素并且删除，也可用remove
        System.out.println(q.poll());
        System.out.println(q.poll());
        System.out.println(q.peek());//只获得队列头元素，并不删除，也可用element
        System.out.println(q.peek());//依然还是上一个结果
        System.out.println("The rest number of Queue:" + q.size());


    }
}
