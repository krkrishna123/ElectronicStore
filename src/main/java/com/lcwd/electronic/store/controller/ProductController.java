package com.lcwd.electronic.store.controller;


import com.lcwd.electronic.store.dto.*;
import com.lcwd.electronic.store.service.FileService;
import com.lcwd.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;
    private static Logger logger=  LoggerFactory.getLogger( ProductController.class);

    @Autowired
    private FileService fileService;

    @Value("${product.profile.image.path}")
    private String imagePath;



    /**
     * Authors:Krishna
     * ApiNotes:Create
     * @param productDto
     * @return
     */
    @PostMapping
   public ResponseEntity<ProductDto>createProduct(@RequestBody ProductDto productDto){
        logger.info(" Before initiated createdProduct in controller :="+ productDto);
       ProductDto createdProduct = productService.create(productDto);
        logger.info(" After initiated createdProduct in controller :="+ productDto);
return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
   }


    /**
     * ApiNotes:update
     * @param productId
     * @param productDto
     * @return
     */
    @PutMapping("/{productId}")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable String productId ,@RequestBody ProductDto productDto) {
        logger.info(" Before initiated updateProduct in controller :="+ productId);
        ProductDto updateProduct = productService.update(productDto,productId );
        logger.info(" After initiated updateProduct in controller :="+ productId);
        return new ResponseEntity<>(updateProduct, HttpStatus.OK);
    }


    /**
     * ApiNotes:delete
     * @param productId
     * @return
     */
    @DeleteMapping("/{productId}")
    public ResponseEntity<ApiResponseMessage>delete(@PathVariable String productId){
        logger.info(" Before initiated DeleteProduct in controller :="+ productId);
        productService.delete(productId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("product is deleted successfully !!").status(HttpStatus.OK).success(true).build();
        logger.info(" after initiated deleteProduct in controller :="+ productId);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }


    /**
     * apiNotes:getsingle by id
     * @param productId
     * @return
     */
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDto>getProduct(@PathVariable String productId ) {
        logger.info(" Before initiated get ProductById in controller :="+ productId);
        ProductDto productDto = productService.get(productId);
        logger.info(" After initiated get ProductById in controller :="+ productId);
        return new ResponseEntity<>(productDto, HttpStatus.OK);
    }


    /**
     * Apinots:getAll pagination shorting
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
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



    /**
     * ApiNotes:Live url is "'/products/live'"
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
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


    /**
     * apiNotes:search all
     * @param query
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
        @GetMapping("/search/{query}")
        public ResponseEntity<PageableResponse<ProductDto>>searchProduct(
                @PathVariable String query,
                @RequestParam(value="pageNumber",defaultValue = "0",required=false) int pageNumber,
                @RequestParam(value="pageSize",defaultValue="10",required = false) int pageSize,
                @RequestParam(value="sortBy",defaultValue = "title",required=false) String  sortBy,
                @RequestParam(value="sortDir",defaultValue="10",required = false) String sortDir

        ) {
            logger.info("searching by query key Product in controller ");
            PageableResponse<ProductDto> pageableResponse = productService.searchByTitle(query,pageNumber, pageSize, sortBy, sortDir);
            return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
        }
    //upload  Image

    /**
     * apiNotes:Imageuploading Api
     * @param productId
     * @param image
     * @return
     * @throws IOException
     */
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse>uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage") MultipartFile image
    ) throws IOException {
        logger.info("uploading image is  going on below  in Productcontroller ");
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.get(productId);
        productDto.setProductImageName(fileName);
        ProductDto updateProduct = productService.update(productDto, productId);
        ImageResponse response = ImageResponse.builder().imageName(updateProduct.getProductImageName()).message("Product image is sucess fully uploaded").status(HttpStatus.CREATED).success(true).build();
       return new ResponseEntity<>(response,HttpStatus.CREATED);
    }
    //serve image in product

    /**
     * ApiNotes:image uploaded by productId
     * @param productId
     * @param response
     * @throws IOException
     */
    @GetMapping("/image/{productId}")
    public void serveProductImage(@PathVariable String productId, HttpServletResponse response) throws IOException {
        logger.info("uploading image is  going on below  in Productcontroller by ProductId ");
        ProductDto productDto = productService.get(productId);
        InputStream resource = fileService.getResource(imagePath,productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }}
