package com.nitsoft.ecommerce.database.model;

import java.io.Serializable;
import java.util.Objects;

public class ProductAttributeDetailPK implements Serializable {

    private Long productId;
    private Long attributeId;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + Objects.hashCode(this.productId);
        hash = 29 * hash + Objects.hashCode(this.attributeId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductAttributeDetailPK other = (ProductAttributeDetailPK) obj;
        if (!Objects.equals(this.productId, other.productId)) {
            return false;
        }
        if (!Objects.equals(this.attributeId, other.attributeId)) {
            return false;
        }
        return true;
    }

}
