package com.mtdp.agent;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/25
 */
public class RunnableTransformer implements Transformer {

    private static final String RUNNABLE_CLASS_NAME = "java.lang.Runnable";

    /**
     * java.lang.NoClassDefFoundError: com/mtdp/agent/MyRunnable ? ? ?
     * <p>
     * MyRunnable.class.getName() ?
     * <p>
     * 都是APPClassLoader进行加载的
     */
    private static final String TTL_RUNNABLE_CLASS_NAME = MyRunnable.class.getName();

    private static final Set<String> UPDATE_METHOD_NAME = Collections.unmodifiableSet(getMethodNameSet());

    private static Set<String> getMethodNameSet() {
        Set<String> set = new HashSet<>();
        set.add("submit");
        set.add("execute");
        set.add("schedule");
        set.add("scheduleAtFixedRate");
        set.add("scheduleWithFixedDelay");
        return set;
    }

    @Override
    public boolean needTransform(String className) {
        //注意：ctClass.getClass获取到的Class对象全部是class javassist.CtClassType，所以不鞥根据该class判断
        return "java.util.concurrent.ThreadPoolExecutor".equals(className);
    }

    @Override
    public void doTransform(CtClass ctClass) throws NotFoundException, CannotCompileException {
        CtMethod[] declaredMethods = ctClass.getDeclaredMethods();

        for (CtMethod declaredMethod : declaredMethods) {
            updateMethodIfNecessary(declaredMethod);
        }
    }

    private void updateMethodIfNecessary(CtMethod declaredMethod) throws NotFoundException, CannotCompileException {
        String methodName = declaredMethod.getName();
        int modifiers = declaredMethod.getModifiers();

        if (Modifier.isPublic(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isStatic(modifiers)) {
            if (UPDATE_METHOD_NAME.contains(methodName)) {
                CtClass[] parameterTypes = declaredMethod.getParameterTypes();
                for (int i = 0; i < parameterTypes.length; i++) {
                    CtClass parameterType = parameterTypes[i];
                    //动态替换参数类型
                    if (RUNNABLE_CLASS_NAME.equals(parameterType.getName())) {
                        String src = String.format("$%d = %s.getInstance($%d);", i + 1, TTL_RUNNABLE_CLASS_NAME, i + 1);
                        declaredMethod.insertBefore(src);
                    }
                }

            }
        }
    }


}
