package com.example.prodcut.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue
   /* @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cat_seq_gen")
    @SequenceGenerator(name = "cat_seq_gen", sequenceName = "category_seq", allocationSize = 1)*/

    public Integer id;
    private String name;
    private String description;
    @OneToMany(mappedBy = "category",cascade =CascadeType.REMOVE)
    private List<Product> productList;
}
