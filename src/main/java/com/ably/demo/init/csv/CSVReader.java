package com.ably.demo.init.csv;

import com.ably.demo.product.Product;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<Product> readCSVData(String filePath) {
        List<Product> products = new ArrayList<>();
        try (Reader reader = new FileReader(filePath);
             CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
            for (CSVRecord csvRecord : csvParser) {
                String productName = csvRecord.get("name");
                String imageUrl = csvRecord.get("thumbnail");
                int price = Integer.parseInt(csvRecord.get("price"));

                Product product = Product.createProduct(productName, imageUrl, price);
                products.add(product);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return products;
    }

}