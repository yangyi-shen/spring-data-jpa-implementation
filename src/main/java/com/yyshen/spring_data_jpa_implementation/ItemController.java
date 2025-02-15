package com.yyshen.spring_data_jpa_implementation;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    private final static Item NULL = new Item("NULL");
    private final static Random RANDOMIZER = new Random();

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/all")
    public List<ItemResource> getAll() {
        return this.repository.findAll().stream()
            .map(item -> new ItemResource("success", item))
            .collect(Collectors.toList());
    }

    @GetMapping("/api/{id}")
    public ItemResource getItem(@PathVariable Long id) {
        return repository.findById(id)
            .map(item -> new ItemResource("success", item))
            .orElse(new ItemResource("failure: item with ID " + id + "does not exist", NULL));
    }

    @GetMapping("/api/random")
    public ItemResource getRandom() {
        return getItem(RANDOMIZER.nextLong(1, repository.count() + 1));
    }
}
