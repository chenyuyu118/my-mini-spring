package xyz.cherish.aop.aspectj;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import xyz.cherish.aop.ClassFilter;
import xyz.cherish.aop.MethodMatcher;
import xyz.cherish.aop.Pointcut;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 验证是否匹配切点表达式，包含切点表达式解析能力
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>(); // 切点的支持Set

    static {
        // 初始化支持的primitive
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION); // 支持执行方法
    }

    private final PointcutExpression pointcutExpression; // 初始化的切点表达式

    public AspectJExpressionPointcut(String expression) {
        PointcutParser pointcutParse = PointcutParser.
                getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(
                        SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        pointcutExpression = pointcutParse.parsePointcutExpression(expression);
    }

    @Override
    public boolean matches(Class<?> clazz) {
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }

    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }

    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
