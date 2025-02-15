package com.yyshen.spring_data_jpa_implementation;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class DatabaseLoader {
    @Bean
    CommandLineRunner init(ItemRepository repository) {
        return args -> {
            repository.save(new Item(
                    "I was digging in our garden and found a chest full of gold coins. I wanted to run straight home to tell my wife about it. Then, I remembered why I was digging in our garden."));
            repository.save(new Item("My grief counselor died. He was so good, I don't even care."));
            repository.save(new Item(
                    "My husband left a note on the fridge that said, \"This isn't working.\" I'm not sure what he's talking about. I opened the fridge door, and it's working fine!"));
            repository.save(new Item(
                    "Give a man a match, and he'll be warm for a few hours. Set him on fire, and he will be warm for the rest of his life."));
            repository.save(new Item("Where did Joe go after getting lost in a minefield? Everywhere."));
        };
    }
}
