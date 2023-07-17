package com.lcwd.electronic.store.repository;



import com.lcwd.electronic.store.entity.Category;
import com.lcwd.electronic.store.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {
    //search
    Page<Product> findByTitleContaining(String subTitle, Pageable pageable);
    Page<Product>findByLiveTrue(Pageable pageable);

     Page<Product> findByCategory(Category category,Pageable pageable);

    //other
    //customfinder methods
    //query methods


}
