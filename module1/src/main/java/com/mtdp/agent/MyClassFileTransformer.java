package com.mtdp.agent;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.LoaderClassPath;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/25
 */
public class MyClassFileTransformer implements ClassFileTransformer {

    private Transformer transformer = getTransformer();

    private static final byte[] EMPTY_BYTE_ARRAY = {};

    private Transformer getTransformer() {
        return new RunnableTransformer();
    }

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        String normalizedClassName = getNormalizedClassName(className);
        try {
            if (transformer.needTransform(normalizedClassName)) {
                CtClass ctClass = getCtClass(classfileBuffer, loader);
                transformer.doTransform(ctClass);
                return ctClass.toBytecode();
            }

        } catch (Throwable e) {
            String msg = "Fail to transform class " + className + ", cause: " + e.toString();
            throw new IllegalStateException(msg, e);
        }

        return EMPTY_BYTE_ARRAY;
    }

    private String getNormalizedClassName(String className) {
        return className.replaceAll("/", ".");
    }

    private CtClass getCtClass(byte[] classfileBuffer, ClassLoader loader) throws IOException {
        ClassPool classPool = ClassPool.getDefault();
        if (loader != null) {
            classPool.appendClassPath(new LoaderClassPath(loader));
        }

        CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classfileBuffer), false);
        //defrost class byte code ,which can be change again
        ctClass.defrost();
        //remove class file from classPool
        //节省内存占用
        ctClass.detach();
        return ctClass;
    }
}
