package com.digitalHouse.beerClub.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "faqs")
public class Faqs {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column
    private String question;
    @Column
    private String answer;

    public void setId(Long id) {
        this.id = id;
    }
}
