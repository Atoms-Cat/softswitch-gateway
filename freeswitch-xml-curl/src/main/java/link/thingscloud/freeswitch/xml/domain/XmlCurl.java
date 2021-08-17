package link.thingscloud.freeswitch.xml.domain;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class XmlCurl {

    String section;

    String keyValue;
}
