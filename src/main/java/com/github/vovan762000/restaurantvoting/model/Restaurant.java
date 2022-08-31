package com.github.vovan762000.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "restaurant")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true, exclude = {"dishes"})
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Restaurant extends BaseEntity {
    public Restaurant(Integer id, String restaurantName, List<Dish> dishes) {
        super(id);
        this.restaurantName = restaurantName;
        this.dishes = dishes;
    }

    public Restaurant(String restaurantName, List<Dish> dishes) {
        super(null);
        this.restaurantName = restaurantName;
        this.dishes = dishes;
    }

    @Column(name = "restaurant_name")
    @Size(max = 128)
    @NotBlank
    private String restaurantName;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Vote> votes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonManagedReference
    private List<Dish> dishes;
}
