package link.thingscloud.freeswitch.cdr.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>ErrorLog class.</p>
 *
 * @author : <a href="mailto:ant.zhou@aliyun.com">zhouhailin</a>
 * @version $Id: $Id
 */
@Data
@Accessors(chain = true)
public class ErrorLog {
    private List<ErrorPeriod> errorPeriods;

    /**
     * <p>addErrorPeriod.</p>
     *
     * @param errorPeriod a {@link link.thingscloud.freeswitch.cdr.domain.ErrorPeriod} object.
     * @return a {@link link.thingscloud.freeswitch.cdr.domain.ErrorLog} object.
     */
    public ErrorLog addErrorPeriod(ErrorPeriod errorPeriod) {
        if (errorPeriods == null) {
            errorPeriods = new ArrayList<>(4);
        }
        errorPeriods.add(errorPeriod);
        return this;
    }
}
