package com.xcy;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * @author xuchenyi
 * @date 2021/9/6 14:20
 * Command-Line Interface -javaagent:jarpath[=options]
 */
public class Premain {
    public static void premain(String agentArgs, Instrumentation instrumentation) {
        System.out.println("agentArgs : " + agentArgs);
//        instrumentation.addTransformer(new DefineTransformer(), true);
        instrumentation.addTransformer(new MyClassTransformer(), true);
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
            System.out.println("premain load Class:" + className);
            return classfileBuffer;
        }
    }
}
