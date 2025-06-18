package com.dami.hms.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "medicine_categories", schema = "hms")
public class MedicineCategory {
    @Id
    @Size(max = 255)
    @Column(name = "category_id", nullable = false)
    private String categoryId;

    @Lob
    @Column(name = "category_name")
    private String categoryName;

    @Column(name = "category_desc")
    private String categoryDesc;

    // Bidirectional relationship: One Category has Many Details
    @OneToMany(mappedBy = "medicineCategory", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MedicineDetail> medicineDetails;

}