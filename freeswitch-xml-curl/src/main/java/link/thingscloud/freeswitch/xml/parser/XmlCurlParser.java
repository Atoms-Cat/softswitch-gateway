package link.thingscloud.freeswitch.xml.parser;

import com.alibaba.fastjson.JSONObject;
import link.thingscloud.freeswitch.xml.domain.XmlCurl;
import link.thingscloud.freeswitch.xml.exception.ParserException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>XmlCurlParser class.</p>
 *
 * @author : zhouhailin
 * @version $Id: $Id
 */
@Slf4j
public class XmlCurlParser {


    private XmlCurlParser() {
    }

    /**
     * @param request a {@link HttpServletRequest} object.
     * @return a {@link XmlCurl} object.
     * @throws ParserException
     */
    public static XmlCurl decodeThenParse(final HttpServletRequest request) throws ParserException {
        return JSONObject.parseObject(JSONObject.toJSONString(getAllRequestParam(request)), XmlCurl.class);
    }

    public static Map<String, String> getAllRequestParam(final HttpServletRequest request) {
        Map<String, String> res = new HashMap<String, String>();
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
