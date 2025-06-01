package group1.tut.entities.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import group1.tut.entities.models.Product;
import group1.tut.entities.repository.ProductRepository;

@RestController
@RequestMapping("/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // Add a new product with image upload
    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    @SuppressWarnings("UseSpecificCatch")
    public ResponseEntity<Product> addProduct(
            @RequestParam("itemName") String itemName,
            @RequestParam("price") Double price,
            @RequestParam("storeId") Long storeId,
            @RequestPart("image") MultipartFile imageFile) {

        String uploadsDir = "uploads/";
        try {
            // Create uploads directory if it doesn't exist
            Path uploadPath = Paths.get(uploadsDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the file
            String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(imageFile.getInputStream(), filePath);

            // Create and save product
            Product product = new Product();
            product.setItemName(itemName);
            product.setPrice(price);
            product.setStoreId(storeId);
            product.setImage(fileName); // Only store the filename

            Product savedProduct = productRepository.save(product);
            return ResponseEntity.ok(savedProduct);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    // Get all products
    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return ResponseEntity.ok(products);
    }
}
