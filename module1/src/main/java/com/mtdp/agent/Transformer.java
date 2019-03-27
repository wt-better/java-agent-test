package com.mtdp.agent;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.NotFoundException;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/25
 */
public interface Transformer {

    boolean needTransform(String className);

    void doTransform(CtClass ctClass) throws NotFoundException, CannotCompileException;
}
