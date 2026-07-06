package com.bharath.loanapplication.controller;

import com.bharath.loanapplication.entity.LoanApplicationEntity;
import com.bharath.loanapplication.model.LoanDetailsResponse;
import java.util.List;
import jakarta.validation.Valid;
import com.bharath.loanapplication.model.LoanApplicationRequest;
import com.bharath.loanapplication.model.LoanApplicationResponse;
import com.bharath.loanapplication.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.bharath.loanapplication.model.LoanPageResponse;

@RestController
@RequestMapping("/api/loans")
public class HomeController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/apply")
    public LoanApplicationResponse applyLoan(@Valid @RequestBody LoanApplicationRequest request) {
        return loanService.processLoan(request);
    }

    @GetMapping
    public List<LoanDetailsResponse> getAllLoans() {
        return loanService.getAllLoans();
    }

    @GetMapping("/{id}")
    public LoanDetailsResponse getLoanById(@PathVariable Long id) {
        return loanService.getLoanById(id);
    }

    @PutMapping("/{id}")
    public LoanApplicationResponse updateLoan(
            @PathVariable Long id,
            @Valid @RequestBody LoanApplicationRequest request) {

        return loanService.updateLoan(id, request);
    }
    @DeleteMapping("/{id}")
    public String deleteLoan(@PathVariable Long id) {

        return loanService.deleteLoan(id);
    }
    // Search Loan By Applicant Name
    @GetMapping("/search")
    public List<LoanApplicationEntity> searchLoanByApplicantName(
            @RequestParam String name) {

        return loanService.searchLoanByApplicantName(name);
    }
    // Get Loans With Pagination
    @GetMapping("/page")
    public LoanPageResponse getLoansWithPagination(
            @RequestParam int page,
            @RequestParam int size) {

        return loanService.getLoansWithPagination(page, size);
    }
    // Get Loans Sorted By Credit Score
    @GetMapping("/sort")
    public List<LoanDetailsResponse> getLoansSortedByCreditScore() {

        return loanService.getLoansSortedByCreditScore();
    }
}