package com.zoe.snow.util;


/**
 * ParameterHelper
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2017/1/12
 */
public class ParameterHelper {
    /*public static String getParameterName(String clazzName, String methodName, int ndx)
            throws NotFoundException {
        String[] paramNames = getParameterNames(clazzName, methodName);
        if (paramNames.length >= ndx)
            return paramNames[ndx];
        return "";
    }

    public static String[] getParameterNames(String clazzName, String methodName) throws NotFoundException {
        ClassPool pool = ClassPool.getDefault();
        ClassClassPath classPath = new ClassClassPath(this.getClass());
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute
                .getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < paramNames.length; i++)
            paramNames[i] = attr.variableName(i + pos);
        return paramNames;
    }*/
}
