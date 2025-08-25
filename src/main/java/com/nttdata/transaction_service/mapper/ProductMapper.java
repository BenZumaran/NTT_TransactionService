package com.nttdata.transaction_service.mapper;

import com.nttdata.transaction_service.model.Product;
import com.nttdata.transaction_service.model.entity.ProductEntity;

import java.math.BigDecimal;

public class ProductMapper {

    // Validate and Converts ProductEntity to Product
    public static Product productEntityToProduct(ProductEntity productEntity) {
        Product product = new Product();
        product.setId(productEntity.getId());
        if (productEntity.getType() != null)
            product.setType(Product.TypeEnum.valueOf(productEntity.getType().toUpperCase()));
        if (productEntity.getNumber() != null)
            product.setNumber(productEntity.getNumber());
        if (productEntity.getBalance() > -1)
            product.setBalance(BigDecimal.valueOf(productEntity.getBalance()));
        if(productEntity.getLimit() > -1)
            product.setLimit(BigDecimal.valueOf(productEntity.getLimit()));
        return product;
    }

    // Validate Converts Product to ProductEntity
    public static ProductEntity productToProductEntity(Product product) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setId(product.getId());
        if (product.getType() != null)
            productEntity.setType(product.getType().getValue());
        if (product.getNumber() != null)
            productEntity.setNumber(product.getNumber());
        if (product.getBalance() != null)
            productEntity.setBalance(product.getBalance().doubleValue());
        if(product.getLimit() != null)
            productEntity.setLimit(product.getLimit().doubleValue());
        return productEntity;
    }


}
