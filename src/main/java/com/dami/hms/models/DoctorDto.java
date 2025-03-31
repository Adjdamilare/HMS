package com.dami.hms.models;

import java.math.BigDecimal;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDto {
    @Id
    private String doctorId;
    @NotBlank(message = "First Name is required")
    private String doctorFname;
    @NotBlank(message = "Last Name is required")
    private String doctorLname;
    @NotBlank(message = "Sex is required")
    private String doctorSex;
    @NotBlank(message = "NID is required")
    private String doctorNid;
    @NotBlank(message = "Home Phone is required")
    private String doctorHphone;
    @NotBlank(message = "Mobile Phone is required")
    private String doctorMphone;
    @NotBlank(message = "Address is required")
    private String doctorAddress;
    @NotBlank(message = "Qualification is required")
    private String doctorQualification;
    @NotBlank(message = "Specialization is required")
    private String doctorSpecialization;
    @NotBlank(message = "Type is required")
    private String doctorType;
    @NotNull(message = "Visiting Charge is required")
    private BigDecimal doctorVcharge;
    @NotNull(message = "Consultation Charge is required")
    private BigDecimal doctorCcharge;
    private String doctorNotes;
    @NotNull(message = "Basic Salary is required")
    private BigDecimal doctorBasicSal;
}