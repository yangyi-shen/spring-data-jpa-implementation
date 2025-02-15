package com.yyshen.spring_data_jpa_implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {
    private final static Item NULL = new Item("NULL");
    private final static Random RANDOMIZER = new Random();

    private final ItemRepository repository;

    public ItemController(ItemRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/api/read")
    public List<ItemResource> read(@RequestParam String id) {
        // check if id can be converted to numeric value; if not, test for values "all" & "random"
        try {
            Long formattedId = Long.parseLong(id);

            return getItem(formattedId);
        } catch (Exception e) {
            if (id == "random") {
                return getRandom();
            } else if (id == "all") {
                return getAll();
            } else {
                // create ItemResource with error message and null value, and return
                List<ItemResource> ItemResourceList = new ArrayList<ItemResource>();

                ItemResourceList.add(new ItemResource("failure: invalid url parameters", NULL));

                return ItemResourceList;
            }
        }
    }

    public List<ItemResource> getItem(@PathVariable Long id) {
        List<ItemResource> ItemResourceList = new ArrayList<ItemResource>();

        this.repository.findById(id).ifPresentOrElse(
            item -> ItemResourceList.add(new ItemResource("success", item)),
            () -> ItemResourceList.add(new ItemResource("failure: item with ID " + id + "does not exist", NULL)));

        return ItemResourceList;
    }

    public List<ItemResource> getRandom() {
        return getItem(RANDOMIZER.nextLong(1, repository.count() + 1));
    }

    public List<ItemResource> getAll() {
        List<ItemResource> ItemResourceList = new ArrayList<ItemResource>();

        this.repository.findAll().forEach(item -> 
            ItemResourceList.add(new ItemResource("success", item))
        );

        return ItemResourceList;
    }
}
