package com.dami.hms.repositories;

import com.dami.hms.entities.BedDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BedDetailsRepository extends JpaRepository<BedDetail, String> {

    List<BedDetail> findByStatus(Byte status);}
