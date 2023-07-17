package com.lcwd.electronic.store.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="categories")
public class Category {
    @Id
    @Column(name="id")
    private String categoryId;

    @Column(name="category_title",length=60,nullable=false)
    private String title;

    @Column(name="category_desc",length=50)
    private String description;

    private String coverImage;

    //other attributes if you want

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)//product on demand pr fetch hoga
    private List<Product> products=new ArrayList<>();


}
