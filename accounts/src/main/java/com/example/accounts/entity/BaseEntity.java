package com.example.accounts.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BaseEntity {

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column(updatable = false)
    private LocalDateTime updatedAt;

    @Column(insertable = false)
    private String createdBy;

    @Column(insertable = false)
    private String updatedBy;

}
