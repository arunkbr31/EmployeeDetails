package com.arunkumar.springbootwebioc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.arunkumar.springbootwebioc.entity.TaxDeduction;

@Repository
public interface TaxDeductionRepository extends JpaRepository<TaxDeduction, Long>{

}
