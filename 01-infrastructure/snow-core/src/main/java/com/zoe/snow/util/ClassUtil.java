package com.zoe.snow.util;

import javassist.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ConstPool;
import javassist.bytecode.FieldInfo;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.BooleanMemberValue;
import javassist.bytecode.annotation.StringMemberValue;

//import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * ClassUtil
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/8/11
 */
public class ClassUtil {
    public static Object changeAnnotationValue(Annotation annotation, String key, Object newValue) {
        Object handler = Proxy.getInvocationHandler(annotation);
        Field f;
        try {
            f = handler.getClass().getDeclaredField("memberValues");
        } catch (NoSuchFieldException | SecurityException e) {
            throw new IllegalStateException(e);
        }
        f.setAccessible(true);
        Map<String, Object> memberValues;
        try {
            memberValues = (Map<String, Object>) f.get(handler);
        } catch (IllegalArgumentException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
        Object oldValue = memberValues.get(key);
        if (oldValue == null || oldValue.getClass() != newValue.getClass()) {
            throw new IllegalArgumentException();
        }
        memberValues.put(key, newValue);
        return oldValue;
    }

    public static void changeAnnotationValue(String className, String annotationType, String fieldName, String key, Object value) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        //获取要修改的类的所有信息
        CtClass ct = pool.get(className);
        /*//获取类中的方法
        CtMethod[] cms = ct.getDeclaredMethods();
        //获取第一个方法（因为只有一个方法）
        CtMethod cm = cms[0];
        System.out.println("方法名称====" + cm.getName());
        //获取方法信息
        MethodInfo methodInfo = cm.getMethodInfo();*/
        //获取类里的em属性
        CtField cf = ct.getField(key);
        //获取属性信息
        FieldInfo fieldInfo = cf.getFieldInfo();
        ConstPool cp = fieldInfo.getConstPool();
        //获取注解属性
        AnnotationsAttribute attribute = (AnnotationsAttribute) fieldInfo.getAttribute(AnnotationsAttribute.visibleTag);
        //获取注解
        Annotation annotation = attribute.getAnnotation(annotationType);
        //修改名称为unitName的注解
        if (value instanceof Boolean)
            annotation.addMemberValue(key, new BooleanMemberValue(Boolean.parseBoolean(value.toString()), cp));
        attribute.setAnnotation(annotation);
    }

}