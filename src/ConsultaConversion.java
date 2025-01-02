import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsultaConversion {
    private static final String API_KEY = "5218c5b28a23cd05b9b2466b";
    private static final String BASE_URL = "https://v6.exchangerate-api.com/v6/";
    private final HttpClient client;

    public ConsultaConversion() {
        this.client = HttpClient.newHttpClient();
    }

    public String buscaConversion(String monedaBase, String monedaObjetivo, double cantidad) {
        try {
            URI direccion = URI.create(BASE_URL + API_KEY + "/pair/" +
                    monedaBase.toUpperCase() + "/" + monedaObjetivo.toUpperCase() + "/" + cantidad);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(direccion)
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                throw new RuntimeException("Error en la API: " + response.statusCode());
            }

            JsonElement jsonElement = JsonParser.parseString(response.body());
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            double result = jsonObject.get("conversion_result").getAsDouble();
            return String.format("%.2f", result);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Error de conexión: " + e.getMessage());
        } catch (JsonSyntaxException e) {
            throw new RuntimeException("Error al procesar la respuesta: " + e.getMessage());
        }
    }
}
