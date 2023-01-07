package com.green.shopping.controller;

import com.green.shopping.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@RestController

public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("searchview")
    public void searchview(@RequestParam HashMap<String, Object> map) {

       List<String> a = searchService.searchview(map);
        System.out.println(a);
    }

}
