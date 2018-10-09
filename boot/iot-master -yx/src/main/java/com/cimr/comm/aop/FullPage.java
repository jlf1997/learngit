package com.cimr.comm.aop;

import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD })
public @interface FullPage {
	String router() default "nav/";

	String layout() default "comm/layout/layout.vm";

	String menu();

	String subMenu() default "";
}
