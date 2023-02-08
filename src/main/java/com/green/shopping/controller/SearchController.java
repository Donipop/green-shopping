package com.green.shopping.controller;

import com.green.shopping.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;

@RestController

public class SearchController {
    @Autowired
    private SearchService searchService;

    @GetMapping("searchview")
    public List<Map<String, Object>> searchview(@RequestParam HashMap<String, Object> map) {
        List<Map<String, Object>> SearchValue = new ArrayList<>();
        Set<String> categorySet = new HashSet<>();
        String searchcont;
        if (map.get("searchcont").toString().indexOf(" ") == -1) {
            searchcont = String.valueOf(map.get("searchcont").toString().replace(" ",""));
        } else {
            searchcont = String.valueOf(map.get("searchcont").toString());
        }

        if( map.get("name").equals("전체")){
            List<HashMap<String, Object>> AllcategorySearch = searchService.AllcategorySearch(searchcont);
            for(int i=0; i< AllcategorySearch.size(); i++){
                HashMap<String, Object> productValue = searchService.getProductValue(AllcategorySearch.get(i));
                HashMap<String, Object> productReview = searchService.getProductReview(AllcategorySearch.get(i));
                HashMap<String, Object> SearchPut = new HashMap<>();
                HashMap<String,Object> productImage = searchService.getProductImgByProductId(AllcategorySearch.get(i).get("ID").toString());
                 if(productImage != null){
                     HashMap<String,Object> fileMap = searchService.getFile(productImage.get("FILE_NAME").toString());
                     SearchPut.put("FILE_NAME",fileMap.get("NAME") + "." + fileMap.get("FILE_TYPE"));
                 }

                SearchPut.put("product_price", productValue.get("PRODUCT_PRICE"));
                SearchPut.put("product_discount", productValue.get("PRODUCT_DISCOUNT"));
                SearchPut.put("star", productReview.get("STAR"));
                SearchPut.put("title", AllcategorySearch.get(i).get("TITLE"));
                SearchPut.put("id", AllcategorySearch.get(i).get("ID"));
                SearchPut.put("mainimage", AllcategorySearch.get(i).get("MAINIMAGE"));
                SearchPut.put("starcount", productReview.get("STARCOUNT"));
                if(categorySet.size() < 4){
                    categorySet.add(AllcategorySearch.get(i).get("CATEGORY").toString());
                }
                SearchValue.add(SearchPut);
            }


        } else {
            List<HashMap<String, Object>> categorySearch = new ArrayList<>();
            HashMap<String, Object> mapbasket = new HashMap<>();
            if(map.get("category").toString().length() != 4){
                mapbasket.put("categorynum", map.get("category").toString());
                mapbasket.put("searchcont", searchcont);
                categorySearch = searchService.categorySearch(mapbasket);
            } else{
                String categorynum = searchService.categorynum(map.get("name").toString());
                mapbasket.put("categorynum", categorynum);
                mapbasket.put("searchcont", searchcont);
               categorySearch = searchService.categorySearch(mapbasket);
            }
            for (int i = 0; i < categorySearch.size(); i++) {
                HashMap<String, Object> SearchPut = new HashMap<>();
                HashMap<String, Object> productValue = searchService.getProductValue(categorySearch.get(i));
                HashMap<String, Object> productReview = searchService.getProductReview(categorySearch.get(i));
                HashMap<String,Object> productImage = searchService.getProductImgByProductId(categorySearch.get(i).get("ID").toString());
                if(productImage != null){
                    HashMap<String,Object> fileMap = searchService.getFile(productImage.get("FILE_NAME").toString());
                    SearchPut.put("FILE_NAME",fileMap.get("NAME") + "." + fileMap.get("FILE_TYPE"));
                }

                SearchPut.put("product_price", productValue.get("PRODUCT_PRICE"));
                SearchPut.put("product_discount", productValue.get("PRODUCT_DISCOUNT"));
                SearchPut.put("star", productReview.get("STAR"));
                SearchPut.put("title", categorySearch.get(i).get("TITLE"));
                SearchPut.put("id", categorySearch.get(i).get("ID"));
                SearchPut.put("mainimage", categorySearch.get(i).get("MAINIMAGE"));
                SearchPut.put("starcount", productReview.get("STARCOUNT"));

                SearchValue.add(SearchPut);

            }
        }
        SearchValue.add(Map.of("categoryList", categorySet));
        return SearchValue;


        }
}

