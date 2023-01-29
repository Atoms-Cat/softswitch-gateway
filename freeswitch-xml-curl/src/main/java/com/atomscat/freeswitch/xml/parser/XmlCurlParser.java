package com.atomscat.freeswitch.xml.parser;

import com.alibaba.fastjson.JSONObject;
import com.atomscat.freeswitch.xml.domain.XmlCurl;
import com.atomscat.freeswitch.xml.exception.ParserException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>XmlCurlParser class.</p>
 *
 * @author : everyone
 * @version $Id: $Id
 */
@Slf4j
public class XmlCurlParser {


    private XmlCurlParser() {
    }

    /**
     * @param request a {@link HttpServletRequest} object.
     * @return a {@link XmlCurl} object.
     */
    public static XmlCurl decodeThenParse(final HttpServletRequest request) throws ParserException {
        Map<String, String> metadata = getAllRequestParam(request);
        XmlCurl xmlCurl = JSONObject.parseObject(JSONObject.toJSONString(metadata), XmlCurl.class);
        xmlCurl.setMetadata(metadata);
        return xmlCurl;
    }

    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new ConcurrentHashMap<String, String>();
        Enumeration<?> temp = request.getParameterNames();
        if (null != temp) {
            while (temp.hasMoreElements()) {
                String en = (String) temp.nextElement();
                String value = request.getParameter(en);
                res.put(en, value);
                //如果字段的值为空，判断若值为空，则删除这个字段>
                if (null == res.get(en) || "".equals(res.get(en))) {
                    res.remove(en);
                }
            }
        }
        return res;
    }
}
