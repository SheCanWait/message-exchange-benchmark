package com.benchmark.receiver;

import lombok.Getter;
import lombok.ToString;


@Getter
public class BookDto {

        private BookData data;

        @Getter
        public class BookData {

            private BookById bookById;

            @Getter
            public class BookById {
                private String id;
                private String name;
                private String pageCount;
                private Author author;

                @Getter
                @ToString
                public class Author {

                    private String firstName;
                    private String lastName;
                }
            }
        }
}

