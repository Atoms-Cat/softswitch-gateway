package link.thingscloud.spring.boot.common.util;


import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public class LocalDateTimeUtil {
    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    private LocalDateTimeUtil() {
    }

    public static LocalDateTime ofEpochMilli(long epochMilli) {
        return epochMilli <= 0L ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(epochMilli), DEFAULT_ZONE_ID);
    }

    public static LocalDateTime ofUepochMilli(long uepochMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(uepochMilli / 1000L), DEFAULT_ZONE_ID);
    }

    public static long toEpochMilli(long uepochMillis) {
        return uepochMillis / 1000L;
    }

    public static int epochToSeconds(long epochMillis) {
        return (int)(epochMillis / 1000L);
    }

    public static int uepochToSeconds(long uepochMillis) {
        return (int)(uepochMillis / 1000000L);
    }

    public static long toEpochMilli(LocalDateTime localDateTime) {
        return localDateTime.toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }
}

