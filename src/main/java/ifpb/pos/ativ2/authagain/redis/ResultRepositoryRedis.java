package ifpb.pos.ativ2.authagain.redis;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import ifpb.pos.ativ2.authagain.models.ImmutableResultTwitter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import redis.clients.jedis.Jedis;

/**
 * Created by natarajan.
 */
//@Stateless
public class ResultRepositoryRedis {

    public ResultRepositoryRedis() {
    }
    
    public void save(ImmutableResultTwitter result) {
        
        String resultado = getJsonResultado(result);
        
        try (Jedis jedis = JedisConnection.getJedisConnection()) {
            
            jedis.set(result.userInfos().id_str(), resultado);
            jedis.expire(result.userInfos().id_str(), 36000); //10 horas
        }
    }

    
    public ImmutableResultTwitter getCachedResult(String userEmail) {
        
        String result = null;
        try (Jedis jedis = JedisConnection.getJedisConnection()) {
            result = jedis.get(userEmail);
        }
        if (result == null)
            return null;
        
        ImmutableResultTwitter i = getFromJson(result);
        
        return i;
    }
    
    private ImmutableResultTwitter getFromJson(String json){
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                    false);
            ImmutableResultTwitter result
                    = objectMapper.readValue(json,
                            new TypeReference<ImmutableResultTwitter>() {
                    });

            return result;
        } catch (IOException ex) {
            Logger.getLogger(ResultRepositoryRedis.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }

    private String getJsonResultado(ImmutableResultTwitter result) {
        try {
            ObjectMapper objectMapper
                    = new ObjectMapper()
                            .configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS,
                                    false);
             String resultado 
                    = objectMapper.writeValueAsString(result);

             return resultado;
        } catch (IOException ex) {
            Logger.getLogger(ResultRepositoryRedis.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
}
