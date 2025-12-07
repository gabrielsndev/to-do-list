package persistencia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import modelo.User;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import utils.LocalDateAdapter;

import java.time.LocalDate;

public class RedisUser {

    private static JedisPool pool = new JedisPool("localhost", 6379);
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public void salvarUsuario(User user) {
        try (Jedis jedis = pool.getResource()) {
            String json = gson.toJson(user);
            jedis.setex("sessao:usuario", 60, json);
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

    public void limparRedis() {
        try (Jedis jedis = pool.getResource()) {
            jedis.del("sessao:usuario");
        }
    }

}
