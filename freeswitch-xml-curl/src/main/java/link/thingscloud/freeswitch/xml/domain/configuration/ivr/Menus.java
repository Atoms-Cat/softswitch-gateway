package link.thingscloud.freeswitch.xml.domain.configuration.ivr;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author th158
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Menus implements Serializable {

    @JacksonXmlElementWrapper(localName = "menu", useWrapping = false)
    private List<Menu> menu;
}
