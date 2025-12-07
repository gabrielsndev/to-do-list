package persistencia;

import com.google.gson.Gson;
import modelo.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class RedisUser {

    private static JedisPool pool = new JedisPool("localhost", 6379);
    private Gson gson = new Gson();

    public void salvarUsuario(User user) {
        try (Jedis jedis = pool.getResource()) {
            String json = gson.toJson(user);
            jedis.setex("sessao:usuario", 1800, json);
            System.out.println("Log: Usuário setado no redis");
        }
    }

    public User getUsario() {
        try (Jedis jedis = pool.getResource()) {
            String json = jedis.get("sessao:usuario");
            if (json == null) {
                return null;
            }
            return gson.fromJson(json, User.class);
        }
    }

}
