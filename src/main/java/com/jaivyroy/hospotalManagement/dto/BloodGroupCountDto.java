package com.jaivyroy.hospotalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BloodGroupCountDto { // when the  client want to only the count  of the all  total bloodgrp
    // int he hostpital bd the count will count the with the  jpql hibernet
    private String bloodGroup;
    private Long count;
}
