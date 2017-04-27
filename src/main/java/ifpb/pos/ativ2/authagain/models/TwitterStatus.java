package ifpb.pos.ativ2.authagain.models;



import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import ifpb.pos.ativ2.authagain.models.ImmutableTwitterStatus;
import org.immutables.value.Value;

/**
 * @author Ricardo Job
 * @mail ricardo.job@ifpb.edu.br
 * @since 29/08/2016, 00:59:00
 */
@Value.Immutable
@JsonSerialize(as = ImmutableTwitterStatus.class)
@JsonDeserialize(as = ImmutableTwitterStatus.class)
public interface TwitterStatus {

    String created_at();

    String text();

    TwitterUser user();

    TwitterEntity entities();
}
