package org.knowyourdenfender.enhelper.service.implement;

import org.knowyourdenfender.enhelper.service.TranslateService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static org.knowyourdenfender.enhelper.thirdParty.YoudaoTranslate.*;

@Service
public class TranslateImplement implements TranslateService {
    @Value("${youdao.appKey}")
    private String appKey;
    @Value("${youdao.appSecret}")
    private String appSecret;
    @Value("${youdao.url}")
    private String youdaoUrl;

    public HashMap<String, String> translate(String srcText, String srcLang, String dstLang) throws IOException {
        Map<String, String> params = new HashMap<>();

        paramGenerator(srcText, params, appKey, appSecret, srcLang, dstLang);
        // 处理结果
        return requestForHttp(youdaoUrl, params);
    }

    public static void paramGenerator(String srcText, Map<String, String> params, String appKey, String appSecret, String srcLang, String dstLang) {
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("from", srcLang);
        params.put("to", dstLang);
        params.put("signType", "v3");

        String currentTime = String.valueOf(System.currentTimeMillis() / 1000);
        params.put("curtime", currentTime);
        String sign = getDigest(appKey + truncate(srcText) + salt + currentTime + appSecret);

        params.put("appKey", appKey);
        params.put("q", srcText);
        params.put("salt", salt);
        params.put("sign", sign);
        //params.put("vocabId", "您的用户词表ID");
    }
}
