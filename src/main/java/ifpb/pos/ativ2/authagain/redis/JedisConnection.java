package ifpb.pos.ativ2.authagain.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 */
public class JedisConnection {

//    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost", 6379);
    private static JedisPool pool = new JedisPool(new JedisPoolConfig(), "redis-docker", 6379);

    public static Jedis getJedisConnection() {
        return pool.getResource();
    }

}
