package com.nitsoft.ecommerce.api.request.model;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateCategoryRequestModel {

    private long id;

    private Long parentId;

    @NotNull
    @NotBlank
    private String name;

    @Min(0)
    @Max(1)
    private int status;

    private int position;

    private String description;
}
