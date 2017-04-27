package ifpb.pos.ativ2.authagain.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ifpb.pos.ativ2.authagain.models.ImmutableTwitterRetweetersList;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/08/2016, 00:59:00
 */
@Value.Immutable
@JsonSerialize(as = ImmutableTwitterRetweetersList.class)
@JsonDeserialize(as = ImmutableTwitterRetweetersList.class)
public interface TwitterRetweetersList {

    Long[] ids();

    Long next_cursor();
    
}
