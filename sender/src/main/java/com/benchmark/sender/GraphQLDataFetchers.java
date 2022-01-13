package com.benchmark.sender;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class GraphQLDataFetchers {

    private static List<Map<String, String>> books = new ArrayList<>();

    private static List<Map<String, String>> authors = new ArrayList<>();

    static {
        for(int i = 1 ; i <= 100 ; i++) {
            books.add(ImmutableMap.of("id", "book-" + i,
                    "name", "Harry Potter and the Philosopher's Stone",
                    "pageCount", "223",
                    "authorId", "author-" + i));
            authors.add(ImmutableMap.of("id", "author-" + i,
                    "firstName", "Joanne",
                    "lastName", "Rowling"));
        }

    }

    public DataFetcher getBookByIdDataFetcher() {
        return dataFetchingEnvironment -> {
            String bookId = dataFetchingEnvironment.getArgument("id");
            log.info("received request for bookId: " + bookId);
            return books
                    .stream()
                    .filter(book -> book.get("id").equals(bookId))
                    .findFirst()
                    .orElse(null);
        };
    }

    public DataFetcher getAuthorDataFetcher() {
        return dataFetchingEnvironment -> {
            Map<String, String> book = dataFetchingEnvironment.getSource();
            String authorId = book.get("authorId");
            log.info("received request for authorId: " + authorId);
            return authors
                    .stream()
                    .filter(author -> author.get("id").equals(authorId))
                    .findFirst()
                    .orElse(null);
        };
    }
}
