package top.gabin.tools.utils;

import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import top.gabin.tools.auth.AutoUpdateInCloudCertificatesVerifier;
import top.gabin.tools.auth.CacheService;
import top.gabin.tools.config.ProfitsSharingConfig;
import top.gabin.tools.response.AbstractResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class HttpUtils {

    private final ProfitsSharingConfig config;
    private CloseableHttpClient httpClient;
    private CloseableHttpClient httpClientNotVerify;
    private volatile AutoUpdateInCloudCertificatesVerifier verifier;

    public HttpUtils(ProfitsSharingConfig config, CacheService cacheService) {
        this.config = config;
        init(cacheService);
    }

    public AutoUpdateInCloudCertificatesVerifier getVerifier() {
        return verifier;
    }

    private void init(CacheService cacheService) {
        String mchId = config.getMchId();
        String apiKey = config.getApiKey();
        String mchSerialNo = config.getMchSerialNo();
        PrivateKey privateKey = config.getPrivateKey();
        // 不需要传入微信支付证书，将会自动更新
        verifier = new AutoUpdateInCloudCertificatesVerifier(
                new WechatPay2Credentials(mchId, new PrivateKeySigner(mchSerialNo, privateKey)),
                apiKey.getBytes(StandardCharsets.UTF_8), cacheService);
        WechatPayHttpClientBuilder builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, privateKey)
                .withValidator(new WechatPay2Validator(verifier));
        httpClient = builder.build();

        builder = WechatPayHttpClientBuilder.create()
                .withMerchant(mchId, mchSerialNo, privateKey)
                .withValidator(response -> true);
        httpClientNotVerify = builder.build();
    }

    public List<X509Certificate> getLastCertificateList() {
        List<X509Certificate> certificateList = verifier.getLastCertificateList().stream().filter(certificate -> {
            try {
                certificate.checkValidity();
                return true;
            } catch (CertificateExpiredException | CertificateNotYetValidException e) {
                e.printStackTrace();
                return false;
            }
        }).collect(Collectors.toList());
        log.info("可用证书数量：" + certificateList.size());
        return certificateList;
    }

    private <T extends AbstractResponse> T request(Class<T> responseClass, HttpUriRequest request) {
        request.addHeader("Content-Type", "application/json");
        request.addHeader("Accept", "application/json");
        try {
            HttpResponse response = httpClient.execute(request);
            int statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            String responseText = entity == null ? "{}" : EntityUtils.toString(entity, "utf-8");
            EntityUtils.consume(entity);
            log.info(responseText);
            T responseInstance = JsonUtils.json2Bean(responseClass, responseText);
            responseInstance.setHttpStatusCode(statusCode);
            return responseInstance;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T extends AbstractResponse> T get(Class<T> responseClass, String url) {
        return request(responseClass, new HttpGet(url));
    }

    public <T extends AbstractResponse> T post(Class<T> responseClass, Object requestObj, String url) {
        HttpPost httpPost = getHttpPost(requestObj, url);
        return request(responseClass, httpPost);
    }

    public <T extends AbstractResponse> T post(Class<T> responseClass, Object requestObj, String url, X509Certificate certificate) {
        HttpPost httpPost = getHttpPost(requestObj, url);
        httpPost.addHeader("Wechatpay-Serial", certificate.getSerialNumber().toString(16).toUpperCase());
        return request(responseClass, httpPost);
    }

    private HttpPost getHttpPost(Object requestObj, String url) {
        HttpPost httpPost = new HttpPost(url);
        String jsonData = "";
        //增加一个判断是否有Declared方法，要不JsonUtils.bean2Json(requestBody)会报异常
        int methodsNum = requestObj.getClass().getDeclaredMethods().length;
        if (requestObj instanceof String) {
            jsonData = requestObj.toString();
        } else if (methodsNum > 0) {
            jsonData = JsonUtils.bean2Json(requestObj);
        }
        if (jsonData != null && !jsonData.equals("{}")) {
            log.info(jsonData);
            StringEntity reqEntity = new StringEntity(
                    jsonData, ContentType.create("application/json", "utf-8"));
            httpPost.setEntity(reqEntity);
        }
        return httpPost;
    }

    public InputStream download(String downloadUrl) {
        HttpGet httpGet = new HttpGet(downloadUrl);
        httpGet.addHeader("Accept", "application/json");

        try (CloseableHttpResponse response = httpClientNotVerify.execute(httpGet)) {
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return response.getEntity().getContent();
            } else {
                String body = EntityUtils.toString(response.getEntity());
                log.info(body);
                throw new IOException("request failed");
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
