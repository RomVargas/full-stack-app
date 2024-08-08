package com.challenge.operations.Services.Impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

public class RandomIntegerFetcher {
    public static String fetchRandomIntegers() throws IOException, InterruptedException {
        // Crea un cliente HTTP
        HttpClient client = HttpClient.newHttpClient();
        
        // Crea una solicitud HTTP GET a la URL proporcionada
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.random.org/integers/?num=10&min=1&max=6&col=1&base=10&format=plain&rnd=new"))
                .build();
        
        // Envía la solicitud y obtiene la respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        
        // Retorna el cuerpo de la respuesta como un String
        return response.body();
    }
}
