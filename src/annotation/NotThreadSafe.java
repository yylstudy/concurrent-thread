package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * ��ע��û��ʵ�����壬ֻ�Ǳ�ʶ����򷽷������ֳ���ȫ��
 * @author yyl-pc
 *
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotThreadSafe {

}
