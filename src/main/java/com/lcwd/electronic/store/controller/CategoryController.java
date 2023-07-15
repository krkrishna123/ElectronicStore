package com.lcwd.electronic.store.controller;

import com.lcwd.electronic.store.dto.ApiResponseMessage;
import com.lcwd.electronic.store.dto.CategoryDto;
import com.lcwd.electronic.store.dto.PageableResponse;
import com.lcwd.electronic.store.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * auhor's name :krishna
     * @param Category related all controllerApi
     * @return
     */

    //create

    /**
     * api notes:createCategory
     * @param categoryDto
     * @return
     */
    @PostMapping
   public ResponseEntity<CategoryDto>createCategory(@RequestBody CategoryDto categoryDto){
       //call service to save object
       CategoryDto categoryDto1 = categoryService.create(categoryDto);
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
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
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
categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder().message("Category is delete  successfully!!!!").status(HttpStatus.OK).success(true).build();
   return new ResponseEntity<>(responseMessage,HttpStatus.OK);

    }
    //getall

    /**
     * apiNotes:Get All
     * @param Pagination and sorting for this
     * @param pageNumber
     * @param pageSize
     * @param sortBy
     * @param sortDir
     * @return
     */
    @GetMapping
public ResponseEntity<PageableResponse<CategoryDto>>getAll(

        @RequestParam(value="pageNumber",defaultValue = "0",required=false)int pageNumber,
        @RequestParam(value="pageSize",defaultValue = "0",required=false)int pageSize,
        @RequestParam(value="sortBy",defaultValue = "title",required=false)String sortBy,
        @RequestParam(value="sortDir",defaultValue = "asc",required=false)String sortDir
){
    PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
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
        CategoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);
    }



}
