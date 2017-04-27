package ifpb.pos.ativ2.authagain.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ifpb.pos.ativ2.authagain.models.ImmutableTwitterTimelineStatus;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/08/2016, 00:59:00
 */
@Value.Immutable
@JsonSerialize(as = ImmutableTwitterTimelineStatus.class)
@JsonDeserialize(as = ImmutableTwitterTimelineStatus.class)
public interface TwitterTimelineStatus {

    String id_str();
    
    long id();

    long retweet_count();
    
    long favorite_count();
    
    boolean favorited();
    
    boolean retweeted();
    
    TwitterEntity entities();
    
}
