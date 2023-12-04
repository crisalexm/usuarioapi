package com.pruebatecnica.usuarioapi.entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "phones")
@Getter @Setter @NoArgsConstructor
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String citycode;
    private String countrycode;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}