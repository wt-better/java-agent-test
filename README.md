# java-agent-test

java-agent-test example

###(module1 -> premain)
module2引用module1测试premain方式修改字节码

该方式需要增加jvm的启动参数：

-Xbootclasspath/a:moudle1.jar具体路径 -javaagent:moudle1.jar具体路径
具体参见@link https://blog.csdn.net/wt_better/article/details/88832632

最好不要直接指定moudle1.jar的版本，避免jar包升级时找不到包，可以按如下配置例子，
写在run.sh脚本里：

<code>
    cd $WEB_ROOT
    ...
    # 如果没有 WEB-INF/lib/ 目录，需要改成其它路径，比如 target/lib
    MTRACE_AGENT=`ls WEB-INF/lib/ | grep mtrace-agent`
    MTRACE_AGENT_PATH="WEB-INF/lib/${MTRACE_AGENT}"
    JAVA_AGENT="-Xbootclasspath/a:${MTRACE_AGENT_PATH} -javaagent:${MTRACE_AGENT_PATH}"
    ...
    EXEC_JAVA="$EXEC $JAVA_CMD $JAVA_AGENT $JVM_ARGS $JVM_SIZE $JVM_HEAP $JVM_JIT $JVM_GC"\
    ...
</code>



###(module3 -> agentmain)

module2引用module3测试agentmain方式修改字节码


