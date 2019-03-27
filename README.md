# java-agent-test
java-agent-test example


module1 -> premain
module3 -> agentmain
        
        
module2引用module1测试premain方式修改字节码
该方式需要增加jvm的启动参数：
-Xbootclasspath/a:moudle1.jar具体路径 -javaagent:moudle1.jar具体路径
具体参见@link https://blog.csdn.net/wt_better/article/details/88832632

        
module2引用module3测试agentmain方式修改字节码
     
     
     
