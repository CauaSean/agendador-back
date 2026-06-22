package com.agendador.back.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "patients")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-incremento (1, 2, 3...)
    private Long id;

    private String nome;
    private String email;
    private String tel;
    private String modal;
    private String status;
    private String data;
    private String hora;

    @Column(columnDefinition = "TEXT")
    private String msg;

    @Column(name = "cal_id")
    private String calId;

}
