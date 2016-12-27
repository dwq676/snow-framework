package org.jboss.resteasy.core;

import com.zoe.snow.log.Logger;
import org.apache.commons.beanutils.ConvertUtils;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 默认注入器
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2016/12/22
 */
public class DefaultInjector implements ValueInjector {
    private Class type;
    private Type genericType;
    private String paramName;
    private Annotation[] annotations;

    public DefaultInjector(Class type, Type genericType, String paramName, Annotation[] annotations) {
        this.type = type;
        this.genericType = genericType;
        this.paramName = paramName;
        this.annotations = annotations;
    }

    @Override
    public Object inject() {
        return null;
    }

    @Override
    public Object inject(HttpRequest request, HttpResponse response) {
        Object object = null;
        if (type != null) {
            try {
                if (request != null) {
                    object = request.getUri().getPathParameters().getFirst(paramName);
                    if (object != null)
                        object = ConvertUtils.convert(object, type);
                }
            } catch (Exception e) {
                Logger.error(e, "类型转换出错");
            }
        }
        return object;
    }
}
