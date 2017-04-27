package ifpb.pos.ativ2.authagain.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 08:13:54
 */
@Value.Immutable
@JsonSerialize(as=ImmutableTwitterUserInfo.class)
@JsonDeserialize(as=ImmutableTwitterUserInfo.class)
public interface TwitterUserInfo {

    String id_str();
    String name();
    String screen_name();
    long followers_count();
    long friends_count();
    long favourites_count();
    long statuses_count();
    String profile_image_url();
    
}
