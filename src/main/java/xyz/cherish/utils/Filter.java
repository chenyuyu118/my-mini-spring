package xyz.cherish.utils;

/**
 * 过滤器
 *
 * @param <T>
 */
public interface Filter<T> {
    boolean accept(T t);
}
