package com.lcwd.electronic.store.service.impl;

import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.dto.ImageResponse;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.entity.Product;
import com.lcwd.electronic.store.exception.ResourceNotFoundException;
import com.lcwd.electronic.store.helper.Helper;
import com.lcwd.electronic.store.repository.CategoryRepository;
import com.lcwd.electronic.store.repository.ProductRepository;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto create(ProductDto productDto) {

        Product product = mapper.map(productDto, Product.class);

        //product id
        String productId= UUID.randomUUID().toString();
        product.setProductId(productId);//automatic dega

        //added date
        product.setAddedDate(new Date());//automatic dega

        Product saveProduct = productRepository.save(product);

        return mapper.map(saveProduct ,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        //fetch the product of given id
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found of given id !!!"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(product.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());
        //save the entity
        Product updatedProduct = productRepository.save(product);

        return mapper.map(updatedProduct, ProductDto.class);
    }

    @Override
    public void delete(String productId) {
       Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found by this id ...."));
       productRepository.delete(product);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product not found by this id ...."));

        return mapper.map(product,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
       Page<Product>page=productRepository.findAll(pageable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product>page=productRepository.findByLiveTrue(pageable);

        return Helper.getPageableResponse(page,ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page=productRepository.findByTitleContaining(subTitle,pageable);

        return Helper.getPageableResponse(page, ProductDto.class);

    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        //fetch the category form Db:
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found with given Id !! "));

        Product product = mapper.map(productDto, Product.class);

        //product id
        String productId= UUID.randomUUID().toString();
        product.setProductId(productId);//automatic dega

        //added date
        product.setAddedDate(new Date());//automatic dega
        product.setProductId(productId);
        Product saveProduct = productRepository.save(product);

        return mapper.map(saveProduct ,ProductDto.class);


    }


}
