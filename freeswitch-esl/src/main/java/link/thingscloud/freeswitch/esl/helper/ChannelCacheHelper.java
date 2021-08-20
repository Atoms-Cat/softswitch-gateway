package link.thingscloud.freeswitch.esl.helper;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import io.netty.channel.Channel;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author th158
 */
public class ChannelCacheHelper {

    private static final Cache<String, Channel> channelCache = CacheBuilder.newBuilder()
            .maximumSize(8000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .removalListener(new RemovalListener<String, Channel>() {
                @Override
                public void onRemoval(RemovalNotification<String, Channel> removalNotification) {
                    removalNotification.getValue().close();
                }
            })
            .build();


    private static final Cache<String, List<ChannelStateMachine>> listCache = CacheBuilder.newBuilder()
            .maximumSize(8000)
            .expireAfterWrite(1, TimeUnit.DAYS)
            .removalListener(new RemovalListener<String, List<ChannelStateMachine>>() {
                @Override
                public void onRemoval(RemovalNotification<String, List<ChannelStateMachine>> removalNotification) {
                    // todo

                }
            })
            .build();


    public static void setCache(String coreUUID, Channel channel) {
        channelCache.put(coreUUID, channel);
    }

    public static Channel getCache(String coreUUID) {
        try {
            return channelCache.get(coreUUID, new Callable<Channel>() {
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
