package com.nitsoft.ecommerce.api.request.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CreateCategoryRequestModel {

    private Long parentId;

    @NotNull
    @NotBlank
    private String name;

    private Integer position;

    private String description;
}
