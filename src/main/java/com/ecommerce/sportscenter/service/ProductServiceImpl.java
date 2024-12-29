package com.ecommerce.sportscenter.service;

import com.ecommerce.sportscenter.entity.Product;
import com.ecommerce.sportscenter.model.ProductResponse;
import com.ecommerce.sportscenter.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class ProductServiceImpl implements  ProductService{

    private  final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponse getProductById(Integer productId) {
        log.info("fetching product by Id: {}", productId);
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product doesn't exist"));
        //now convert the Product to Product Response
        ProductResponse productResponse = convertToProductResponse(product);
        log.info("Fetched Product by Product Id: {}", productId);
        return productResponse;
    }

    @Override
    public Page<ProductResponse> getProducts(Pageable pageable, Integer brandId, Integer typeId, String keyword) {
        log.info("Fetching Products!!!");
        //Fetching from DB
        // Initialize the Specification object with no initial filters.
        // Specification.where(null) acts as a placeholder to chain further filters.
        Specification<Product> spec = Specification.where(null);
        if(brandId!=null){
            // Add a filter condition to the Specification.
            // This filter checks if the "brand.id" field of the Product entity matches the provided brandId.
            spec = spec.and((root, query, criteriaBuilder) ->
                    // root represents the Product entity in the query.
                    // root.get("brand") navigates to the "brand" field in Product (assumes a Many-to-One mapping).
                    // root.get("brand").get("id") accesses the "id" field of the associated Brand entity.
                    // criteriaBuilder.equal creates a SQL condition: WHERE product.brand.id = :brandId.
                    criteriaBuilder.equal(root.get("brand").get("id"),brandId));
        }
        if(typeId!=null){
            spec=spec.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type").get("id"),typeId));
        }
        if(keyword!=null && !keyword.isEmpty()){
            spec = spec.and(((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),"%" + keyword + "%")));
        }
        log.info("Fetched All Products!!!");
        //map
        return productRepository.findAll(spec,pageable).map(this::convertToProductResponse);
    }

    private ProductResponse convertToProductResponse(Product product) {
        return  ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .pictureUrl(product.getPictureUrl())
                .productBrand(product.getBrand().getName())
                .productType(product.getType().getName())
                .build();
    }


}
