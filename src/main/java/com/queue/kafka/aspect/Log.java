package com.queue.kafka.aspect;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/*
 * Marker for {@link org.slf4j.Logger} injection.
 */
@Retention(RUNTIME)
@Target(FIELD)
@Documented
public @interface Log {
	/**
	 * Specifies logger name. Injected logger will be named corresponding to the
	 * class passed as parameter. The default class is the class of a bean where
	 * injection takes place.
	 */
	Class<?> fromClass() default DEFAULT.class;

	/**
	 * Create as a default value for {@link Log#fromClass()}.
	 */
	static class DEFAULT {
	}
}
