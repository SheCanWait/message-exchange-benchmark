package com.benchmark.receiver;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;

@Component
public class GraphQLRequestorAndReceiver {

//    private static final String url = "https://countries.trevorblades.com/";
    private static final String url = "http://localhost:8080/graphql";

    public BookDto request(String requestNumber) throws IOException {

        WebClient webClient = WebClient.builder().build();

        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

        String query = getSchemaFromFileName("getBookDetails");
        final String variables = getSchemaFromFileName("variables");

        query = query.replace("book-number", "book-" + requestNumber);
        graphQLRequestBody.setQuery(query);
//        graphQLRequestBody.setVariables(variables.replace("bookId", "book-" + requestNumber));
//        graphQLRequestBody.setVariables(variables.replace("bookId", requestNumber));

        WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.post()
                .uri(url)
                .bodyValue(graphQLRequestBody);
        return requestHeadersSpec
                .retrieve()
                .bodyToMono(BookDto.class)
                .block();
    }

    private static String getSchemaFromFileName(final String filename) throws IOException {
        return new String(
                GraphQLRequestorAndReceiver.class.getClassLoader().getResourceAsStream(filename + ".graphql").readAllBytes());
    }

}
