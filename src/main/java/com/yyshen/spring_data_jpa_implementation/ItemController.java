package com.yyshen.spring_data_jpa_implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.data.domain.Example;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/")
    public String returnDatabaseSummary() {
        Long databaseItemCount = this.repository.count();
        List<ItemResource> databaseItems = readAll();

        String databaseSummaryTemplate = """
                -----------------------------------------------------------------<br>
                🎺🪽 BEHOLD, THE INCREDIBLE SPRING DATA JPA IMPLEMENTATION!!! 🪽🎺<br>
                -----------------------------------------------------------------<br>
                <br>
                NUMBER OF ITEMS IN DATABASE: %s                                  <br>
                DATABASE CONTENTS:                                               <br>
                    """ + databaseItems;
        String databaseSummary = String.format(databaseSummaryTemplate, databaseItemCount);

        return databaseSummary;
    }

    @GetMapping("/api/create")
    public ItemResource create(@RequestParam String text) {
        // make sure text param isn't empty
        if (text == null || text.trim() == "") {
            return new ItemResource("error: text parameter is empty or not provided", NULL);
        } else {
            return createItem(text);
        }
    }

    @GetMapping("/api/read")
    public List<ItemResource> read(@RequestParam String id) {
        // check if id can be converted to numeric value; if not, test for values "all"
        // & "random"
        try {
            Long formattedId = Long.parseLong(id);

            return readItem(formattedId);
        } catch (Exception e) {
            if (id.equals("random")) {
                return readRandom();
            } else if (id.equals("all")) {
                return readAll();
            } else {
                // create ItemResource with error message and null value, and return
                List<ItemResource> ItemResourceList = new ArrayList<ItemResource>();

                ItemResourceList.add(new ItemResource("error: invalid url parameters", NULL));

                return ItemResourceList;
            }
        }
    }

    @GetMapping("/api/update")
    public ItemResource update(@RequestParam String id, @RequestParam String text) {
        // check if id can be converted to numeric value and try to update element; if
        // not, check for various errors
        try {
            Long formattedId = Long.parseLong(id);

            return updateItem(formattedId, text);
        } catch (Exception e) {
            if (id == null || id.trim().equals("")) {
                return new ItemResource("error: id parameter is empty or not provided", NULL);
            } else if (text == null || text.trim().equals("")) {
                return new ItemResource("error: id parameter is empty or not provided", NULL);
            } else {
                return new ItemResource("error: no url parameters provided", NULL);
            }
        }
    }

    @GetMapping("/api/delete")
    public ItemResource delete(@RequestParam String id) {
        try {
            Long formattedId = Long.parseLong(id);

            return deleteItem(formattedId);
        } catch (Exception e) {
            if (id == null || id.trim().equals("")) {
                return new ItemResource("error: id parameter is empty or not provided", NULL);
            } else {
                return new ItemResource("error: no url parameters provided", NULL);
            }
        }
    }

    public ItemResource createItem(String text) {
        Item newItem = new Item(text);

        // check if item with same text content already exists
        Example<Item> newItemExample = Example.of(newItem);
        Optional<Item> duplicateItem = this.repository.findOne(newItemExample);

        if (duplicateItem.isPresent()) {
            return new ItemResource("error: item with same content already exists", NULL);
        } else {
            // save item
            this.repository.save(newItem);

            return new ItemResource("success", newItem);
        }
    }

    public List<ItemResource> readItem(Long id) {
        List<ItemResource> ItemResourceList = new ArrayList<ItemResource>();

        this.repository.findById(id).ifPresentOrElse(
                item -> ItemResourceList.add(new ItemResource("success", item)),
                () -> ItemResourceList.add(new ItemResource("error: item with ID " + id + " does not exist", NULL)));

        return ItemResourceList;
    }

    public List<ItemResource> readRandom() {
        return readItem(RANDOMIZER.nextLong(1, repository.count() + 1));
    }

    public List<ItemResource> readAll() {
        List<ItemResource> ItemResourceList = new ArrayList<ItemResource>();

        this.repository.findAll().forEach(item -> ItemResourceList.add(new ItemResource("success", item)));

        return ItemResourceList;
    }

    public ItemResource updateItem(Long id, String text) {
        Optional<Item> itemOptional = this.repository.findById(id);
        if (itemOptional.isPresent()) {
            Item item = itemOptional.get();
            item.setText(text);
            this.repository.save(item);
            return new ItemResource("success", item);
        } else {
            return new ItemResource("error: item with ID " + id + " does not exist", NULL);
        }
    }

    public ItemResource deleteItem(Long id) {
        Optional<Item> itemOptional = this.repository.findById(id);
        if (itemOptional.isPresent()) {
            this.repository.deleteById(id);

            return new ItemResource("success", NULL);
        } else {
            return new ItemResource("failure: item with ID " + id + " does not exist", NULL);
        }
    }
}
