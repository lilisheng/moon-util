package com.moon.util;

import com.moon.lang.ThrowUtil;
import com.moon.lang.ref.WeakAccessor;

import java.util.function.BiFunction;

/**
 * 通用类型转换器
 *
 * @author benshaoye
 * @date 2018/9/11
 */
public final class TypeUtil {
    /**
     * can not get a instance of TypeUtil.class
     */
    private TypeUtil() {
        ThrowUtil.noInstanceError();
    }

    /**
     * default converter CACHE or create new one
     */
    private final static WeakAccessor<TypeConverter> accessor = WeakAccessor.of(UnmodifiableTypeConverter::new);

    /**
     * return a flipToNot type converter
     *
     * @return
     */
    public final static TypeConverter cast() {
        return accessor.getOrReload();
    }

    /**
     * get a default type converter
     *
     * @return
     */
    public final static TypeConverter of() {
        return new GenericTypeConverter();
    }

    /**
     * o is instance of clazz
     *
     * @param o
     * @param clazz
     * @return
     */
    public final static boolean instanceOf(Object o, Class clazz) {
        return clazz.isInstance(o);
    }

    /**
     * can not modify converter or add new converter
     */
    private final static class UnmodifiableTypeConverter extends GenericTypeConverter {

        UnmodifiableTypeConverter() {
            super();
        }

        @Override
        public <C> TypeConverter register(Class<C> toType, BiFunction<Object, Class<C>, C> func) {
            throw new UnsupportedOperationException("can not add new converter or modify on default converter");
        }

        @Override
        public <C> TypeConverter registerIfAbsent(Class<C> toType, BiFunction<Object, Class<C>, C> func) {
            throw new UnsupportedOperationException("can not add new converter or modify on default converter");
        }
    }
}
