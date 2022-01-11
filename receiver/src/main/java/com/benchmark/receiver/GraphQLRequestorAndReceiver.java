package com.benchmark.receiver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
public class GraphQLRequestorAndReceiver {

    private static final String url = "http://localhost:8080/graphql";

    public void request(int requestNumber) throws IOException {

        ExecutorService executorService = Executors.newFixedThreadPool(20);
        WebClient webClient = WebClient.builder().build();

        GraphqlRequestBody graphQLRequestBody = new GraphqlRequestBody();

        final String[] query = {getSchemaFromFileName("getBookDetails")};
        final String variables = getSchemaFromFileName("variables");


        executorService.submit(() -> {

            query[0] = query[0].replace("book-number", "book-" + requestNumber);
            graphQLRequestBody.setQuery(query[0]);
//        graphQLRequestBody.setVariables(variables.replace("bookId", "book-" + requestNumber));
//        graphQLRequestBody.setVariables(variables.replace("bookId", requestNumber));

            for(int i = 0 ; i < requestNumber ; i++) {
                WebClient.RequestHeadersSpec<?> requestHeadersSpec = webClient.post()
                        .uri(url)
                        .bodyValue(graphQLRequestBody);
                log.info("Request number " + i + " requested");

                BookDto block = requestHeadersSpec
                        .retrieve()
                        .bodyToMono(BookDto.class)
                        .block();
                log.info("Received request number " + i + " with body: " + block.toString());

            }

        });


    }

    private static String getSchemaFromFileName(final String filename) throws IOException {
        return new String(
                GraphQLRequestorAndReceiver.class.getClassLoader().getResourceAsStream(filename + ".graphql").readAllBytes());
    }

}
