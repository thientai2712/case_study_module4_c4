package com.cg.repository;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
//    Boolean exitsById(Long id);
            @Query("SELECT new com.cg.model.dto.ProductDTO (" +
                    "p.id, " +
                    "p.title, " +
                    "p.urlImage, " +
                    "p.price, " +
                    "p.description, " +
                    "p.category " +
                    ") " +
                    "FROM Product AS p WHERE p.deleted = false"
            )
        List<ProductDTO> findAllProductDTOdeleteFalse();

    @Query("SELECT NEW com.cg.model.dto.ProductDTO (" +
            "p.id, " +
            "p.title, " +
            "p.urlImage, " +
            "p.price," +
            "p.description, " +
            "p.category) " +
            "FROM Product AS p " +
            "WHERE p.deleted = false " +
            "AND (p.title LIKE %?1%" +
            "OR p.category.title LIKE %?1%) ")
    List<ProductDTO> searchProductDTOByTileAndCategory(String keySearch);
}
