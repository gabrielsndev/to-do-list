package persistencia;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MongoDBConnection {

    private static MongoClient mongoClient = null;
    private static MongoDatabase database = null;
    private MongoDBConnection() {}

    public static MongoDatabase getDatabase() throws Exception {
        if(mongoClient == null) {
            try {
                Properties prop = new Properties();
                FileInputStream file = new FileInputStream("config.properties");
                prop.load(file);
                String url = prop.getProperty("db.connection");
                String dbName= prop.getProperty("db.name");
                file.close();

                mongoClient = MongoClients.create(url);
                database = mongoClient.getDatabase(dbName);

            } catch (IOException e) {
                System.err.println("ERRO: impossivel encontrar o arquivo config.properties");
                throw new IOException("Arquivo de configuração não encontrado");
            } catch (Exception e) {
                System.err.println("ERRO: não foi possivel conecrar com o Banco de Dados");
                throw new Exception("Não foi possível a conexão com o banco de dados");
            }
        }
        return database;
    }
}
