package ifpb.pos.ativ2.authagain.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ifpb.pos.ativ2.authagain.models.ImmutableInfluenceIm;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 30/03/2017, 08:13:54
 */
@Value.Immutable
@JsonSerialize(as=ImmutableInfluenceIm.class)
@JsonDeserialize(as=ImmutableInfluenceIm.class)
public interface InfluenceIm {

    TwitterUser userW();
    
    double influence();
    
}
