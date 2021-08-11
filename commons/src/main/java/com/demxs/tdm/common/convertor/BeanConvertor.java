package com.demxs.tdm.common.convertor;

public interface BeanConvertor<T, E> {
    /**
     * 属性转换器
     *
     * @param orig 源对象
     * @param dest 目标对象
     */
    void convertBean(final T orig, final E dest);
}
