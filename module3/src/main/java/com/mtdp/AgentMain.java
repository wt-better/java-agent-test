package com.mtdp;

import java.lang.instrument.Instrumentation;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/27
 */
public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation inst) {
        System.out.println("agentmain...");
    }
}
