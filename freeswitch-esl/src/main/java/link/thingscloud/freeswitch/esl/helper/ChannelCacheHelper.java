package link.thingscloud.freeswitch.esl.helper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import io.netty.channel.Channel;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author th158
 */
public class ChannelCacheHelper {

    private static final Cache<String, Channel> channelCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(60, TimeUnit.MINUTES)
            .removalListener(new RemovalListener<String, Channel>() {
                @Override
                public void onRemoval(RemovalNotification<String, Channel> removalNotification) {
                    removalNotification.getValue().close();
                }
            })
            .build();


    public static void setCache(String callerUniqueID, Channel channel) {
        channelCache.put(callerUniqueID, channel);
    }

    public static Channel getCache(String callerUniqueID) {
        try {
            return channelCache.get(callerUniqueID, new Callable<Channel>() {
                @Override
                public Channel call() throws Exception {
                    return null;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
