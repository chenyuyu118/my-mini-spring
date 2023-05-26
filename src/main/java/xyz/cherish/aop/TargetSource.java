package xyz.cherish.aop;

/**
 * 目标类的包装器
 */
public class TargetSource {

    private final Object target;

    public TargetSource(Object target) {
        this.target = target;
    }

    /**
     * 获取所有的实现接口
     *
     * @return 目标类的所有实现
     */
    public Class<?>[] getTargetClass() {
        return target.getClass().getInterfaces();
    }

    public Object getTarget() {
        return target;
    }
}
