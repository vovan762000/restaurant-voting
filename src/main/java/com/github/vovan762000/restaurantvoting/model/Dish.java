package com.github.vovan762000.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Entity
@Table(name = "dish")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Dish extends BaseEntity {

    public Dish(Integer id, String dishName, BigDecimal price, Restaurant restaurant) {
        super(id);
        this.dishName = dishName;
        this.price = price;
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String dishName, BigDecimal price) {
        super(id);
        this.dishName = dishName;
        this.price = price;
    }

    @Column(name = "dish_name")
    @Size(max = 128)
    @NotBlank
    private String dishName;

    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 3, fraction = 2)
    @NotNull
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Restaurant restaurant;
}
