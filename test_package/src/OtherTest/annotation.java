package test_package.src.OtherTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)//元注解Retention设置为RUNTIME，此为Annotation的生命周期
@Target({
    ElementType.FIELD,//元注解Target定义Annotation能够被应用于源码的位置
    ElementType.METHOD//另外还有构造方法CONSTRUCTOR、方法参数PARAMETER、类或接口TYPE等
})
public @interface annotation {
    int type() default 0;
    String level() default "info";
    String value() default "";
}
