package com.zoe.snow.delivery;

import com.zoe.snow.listener.ContextRefreshedListener;
import com.zoe.snow.log.Logger;
import com.zoe.snow.resource.Io;
import com.zoe.snow.util.Converter;
import com.zoe.snow.util.Validator;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lpw
 */
@Component("snow.delivery.http")
public class HttpImpl implements Http, ContextRefreshedListener {
    private static final String CHARSET = "UTF-8";
    @Value("${commons.util.http.pool.max:256}")
    protected int max;
    @Value("${commons.util.http.connect.time-out:5000}")
    protected int connectTimeout;
    @Value("${commons.util.http.read.time-out:20000}")
    protected int readTimeout;
    protected PoolingHttpClientConnectionManager manager;
    @Autowired
    private Io io;

    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> parameters, String charset) {
        if (Validator.isEmpty(parameters))
            return get(url, headers, "", charset);

        StringBuilder sb = new StringBuilder();
        parameters.forEach((name, value) -> sb.append('&').append(name).append('=').append(Converter.encodeUrl(parameters.get(name), charset)));

        return get(url, headers, sb.substring(1), charset);
    }

    @Override
    public String get(String url, Map<String, String> headers, String parameters, String charset) {
        if (Validator.isEmpty(url))
            return null;

        if (!Validator.isEmpty(parameters))
            url = url + (url.indexOf('?') == -1 ? '?' : '&') + parameters;

        if (Logger.isDebugEnable())
            Logger.debug("使用GET访问[{}]。", url);

        String content = null;
        try {
            HttpGet get = new HttpGet(url);
            get.setConfig(getRequestConfig());
            content = execute(get, headers, charset);
        } catch (Exception e) {
            Logger.warn(e, "使用GET访问[{}]时发生异常！", url);
        }

        if (Logger.isDebugEnable())
            Logger.debug("使用GET访问[{}]结果[{}]。", url, content);

        return content;
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> parameters, String charset) {
        if (Validator.isEmpty(parameters))
            return postByEntity(url, headers, null, charset);

        try {
            List<NameValuePair> nvps = new ArrayList<>();
            parameters.forEach((key, value) -> nvps.add(new BasicNameValuePair(key, value)));

            return postByEntity(url, headers, new UrlEncodedFormEntity(nvps, getCharset(charset)), charset);
        } catch (Exception e) {
            Logger.warn(e, "使用POST访问[{}]时发生异常！", url);

            return null;
        }
    }

    @Override
    public String post(String url, Map<String, String> headers, String content, String charset) {
        try {
            return postByEntity(url, headers, Validator.isEmpty(content) ? null : new StringEntity(content, getCharset(charset)), charset);
        } catch (Exception e) {
            Logger.warn(e, "使用POST访问[{}]时发生异常！", url);

            return null;
        }
    }

    @Override
    public String upload(String url, Map<String, String> headers, Map<String, String> parameters, Map<String, File> files, String charset) {
        if (Validator.isEmpty(files))
            return post(url, headers, parameters, charset);

        MultipartEntityBuilder entity = MultipartEntityBuilder.create();
        ContentType contentType = ContentType.create("text/plain", getCharset(charset));
        if (!Validator.isEmpty(parameters))
            parameters.forEach((key, value) -> entity.addTextBody(key, value, contentType));
        files.forEach(entity::addBinaryBody);

        return postByEntity(url, headers, entity.build(), charset);
    }

    protected String postByEntity(String url, Map<String, String> headers, HttpEntity entity, String charset) {
        if (Validator.isEmpty(url))
            return null;

        if (Logger.isDebugEnable())
            Logger.debug("使用POST访问{}[{}]。", url, entity);

        String content = null;
        try {
            HttpPost post = new HttpPost(url);
            post.setConfig(getRequestConfig());
            if (entity != null)
                post.setEntity(entity);
            content = execute(post, headers, charset);
        } catch (Exception e) {
            Logger.warn(e, "使用HTTP访问{}[{}]时发生异常！", url, entity);
        }

        if (Logger.isDebugEnable())
            Logger.debug("使用POST访问[{}]结果[{}]。", url, content);

        return content;
    }

    @Override
    public Map<String, String> download(String url, Map<String, String> headers, Map<String, String> parameters, String charset, String dest) {
        if (Validator.isEmpty(parameters))
            return download(url, headers, "", dest);

        StringBuilder sb = new StringBuilder();
        parameters.forEach((name, value) -> sb.append('&').append(name).append('=').append(Converter.encodeUrl(parameters.get(name), charset)));

        return download(url, headers, sb.substring(1), dest);
    }

    @Override
    public Map<String, String> download(String url, Map<String, String> headers, String parameters, String dest) {
        if (Validator.isEmpty(url))
            return null;

        if (!Validator.isEmpty(parameters))
            url = url + (url.indexOf('?') == -1 ? '?' : '&') + parameters;

        if (Logger.isDebugEnable())
            Logger.debug("使用GET下载文件[{}]。", url);

        try {
            new File(dest.substring(0, dest.lastIndexOf('/'))).mkdirs();
            HttpGet get = new HttpGet(url);
            get.setConfig(getRequestConfig());
            CloseableHttpResponse response = execute(get, headers);
            Map<String, String> map = toMap(response.getAllHeaders());
            InputStream input = response.getEntity().getContent();
            OutputStream output = new FileOutputStream(dest);
            io.copy(input, output);
            input.close();
            output.close();
            response.close();

            if (Logger.isDebugEnable())
                Logger.debug("使用GET下载文件[{}]到[{}]。", url, dest);

            return map;
        } catch (Exception e) {
            Logger.warn(e, "使用GET下载文件[{}]时发生异常！", url);

            return null;
        }
    }

    protected Map<String, String> toMap(Header[] headers) {
        Map<String, String> map = new HashMap<>();
        for (Header header : headers)
            map.put(header.getName(), header.getValue());

        return map;
    }

    protected RequestConfig getRequestConfig() {
        return RequestConfig.custom().setConnectTimeout(connectTimeout).setSocketTimeout(readTimeout).build();
    }

    protected String execute(HttpUriRequest request, Map<String, String> headers, String charset) throws IOException {
        CloseableHttpResponse response = execute(request, headers);
        String content = EntityUtils.toString(response.getEntity(), getCharset(charset));
        response.close();

        return content;
    }

    protected CloseableHttpResponse execute(HttpUriRequest request, Map<String, String> headers) throws IOException {
        if (!Validator.isEmpty(headers)) {
            headers.forEach((name, value) -> {
                if (!name.toLowerCase().equals("content-length"))
                    request.addHeader(name, value);
            });
        }
        request.addHeader("time-hash", String.valueOf(System.currentTimeMillis()));

        return HttpClients.custom().setConnectionManager(manager).build().execute(request, HttpClientContext.create());
    }

    protected String getCharset(String charset) {
        return charset == null ? CHARSET : charset;
    }

    @Override
    public int getContextRefreshedSort() {
        return 9;
    }

    @Override
    public void onContextRefreshed() {
        try {
            SSLContext sslContext = SSLContext.getInstance("SSLv3");
            sslContext.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }
            }}, null);
            SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE).register("https", sslConnectionSocketFactory).build();
            manager = new PoolingHttpClientConnectionManager(registry);
            manager.setMaxTotal(max);
            manager.setDefaultMaxPerRoute(max >> 3);
        } catch (Exception e) {
            Logger.warn(e, "初始化HTTP/S客户端时发生异常！");
        }
    }
}
