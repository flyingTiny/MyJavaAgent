package com.xcy;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * file:///E:/Java/jdk-8u241-docs-all/api/java/lang/instrument/package-summary.html
 * @author xuchenyi
 * @date 2021/9/7 15:21
 * Starting Agents After VM Startup1
 */
public class AgentMain {

    public static void agentmain(String agentArgs, Instrumentation instrumentation) {
        instrumentation.addTransformer(new MyClassTransformer(), true);
//        instrumentation.addTransformer(new DefineTransformer(), true);
    }

    static class DefineTransformer implements ClassFileTransformer {

        @Override
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
            System.out.println("agentmain load Class:" + className);
            return classfileBuffer;
        }
    }
}