package com.mtdp.agent;

import java.lang.instrument.Instrumentation;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/25
 */
public class Agent {

    private Agent() {
        throw new InstantiationError("Must not instantiate this class");
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        install(agentArgs, instrumentation);
    }

    private static void install(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MyClassFileTransformer());
    }
}
