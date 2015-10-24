package insynctive.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import insynctive.utils.ParamObjectField;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD) //can use in method only.
public @interface ParametersFront {
	public ParamObjectField[] attrs(); 
	public String[] labels() default "";
}
