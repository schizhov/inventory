package ai.dm;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.BindMode;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.ToStringConsumer;
import org.testcontainers.containers.output.WaitingConsumer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;

import static io.dropwizard.jackson.Jackson.newObjectMapper;
import static junit.framework.TestCase.assertEquals;

@Testcontainers
public class ProductResourceIT {

    private final Product product = new Product("prod1548#prod104001000080", "High-top sneakers", 8, "Shoes", "Sneakers");
    private final Order order = new Order("ef391c30-fc8d-31d9-aa13-530b6e165591", "prod1548#prod104001000080", "SEK", 1, new BigDecimal("0.000"), new BigDecimal("12615.063"), "direct", "direct", LocalDateTime.parse("2023-03-10T12:57:52"));

    private final ProductWithOrders po = new ProductWithOrders(product, List.of(order));
    private static final ObjectMapper MAPPER = newObjectMapper();

    private final Network network = Network.newNetwork();
    @Container
    private final PostgreSQLContainer db = new PostgreSQLContainer<>("postgres:16-alpine")
            .withInitScript("tc-init.sql")
            .withNetwork(network)
            .withNetworkAliases("db");

    @Container
    private GenericContainer app = new GenericContainer<>(DockerImageName.parse("inventory-service:latest"))
            .waitingFor(Wait.forHttp("/products"))
            .withExposedPorts(8080)
            .dependsOn(db)
            .withNetwork(network)
            .withEnv("AIDM_IS_JDBC_URL", "jdbc:postgresql://db:5432/test")
            .withEnv("AIDM_IS_DB_USER", db.getUsername())
            .withEnv("AIDM_IS_DB_PASSWORD", db.getPassword())
            .withClasspathResourceMapping("config.yaml", "/config.yaml", BindMode.READ_ONLY)
            .withLogConsumer(new WaitingConsumer().andThen(new ToStringConsumer()));

    private final HttpClient httpClient = HttpClient.newBuilder().build();

    @Test
    public void testProducts() throws JsonProcessingException {
        var response = send(products(), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(MAPPER.writeValueAsString(List.of(po)), response.body());
    }

    @Test
    public void testGetProduct() throws JsonProcessingException {
        var response = send(getProduct(product.id()), HttpResponse.BodyHandlers.ofString());
        assertEquals(200, response.statusCode());
        assertEquals(MAPPER.writeValueAsString(po), response.body());
    }

    private HttpRequest products() {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(String.format("http://%s:%s/products", app.getHost(), app.getMappedPort(8080))))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpRequest getProduct(String productId) {
        try {
            return HttpRequest.newBuilder()
                    .uri(new URI(String.format("http://%s:%s/products/%s", app.getHost(), app.getMappedPort(8080), URLEncoder.encode(productId))))
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    private <B> HttpResponse<B> send(HttpRequest request, HttpResponse.BodyHandler<B> bodyHandler) {
        try {
            return httpClient.send(request, bodyHandler);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
