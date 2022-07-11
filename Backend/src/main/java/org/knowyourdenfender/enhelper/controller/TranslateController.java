package org.knowyourdenfender.enhelper.controller;

import org.knowyourdenfender.enhelper.pojo.ServerResult;
import org.knowyourdenfender.enhelper.service.TranslateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

/**
 * Contains entrypoint for translation request.
 * Supported by Youdao translate.
 *
 * @author Miris
 */
@RestController
@CrossOrigin
public class TranslateController {
    private final TranslateService translateService;

    @Autowired
    public TranslateController(TranslateService translateService) {
        this.translateService = translateService;
    }

    @GetMapping("translate")
    public ServerResult translate(String srcLang, String dstLang, String srcText) {
        try {
            HashMap<String, String> result = translateService.translate(srcText, srcLang, dstLang);

            if (null == result) {
                return new ServerResult(-1, "Internal error in translation service.", result);
            }

            return new ServerResult(0, "Success", result.get("translation"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
