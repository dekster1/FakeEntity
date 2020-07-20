package me.newtondev.entity.query;

import me.newtondev.entity.util.ReflectionUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class QueryResult {

    private Class<?> clazz;

    public QueryResult(Class<?> clazz) {
        this.clazz = clazz;
    }

    public String getResult() {
        for (Method method : clazz.getMethods()) {
            Annotation annotation = method.getAnnotation(Query.class);

            if (annotation != null) {
                return (ReflectionUtil.versionLowerThan(getVersion(method))) ? getValue(method)[0] :
                        getValue(method)[1];
            }
        }
        return null;
    }

    private String[] getValue(Method method) {
        return method.getAnnotation(Query.class).result();
    }

    private String getVersion(Method method) {
        return method.getAnnotation(Query.class).version();
    }
}
