package com.ably.demo.init;

import com.ably.demo.init.csv.CSVReader;
import com.ably.demo.init.csv.ProductInitService;
import com.ably.demo.product.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RequiredArgsConstructor
@Component
@Profile("!test")
public class InitProductExecutor implements ApplicationRunner {

    private final ProductInitService dataInsertService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ClassPathResource resource = new ClassPathResource("data/dummy_product.csv");
        File csvFile = File.createTempFile("dummy_product", ".csv");
        InputStream inputStream = resource.getInputStream();
        Files.copy(inputStream, csvFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

        List<Product> products = CSVReader.readCSVData(csvFile.getPath());
        dataInsertService.insertData(products);
    }
}
