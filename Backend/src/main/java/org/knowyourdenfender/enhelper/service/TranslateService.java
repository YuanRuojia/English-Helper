package org.knowyourdenfender.enhelper.service;

import java.io.IOException;
import java.util.HashMap;

public interface TranslateService {
    HashMap<String, String> translate(String srcText, String srcLang, String dstLang) throws IOException;
}
