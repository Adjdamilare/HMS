package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "medicine_details", schema = "hms")
public class MedicineDetail {
    @Id
    @Size(max = 255)
    @Column(name = "product_id", nullable = false)
    private String productId;

    @Lob
    @Column(name = "product_name")
    private String productName;

    @Column(name = "units_in_stock")
    private Long unitsInStock;

    @Column(name = "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name = "reorder_level")
    private Long reorderLevel;

    // Update the relationship to properly map with MedicineCategory
    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private MedicineCategory medicineCategory;
}