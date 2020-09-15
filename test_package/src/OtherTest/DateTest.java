package test_package.src.OtherTest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import java.time.*;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class DateTest {
    public static void main(String[] args) {
        long t = System.currentTimeMillis();
        System.out.println("the epoch time is : " + t);

        // JAVA标准库API
        // 旧：java.util ： Date、Calendar、TimeZone
        // 新：java.time ： LocalDateTime、ZonedDateTime、ZoneId

        // Date并不能转换时区，并且难以对日期和时间进行加减
        Date date = new Date();
        System.out.println(date.getYear() + 1900);
        System.out.println(date.getMonth() + 1);
        System.out.println(date.toString());
        System.out.println(date.toGMTString());
        System.out.println(date.toLocaleString());

        var sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf.format(date));

        // Calendar
        Calendar c = Calendar.getInstance();// 只能通过这种方式获取Calendar实例,并且获取的是当前时间
        int y = c.get(Calendar.YEAR);
        int m = 1 + c.get(Calendar.MONTH);
        int d = c.get(Calendar.DAY_OF_MONTH);
        System.out.println("now we use Calendar:");
        System.out.println(y + "-" + m + "-" + d);
        // 用Calendar.getTime()将Calendar对象转换为Date对象
        System.out.println(sdf.format(c.getTime()));

        // TimeZone
        // TimeZone提供了时区转换功能
        System.out.println("now we use TimeZone:");
        TimeZone tzDefault = TimeZone.getDefault();
        TimeZone tzGMT9 = TimeZone.getTimeZone("GMT+09:00");// GMT+9:00时区
        TimeZone tzNY = TimeZone.getTimeZone("America/New_York");// 纽约时区
        // TimeZone tzSH = TimeZone.getTimeZone("Asia/ShangHai");//上海时区
        // String[] tzAvailable = TimeZone.getAvailableIDs();

        System.out.println(tzDefault.getID());
        System.out.println(tzGMT9.getID());
        System.out.println(tzNY.getID());
        // for(String str : tzAvailable){//打印出所有可用时区
        // System.out.println(str);
        // }

        // 进行时区转换
        System.out.println("now we change the TimeZone: ");
        c.clear();// 清空Calendar
        c.setTimeZone(tzDefault);// 设置时区
        c.set(2020, 8 /* 即为9月 */ , 15, 14, 38, 0);// 设置年月日时分秒
        sdf.setTimeZone(tzNY);// 设定目标时区
        System.out.println(sdf.format(c.getTime()));

        // java.time API
        // 本地日期和时间：LocalDateTime、LocalDate、LocalTime
        // 带时区的日期和时间：ZonedDateTime
        // 时刻：Instant
        // 时区：ZoneId、ZoneOffset
        // 时间间隔：Duration
        // 格式化类型：DateTimeFormatter

        // LocalDateTime
        System.out.println("now we use LocalDateTime: ");
        LocalDate ld = LocalDate.now();
        LocalTime lt = LocalTime.now();
        LocalDateTime ldt = LocalDateTime.now();
        System.out.println(ld);// 严格按照ISO 8601格式打印
        System.out.println(lt);
        System.out.println(ldt);

        // 指定日期和时间
        LocalDateTime ldt1 = LocalDateTime.of(2016, 10, 1, 0, 0, 0);
        System.out.println(ldt1);
        LocalDateTime ldt2 = LocalDateTime.parse("2018-11-11T11:11:11");
        System.out.println(ldt2);
        System.out.println(ldt2.getYear());

        // DateTimeFormatter
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        System.out.println(dtf.format(LocalDateTime.now()));
        // 自定义格式解析字符串
        ldt2 = LocalDateTime.parse("2018/11/12 11:11:11", dtf);
        System.out.println(ldt2);

        // 时间的加减操作
        LocalDateTime ldt3 = ldt1.plusDays(5).minusHours(3);// 加减时间
        System.out.println(ldt3);
        LocalDateTime ldt4 = ldt3.withHour(8);// 调整时间
        System.out.println(ldt4);
        // 更为复杂的运算
        LocalDateTime firstDay = LocalDate.now().withDayOfMonth(1).atStartOfDay();// 本月第一天0：00时刻
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());// 本月最后一天
        LocalDate nextMonthFirstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfNextMonth());//下月第一天
        LocalDate firstWeekday = LocalDate.now().with(TemporalAdjusters.firstInMonth(DayOfWeek.MONDAY));//本月第一个周一

        System.out.println(firstDay);
        System.out.println(lastDay);
        System.out.println(nextMonthFirstDay);
        System.out.println(firstWeekday);
        

        //Duration和Period 
        //Duration表示两个时刻时间的时间间隔，Period表示两个日期之间的天数
        Duration d1 = Duration.between(ldt1, ldt2);
        System.out.println(d1);

        Period p1 = lastDay.until(nextMonthFirstDay);
        System.out.println(p1);





        System.out.println("============");//分割一下
        System.out.println("============");


        //ZonedDateTime
        ZonedDateTime zdt1 = ZonedDateTime.now();//默认时区
        ZonedDateTime zdt2 = ZonedDateTime.now(ZoneId.of("America/New_York"));
        //这种创建方法创建的是相同的时刻
        System.out.println(zdt1);
        System.out.println(zdt2);

        //另一种创建方法，给LocalDateTime附加一个ZoneId
        LocalDateTime ldtToZone = LocalDateTime.of(2019,9,15,15,16,17);
        ZonedDateTime zdt3 = ldtToZone.atZone(ZoneId.systemDefault());
        ZonedDateTime zdt4 = ldtToZone.atZone(ZoneId.of("America/New_York"));
        //LocalDateTime ldtBack = zdt3.toLocalDateTime();// 转换回来
        //这种创建方法的日期时间和LocalDateTime是一样的，但是时区不同，是两个不同时刻
        System.out.println(zdt3);
        System.out.println(zdt4);

        

        //DateTimeFormatter
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("E, yyyy-MM-dd HH:mm:ss",Locale.US);
        
        System.out.println(formatter1.format(ldt));
        System.out.println(formatter2.format(ldt));



        //获取当前时间戳
        //方法1,在前面已经定义过，用t表示
        //System.currentTimeMillis();

        //方法2 instant-时刻
        Instant now = Instant.now();
        System.out.println(now.toEpochMilli());//用毫秒表示
        System.out.println(now.getEpochSecond());//用秒表示
    }
}
