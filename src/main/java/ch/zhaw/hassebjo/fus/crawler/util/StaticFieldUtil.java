package ch.zhaw.hassebjo.fus.crawler.util;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public class StaticFieldUtil {

	/**
	 * Read public static fields of type T from the given class.
	 * 
	 * @param <T>
	 * @param fieldType
	 * @param clazz
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> Set<T> read(Class<T> fieldType, Class<?> clazz) {
		return Arrays.asList(clazz.getDeclaredFields()).stream()
				.filter(f -> Modifier.isStatic(f.getModifiers()) && fieldType.equals(f.getType())).map(f -> {
					try {
						return (T) f.get(null);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						throw new RuntimeException(e);
					}
				}).collect(Collectors.toSet());
	}

}
