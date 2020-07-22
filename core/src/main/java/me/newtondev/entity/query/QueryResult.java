package me.newtondev.entity.query;

import me.newtondev.entity.util.MinecraftVersion;
import me.newtondev.entity.util.ReflectionUtil;

import java.lang.reflect.Method;

public class QueryResult {

    private Class<?> check;

    public QueryResult(Class<?> check) {
        this.check = check;
    }

    public String getResult() {
        for (Method method : check.getMethods()) {
            if (method.isAnnotationPresent(Query.class)) {

                int current = 0;
                for (String version : getVersions(method)) {
                    ++current;

                    if (MinecraftVersion.valueOf(version).lowerOrEqualsThan(ReflectionUtil.getVersion())) {
                        return getResults(method)[current-1];
                    }
                }
            }
        }
        return null;
    }

    private String[] getResults(Method method) {
        return (method.getAnnotation(Query.class)).results();
    }

    private String[] getVersions(Method method) {
        return (method.getAnnotation(Query.class)).versions();
    }
}
