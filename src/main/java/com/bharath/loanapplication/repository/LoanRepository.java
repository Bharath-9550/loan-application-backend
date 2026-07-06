package com.bharath.loanapplication.repository;

import com.bharath.loanapplication.entity.LoanApplicationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<LoanApplicationEntity, Long> {

    List<LoanApplicationEntity> findByApplicantName(String applicantName);

}