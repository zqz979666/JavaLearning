package test_package.src.testjava;


public class testpackage {
    public static void main(String[] args){

        Person p = new Person("zqz",22);
        p.printPackage();

        //字符串格式转换
        System.out.println(String.valueOf(123));
        System.out.println(Integer.parseInt("324"));
        System.out.println(Integer.getInteger("java.version"));
        
    }

    public void hi(){
        System.out.println("you've accessed to me!");
    }

    public void formatString(){
        System.out.println(String.format("I am trying to print format string:%d", 1));
    }
}
