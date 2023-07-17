package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.ApiResponseMessage;
import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.dto.ProductDto;
import com.lcwd.electronic.store.service.CategoryService;
import com.lcwd.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    private static Logger logger=  LoggerFactory.getLogger( CategoryController.class);
    /**
     * auhor's name :krishna
     * @param Category related all controllerApi
     * @return
     */

    //create

    /**
     * Api note:Create category
     * @param categoryDto
     * @return
     */
    @PostMapping
   public ResponseEntity<CategoryDto>createCategory(@Valid @RequestBody CategoryDto categoryDto){
       //call service to save object
        logger.info(" Before initiated createdCategory in controller :="+ categoryDto);

       CategoryDto categoryDto1 = categoryService.create(categoryDto);

        logger.info(" after initiated createdCategory in controller :="+ categoryDto);
       return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
   }
    //update

    /**
     * ApiNotes:UpdateCategory
     * @param categoryId
     * @param categoryDto
     * @return
     */
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>updateCategory(
           @PathVariable String categoryId,
            @RequestBody CategoryDto categoryDto
    ){
        logger.info(" Before initiated updatedCategory in controller by categoryId :="+ categoryId);
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        logger.info(" after initiated updatedCategory in controller by categoryId :="+ categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }
    //delete

    /**
     * apiNotes:deleteCategory byId
     * @param categoryId
     * @return
     */
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage>deleteCategory(@PathVariable String categoryId){
        logger.info(" Before initiated Delete operation Category in controller by categoryId :="+ categoryId);
         categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category is delete  successfully!!!!").status(HttpStatus.OK).success(true).build();
        logger.info(" After initiated Delete operation Category in controller by categoryId :="+ categoryId);
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);

    }
    //getall

    /**
     * apiNotes:Get All by pagination and sorting
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
public ResponseEntity<PageableResponse<CategoryDto>>getAll(

        @RequestParam(value="pageNumber",defaultValue = "0",required=false)int pageNumber,
        @RequestParam(value="pageSize",defaultValue = "10",required=false)int pageSize,
        @RequestParam(value="sortBy",defaultValue = "title",required=false)String sortBy,
        @RequestParam(value="sortDir",defaultValue = "asc",required=false)String sortDir
){
        logger.info(" Before operation of Pagination and sorting by GetAll operation in Category Controller");
    PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        logger.info(" After operation of Pagination and sorting by GetAll operation in Category Controller");
    return new ResponseEntity<>(pageableResponse,HttpStatus.OK);
    }
    //get single

    /**
     * ApiNotes:Get Sinlge by Id
     * @param categoryId
     * @return
     */
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto>getSingle(@PathVariable String categoryId){
        logger.info(" Before operation of get single in CategoryController by id" + categoryId);
        CategoryDto categoryDto = categoryService.get(categoryId);
        logger.info(" after operation of get single in CategoryController " + categoryId);
        return ResponseEntity.ok(categoryDto);
    }
//create prodduct with category
    @PostMapping("/{categoryId}/products")
    public ResponseEntity<ProductDto>createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto dto
    ){
        ProductDto productWithCategory = productService.createWithCategory(dto, categoryId);
        return new ResponseEntity<>(productWithCategory,HttpStatus.CREATED);
    }


}
