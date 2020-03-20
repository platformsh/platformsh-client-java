package sh.platform.config.integration;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import sh.platform.config.Config;
import sh.platform.config.MongoDB;

import static com.mongodb.client.model.Filters.eq;

/**
 * Integration test on MongoDB
 */
@Tag("integration")
public class MongoDBTest {

    private GenericContainer mongodb =
            new GenericContainer("mongo:latest")
                    .withExposedPorts(27017)
                    .waitingFor(Wait.defaultWaitStrategy());


    @Test
    @DisplayName("Should run the integration MongoDB")
    public void shouldRunIntegrationTest() {
        mongodb.start();
        System.setProperty("mongodb.host", mongodb.getContainerIpAddress());
        System.setProperty("mongodb.port", Integer.toString(mongodb.getFirstMappedPort()));
        System.setProperty("mongodb.path", "mongodb");

        Config config = new Config();
        MongoDB database = config.getCredential("mongodb", MongoDB::new);
        MongoClient mongoClient = database.get();
        final MongoDatabase mongoDatabase = mongoClient.getDatabase(database.getDatabase());
        MongoCollection<Document> collection = mongoDatabase.getCollection("scientist");
        Document doc = new Document("name", "Ada Lovelace")
                .append("city", "London");
        collection.insertOne(doc);
        Document myDoc = collection.find(eq("_id", doc.get("_id"))).first();
        Assertions.assertNotNull(myDoc);

        System.clearProperty("mongodb.host");
        System.clearProperty("mongodb.port");
        System.clearProperty("mongodb.path");
        mongodb.close();
    }
}
