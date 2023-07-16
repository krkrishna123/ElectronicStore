package com.lcwd.electronic.store.controller;


import com.lcwd.electronic.store.dto.ApiResponseMessage;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private static Logger logger=  LoggerFactory.getLogger( ProductController.class);
    //create
    @PostMapping
   public ResponseEntity<ProductDto>createProduct(@RequestBody ProductDto productDto){
        logger.info(" Before initiated createdProduct in controller :="+ productDto);
       ProductDto createdProduct = productService.create(productDto);
        logger.info(" After initiated createdProduct in controller :="+ productDto);
return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
   }
    //update
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable String productId ,@RequestBody ProductDto productDto) {
        logger.info(" Before initiated updateProduct in controller :="+ productId);
        ProductDto updateProduct = productService.update(productDto,productId );
        logger.info(" After initiated updateProduct in controller :="+ productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }
    //delete
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage>delete(@PathVariable String productId){
        logger.info(" Before initiated DeleteProduct in controller :="+ productId);
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("product is deleted successfully !!").status(HttpStatus.OK).success(true).build();
        logger.info(" after initiated deleteProduct in controller :="+ productId);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }
    //get single
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto>getProduct(@PathVariable String productId ) {
        logger.info(" Before initiated get ProductById in controller :="+ productId);
        ProductDto productDto = productService.get(productId);
        logger.info(" After initiated get ProductById in controller :="+ productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }
    //get all
    @GetMapping
public ResponseEntity<PageableResponse<ProductDto>>getAll(
            @RequestParam(value="pageNumber",defaultValue = "0",required=false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue="10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name",required=false) String  sortBy,
            @RequestParam(value="sortDir",defaultValue="10",required = false) String sortDir
    ) {
        logger.info("get started pagination sorting Product in controller ");
        PageableResponse<ProductDto> pageableResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
   return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    //get all live
    // '/products/live'
    @GetMapping("/live")
    public ResponseEntity<PageableResponse<ProductDto>>getAllLive(
            @RequestParam(value="pageNumber",defaultValue = "0",required=false) int pageNumber,
            @RequestParam(value="pageSize",defaultValue="10",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue = "name",required=false) String  sortBy,
            @RequestParam(value="sortDir",defaultValue="10",required = false) String sortDir
    ) {
        logger.info("get started Live Product in controller ");
        PageableResponse<ProductDto> pageableResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }
        //search all
        @GetMapping("/search/{query}")
        public ResponseEntity<PageableResponse<ProductDto>>searchProduct(
                @PathVariable String query,
                @RequestParam(value="pageNumber",defaultValue = "0",required=false) int pageNumber,
                @RequestParam(value="pageSize",defaultValue="10",required = false) int pageSize,
                @RequestParam(value="sortBy",defaultValue = "name",required=false) String  sortBy,
                @RequestParam(value="sortDir",defaultValue="10",required = false) String sortDir

        ) {
            logger.info("searching by query key Product in controller ");
            PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
            return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
        }

}
