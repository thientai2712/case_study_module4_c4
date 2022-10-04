package com.cg.service.product;

import com.cg.model.Product;
import com.cg.model.dto.ProductDTO;
import com.cg.model.dto.UserDTO;
import com.cg.service.IGeneralService;

import java.util.List;

public interface ProductService extends IGeneralService<Product> {
//    Boolean exitsById(Long id);

    List<ProductDTO> findAllProductDTOdeleteFalse();

    List<ProductDTO> searchProductDTOByTileAndCategory(String keySearch);
}
