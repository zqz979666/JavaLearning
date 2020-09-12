package test_package.src.errortest;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

//JAVA标准库日志
//import java.util.logging.Level;
import java.util.logging.Logger;

//import org.apache.commons.logging.Log;

//throwable->error & exception
//error:terrible error  eg:OutOfMemory\NoClassDefFound\StackOverflow
//exception:runtime error, catchable  eg:NumberFormat\FileNotFound\Socket\NullPointer\IndexOutOfBounds
//exception->RuntimeException & NonRuntimeException(eg:IOException\ReflectiveOperationException)
//
//must be catched:Exception(and its subclass ,but not include RuntimeException and its subclass)   which called Checked Exception
//don't have to be catched:error(and its subclass)\RuntimeException(and its subclass)
public class error {
    public static void main(String[] args){
        byte[] bs = toGBK("中文");
        System.out.println(Arrays.toString(bs));
        //断言
        //int ass = -1;
        //assert ass > 0;

        //JAVA标准库日志
        Logger logger = Logger.getGlobal();
        logger.info("Start testing...");
        logger.warning("I'm going to die in 10 minutes!");
        logger.fine("ignored.");
        logger.severe("process is going to be terminated...");
        //日志7个级别   <----严重            一般              普通---->
        //            SEVERE WARING INFO(默认) CONFIG FINE FINER FINEST
        //INFO级别以下的日志不会被打印出来（INFO以右）
    }

    static byte[] toGBK(String s){
        try{
            return s.getBytes("GBK");
        }catch(UnsupportedEncodingException e){
            System.out.println(e);
            e.printStackTrace();
        }finally{//无论什么情况下都会调用，且在最后执行
            System.out.println("I will be executed anyway!");
        }
        return null;
    }

    


}


//自定义异常
class BaseException extends RuntimeException{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public BaseException() {
        super();
    }

    public BaseException(String message,Throwable cause){
        super(message,cause);
    }

    public BaseException(String message){
        super(message);
    }

    public BaseException(Throwable cause){
        super(cause);
    }
}