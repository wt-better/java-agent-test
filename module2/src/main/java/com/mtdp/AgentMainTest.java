package com.mtdp;

import com.sun.tools.attach.VirtualMachine;

import java.lang.management.ManagementFactory;

/**
 * @author <a href="wangte@meitaun.com">Te</a>
 * @date created at 2019/3/27
 */
public class AgentMainTest {

    public static void main(String[] args) throws Exception {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        //获取pid
        String pid = name.substring(0, name.indexOf("@"));

        VirtualMachine vm = VirtualMachine.attach(pid);
        vm.loadAgent("/Users/wangte/Develop/study/java-agent-test/module3/target/module3-1.0-SNAPSHOT.jar");
    }
}
