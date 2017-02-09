package org.jboss.resteasy.core;

import com.zoe.snow.log.Logger;
import com.zoe.snow.model.TypeConverter;
import com.zoe.snow.ws.Auto;
import org.jboss.resteasy.spi.HttpRequest;
import org.jboss.resteasy.spi.HttpResponse;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Json格式注入器
 *
 * @author <a href="mailto:dwq676@126.com">daiwenqing</a>
 * @date 2016/12/22
 */
public class AutoInjector implements ValueInjector {
    private Class type;
    private Type genericType;
    private String paramName;
    private Auto auto;

    public AutoInjector(Class type, Type genericType, String paramName, Annotation[] annotations) {
        this.type = type;
        this.genericType = genericType;
        this.paramName = paramName;
        if (annotations.length > 0)
            auto = Auto.class.cast(annotations[0]);
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
                if (request.getHttpMethod().toUpperCase().equals("GET")) {
                    object = request.getUri().getQueryParameters().getFirst(auto.value());
                } else {
                    object = request.getFormParameters().getFirst(auto.value().trim());
                }
                return TypeConverter.converter(object, type);
            } catch (Exception e) {
                //类型转换出错时，判断是否为基本类型，即值类型，值类型返回默认值或最小值
                try {
                    return TypeConverter.initTypeValue(type);
                } catch (Exception ex) {
                    Logger.error(e, "类型转换出错了");
                }
            }
        }
        return object;
    }


}
