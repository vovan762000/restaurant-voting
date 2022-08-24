package com.github.vovan762000.restaurantvoting.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vote")
@NoArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Vote extends BaseEntity {

    public Vote(Integer id, LocalDateTime dateTime, Restaurant restaurant, User user) {
        super(id);
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.user = user;
    }

    public Vote(LocalDateTime dateTime, Restaurant restaurant, User user) {
        this.dateTime = dateTime;
        this.restaurant = restaurant;
        this.user = user;
    }

    @Column(name = "date_time", nullable = false)
    @NotNull
    private LocalDateTime dateTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private User user;

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

}
