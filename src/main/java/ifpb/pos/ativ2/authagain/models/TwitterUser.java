package ifpb.pos.ativ2.authagain.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ifpb.pos.ativ2.authagain.models.ImmutableTwitterUser;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/08/2016, 00:59:10
 */
@Value.Immutable
@JsonSerialize(as=ImmutableTwitterUser.class)
@JsonDeserialize(as=ImmutableTwitterUser.class)
public interface TwitterUser {

//    @Value.Parameter
    public String name();
    
    long id();
    
    String id_str();
    
    String screen_name();
    
    String profile_image_url();
}
