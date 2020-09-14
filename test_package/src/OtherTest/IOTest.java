package test_package.src.OtherTest;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class IOTest {
    public static void main(String[] args){
        //IO流以Byte为最小单位，因此被称为字节流
        //InputStream代表输入字节流，OutputStream代表输出字节流

        //如果需要读写的是字符，那么用char读写更方便，称为字符流
        //JAVA使用Reader和Writer表示字符流，传输的最小数据单位是char

        //java.io提供同步IO java.nio提供异步IO
        //InputStream、OutputStream、Reader、Writer都是同步IO的抽象类
        //对应的具体实现类有FileInputStream、FileOutputStream、FileReader、FileWriter等


        System.out.println(File.separator);//获得当前系统的分隔符

        File f1 = new File("..");
        System.out.println(f1.getPath());//构造方法传入的路径
        System.out.println(f1.getAbsolutePath());//绝对路径
        try{
            System.out.println(f1.getCanonicalPath());//规范路径
        }catch(IOException e){
            e.printStackTrace();
        }

        System.out.println("Is f1 a directory? " + f1.isDirectory());
        System.out.println("Is f1 a file? " + f1.isFile());


        //创建临时文件
        try{
            File f2 = File.createTempFile("tmptest-", ".txt");
            f2.deleteOnExit();
            System.out.println(f2.isFile());
            System.out.println(f2.getAbsolutePath());
        }catch(IOException e){
            e.printStackTrace();
        }


        //遍历文件和目录
        File[] fs1 = f1.listFiles();
        printFiles(fs1);
        File[] fs2 = f1.listFiles(new FilenameFilter(){//过滤不想要的文件和目录，选择性遍历
            public boolean accept(File dir,String name){
                return name.endsWith(".exe");//返回true表示接受该文件
            }
        });
        printFiles(fs2);


        //使用ByteArrayInputStream在内存中模拟InputStream
        byte[] dataStream = { 72,101,108,108,111,33};
        try(InputStream is = new ByteArrayInputStream(dataStream)){//使用try(resource)，编译器会自动关闭资源（input.close()）
            int n;
            while((n = is.read()) != -1){
                System.out.println((char)n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        //用字节数组来一次性写入多个字节
        try{
            OutputStream output = new FileOutputStream(".\\test_package\\src\\OtherTest\\io.txt");//从这可以知道vscode的路径是从根目录开始的
            output.write("Hello World!".getBytes("UTF-8"));
            output.close();
            System.out.println("the byte stream has been written into io.txt!");
        }catch(IOException e){
            e.printStackTrace();
        }




        // //读取zip包
        // try(ZipInputStream zip = new ZipInputStream(new FileInputStream(f1))) {
        //     ZipEntry entry = null;//表示一个压缩文件或目录
        //     while((entry = zip.getNextEntry()) != null){
        //         String name = entry.getName();;
        //         if(!entry.isDirectory()){
        //             int n;
        //             System.out.println(name);
        //             while((n = zip.read()) != -1){
        //                 System.out.println(n);
        //             }

        //         }
        //     }
        // }catch(FileNotFoundException e){
        //     e.printStackTrace();
        // }catch(IOException e){
        //     e.printStackTrace();
        // }
        // //写入zip包
        // try(ZipOutputStream zipout = new ZipOutputStream(new FileOutputStream("out.zip"))){
        //     File[] filesOfZip = {new File("")};
        //     for(File file : filesOfZip){
        //         zipout.putNextEntry(new ZipEntry(file.getName()));
        //         //zipout.write(getFileDataAsBytes(file));
        //         zipout.closeEntry();
        //     }
        // }catch(FileNotFoundException e){
        //     e.printStackTrace();
        // }catch(IOException e){
        //     e.printStackTrace();
        // }


        



        //字符流的输入输出
        System.out.println("now we use Reader: ");
        try{
            Reader reader1 = new FileReader("./test_package/src/OtherTest/io.txt",StandardCharsets.UTF_8);
            for(;;){
                int n = reader1.read();
                if(n == -1){
                    break;
                }
                System.out.println((char)n);
            }
            reader1.close();

        }catch(IOException e){
            e.printStackTrace();
        }





    }


    //自定义方法打印文件
    private static void printFiles(File[] files) {
        System.out.println("==========");
        if(files != null){
            for(File f : files){
                System.out.println(f);
            }
        }
        System.out.println("==========");
    }


    public void getClasspathInput(){
        //读取classpath资源
        //classpath中的资源文件总是以/开头
        //在静态方法main中无法调用非静态方法getClass，因此重新定义一个非静态方法
        try(InputStream classpathInput = getClass().getResourceAsStream("/default.properties")){
            if(classpathInput != null){
                // TODO:READ THE FILE
            }
        }catch(IOException e){
            e.printStackTrace();
        }

    }

}
