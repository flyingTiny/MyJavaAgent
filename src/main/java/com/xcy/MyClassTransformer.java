package com.xcy;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

import java.lang.instrument.ClassFileTransformer;
import java.security.ProtectionDomain;


public class MyClassTransformer implements ClassFileTransformer {

//    private static final String CLASS_NAME_SLASH = "com/xcy/controller/HiController";
    private static final String CLASS_NAME_SLASH = "com/xcy/agent/Person";
    private static final String CLASS_NAME_DOT = CLASS_NAME_SLASH.replace('/', '.');

    @Override
    public byte[] transform(final ClassLoader loader, final String className, final Class<?> classBeingRedefined, final ProtectionDomain protectionDomain, final byte[] classfileBuffer) {
        // 操作Date类
        if (CLASS_NAME_SLASH.equals(className)) {
            try {
                // 从ClassPool获得CtClass对象
                final ClassPool classPool = ClassPool.getDefault();
                final CtClass clazz = classPool.get(CLASS_NAME_DOT);
                for (CtMethod method : clazz.getDeclaredMethods()) {
                    String logMethodName = clazz.getSimpleName() + "." + method.getName();
                    method.addLocalVariable("startTime", CtClass.longType);
                    method.insertBefore("startTime = System.currentTimeMillis();");
                    method.insertAfter("System.out.println(\"" + logMethodName + " cost:\"" + "+(System.currentTimeMillis() - startTime)+" + "\" ms.\");");
                }
                // 返回字节码，并且detachCtClass对象
                byte[] byteCode = clazz.toBytecode();
                //detach的意思是将内存中曾经被javassist加载过的Date对象移除，如果下次有需要在内存中找不到会重新走javassist加载
                clazz.detach();
                return byteCode;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        // 如果返回null则字节码不会被修改
        return null;
    }
}