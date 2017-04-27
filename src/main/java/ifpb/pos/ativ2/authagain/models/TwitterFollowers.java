package ifpb.pos.ativ2.authagain.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ifpb.pos.ativ2.authagain.models.ImmutableTwitterFollowers;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 08:13:54
 */
@Value.Immutable
@JsonSerialize(as=ImmutableTwitterFollowers.class)
@JsonDeserialize(as=ImmutableTwitterFollowers.class)
public interface TwitterFollowers {

//    @JsonProperty("users")
    TwitterUser[] users();
    
    
//    @JsonProperty("next_cursor")
    Long next_cursor();
    
}
