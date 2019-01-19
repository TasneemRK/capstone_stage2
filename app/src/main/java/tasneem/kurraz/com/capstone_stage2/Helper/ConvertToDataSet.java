package tasneem.kurraz.com.capstone_stage2.Helper;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import tasneem.kurraz.com.capstone_stage2.Models.Product;

public class ConvertToDataSet {

    public static Set<String> convertToSet(List<Product> products) {
        Set<String> dataSet = new HashSet<>();
        Gson gson = new Gson();
        for (int i = 0; i < products.size(); i++) {
            Product product = products.get(i);
            String json = gson.toJson(product);
            dataSet.add(json);
        }
        return dataSet;
    }


    public static List<Product> convertToList(Set<String> dataSet) {
        List<Product> products = new ArrayList<>();
        Gson gson = new Gson();
        Iterator<String> iterator;
        String response;
        if (dataSet != null) {
            iterator = dataSet.iterator();
            while (iterator.hasNext()) {
                response = iterator.next();
                Product product = gson.fromJson(response, Product.class);
                products.add(product);
            }


        }

        return products;
    }
}


