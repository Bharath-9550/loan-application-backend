package com.bharath.loanapplication.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.bharath.loanapplication.exception.LoanNotFoundException;
import com.bharath.loanapplication.entity.LoanApplicationEntity;
import com.bharath.loanapplication.model.LoanApplicationRequest;
import com.bharath.loanapplication.model.LoanApplicationResponse;
import com.bharath.loanapplication.model.LoanDetailsResponse;
import com.bharath.loanapplication.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.bharath.loanapplication.model.LoanPageResponse;
import org.springframework.data.domain.Sort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LoanService {
    private static final Logger logger =
            LoggerFactory.getLogger(LoanService.class);
    @Autowired
    private LoanRepository loanRepository;

    // Apply Loan
    public LoanApplicationResponse processLoan(LoanApplicationRequest request) {
        logger.info("Received loan application from {}", request.getApplicantName());
        LoanApplicationResponse response = new LoanApplicationResponse();

        if (request.getCreditScore() >= 700 && request.getAnnualIncome() >= 50000) {
            response.setStatus("APPROVED");
            response.setMessage("Loan Approved");
            response.setApprovedAmount(request.getLoanAmount());
            response.setInterestRate(8.5);
        } else {
            response.setStatus("REJECTED");
            response.setMessage("Loan Rejected");
            response.setApprovedAmount(0);
            response.setInterestRate(0);
        }

        // Save to database
        LoanApplicationEntity entity = new LoanApplicationEntity();

        entity.setApplicantName(request.getApplicantName());
        entity.setEmail(request.getEmail());
        entity.setAge(request.getAge());
        entity.setAnnualIncome(request.getAnnualIncome());
        entity.setCreditScore(request.getCreditScore());
        entity.setLoanAmount(request.getLoanAmount());

        entity.setStatus(response.getStatus());
        entity.setMessage(response.getMessage());
        entity.setApprovedAmount(response.getApprovedAmount());
        entity.setInterestRate(response.getInterestRate());

        loanRepository.save(entity);

        return response;
    }

    // Get All Loans
    public List<LoanDetailsResponse> getAllLoans() {

        List<LoanApplicationEntity> loans = loanRepository.findAll();

        List<LoanDetailsResponse> responses = new ArrayList<>();

        for (LoanApplicationEntity entity : loans) {

            LoanDetailsResponse response = new LoanDetailsResponse();

            response.setId(entity.getId());
            response.setApplicantName(entity.getApplicantName());
            response.setEmail(entity.getEmail());
            response.setAge(entity.getAge());
            response.setAnnualIncome(entity.getAnnualIncome());
            response.setCreditScore(entity.getCreditScore());
            response.setLoanAmount(entity.getLoanAmount());

            response.setStatus(entity.getStatus());
            response.setMessage(entity.getMessage());
            response.setApprovedAmount(entity.getApprovedAmount());
            response.setInterestRate(entity.getInterestRate());

            responses.add(response);
        }

        return responses;

    }
    // Get Loans With Pagination
    public LoanPageResponse getLoansWithPagination(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<LoanApplicationEntity> loanPage = loanRepository.findAll(pageable);

        List<LoanDetailsResponse> responses = new ArrayList<>();

        for (LoanApplicationEntity entity : loanPage.getContent()) {

            LoanDetailsResponse response = new LoanDetailsResponse();

            response.setId(entity.getId());
            response.setApplicantName(entity.getApplicantName());
            response.setAge(entity.getAge());
            response.setAnnualIncome(entity.getAnnualIncome());
            response.setCreditScore(entity.getCreditScore());
            response.setLoanAmount(entity.getLoanAmount());

            response.setStatus(entity.getStatus());
            response.setMessage(entity.getMessage());
            response.setApprovedAmount(entity.getApprovedAmount());
            response.setInterestRate(entity.getInterestRate());

            responses.add(response);
        }

        LoanPageResponse pageResponse = new LoanPageResponse();

        pageResponse.setLoans(responses);
        pageResponse.setPageNumber(loanPage.getNumber());
        pageResponse.setPageSize(loanPage.getSize());
        pageResponse.setTotalElements(loanPage.getTotalElements());
        pageResponse.setTotalPages(loanPage.getTotalPages());
        pageResponse.setLast(loanPage.isLast());

        return pageResponse;
    }
    // Get Loans Sorted By Credit Score
    public List<LoanDetailsResponse> getLoansSortedByCreditScore() {

        List<LoanApplicationEntity> loans =
                loanRepository.findAll(Sort.by(Sort.Direction.DESC, "creditScore"));

        List<LoanDetailsResponse> responses = new ArrayList<>();

        for (LoanApplicationEntity entity : loans) {

            LoanDetailsResponse response = new LoanDetailsResponse();

            response.setId(entity.getId());
            response.setApplicantName(entity.getApplicantName());
            response.setAge(entity.getAge());
            response.setAnnualIncome(entity.getAnnualIncome());
            response.setCreditScore(entity.getCreditScore());
            response.setLoanAmount(entity.getLoanAmount());

            response.setStatus(entity.getStatus());
            response.setMessage(entity.getMessage());
            response.setApprovedAmount(entity.getApprovedAmount());
            response.setInterestRate(entity.getInterestRate());

            responses.add(response);
        }

        return responses;
    }
    // Search Loan By Applicant Name
    public List<LoanApplicationEntity> searchLoanByApplicantName(String applicantName) {

        return loanRepository.findByApplicantName(applicantName);

    }
    // Get Loan By ID
    public LoanDetailsResponse getLoanById(Long id) {

        Optional<LoanApplicationEntity> loan = loanRepository.findById(id);

        if (loan.isPresent()) {

            LoanApplicationEntity entity = loan.get();

            LoanDetailsResponse response = new LoanDetailsResponse();

            response.setId(entity.getId());
            response.setApplicantName(entity.getApplicantName());
            response.setEmail(entity.getEmail());
            response.setAge(entity.getAge());
            response.setAnnualIncome(entity.getAnnualIncome());
            response.setCreditScore(entity.getCreditScore());
            response.setLoanAmount(entity.getLoanAmount());

            response.setStatus(entity.getStatus());
            response.setMessage(entity.getMessage());
            response.setApprovedAmount(entity.getApprovedAmount());
            response.setInterestRate(entity.getInterestRate());

            return response;
        }

        throw new LoanNotFoundException("Loan application not found with id: " + id);
    }
    // Update Loan
    public LoanApplicationResponse updateLoan(Long id, LoanApplicationRequest request) {

        LoanApplicationEntity loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan application not found with id: " + id));

        LoanApplicationResponse response = new LoanApplicationResponse();

        if (request.getCreditScore() >= 700 && request.getAnnualIncome() >= 50000) {
            response.setStatus("APPROVED");
            response.setMessage("Loan Approved");
            response.setApprovedAmount(request.getLoanAmount());
            response.setInterestRate(8.5);
        } else {
            response.setStatus("REJECTED");
            response.setMessage("Loan Rejected");
            response.setApprovedAmount(0);
            response.setInterestRate(0);
        }

        loan.setApplicantName(request.getApplicantName());
        loan.setAge(request.getAge());
        loan.setAnnualIncome(request.getAnnualIncome());
        loan.setCreditScore(request.getCreditScore());
        loan.setLoanAmount(request.getLoanAmount());

        loan.setStatus(response.getStatus());
        loan.setMessage(response.getMessage());
        loan.setApprovedAmount(response.getApprovedAmount());
        loan.setInterestRate(response.getInterestRate());

        loanRepository.save(loan);

        return response;
    }
    // Delete Loan
    public String deleteLoan(Long id) {

        LoanApplicationEntity loan = loanRepository.findById(id)
                .orElseThrow(() ->
                        new LoanNotFoundException(
                                "Loan application not found with id: " + id));

        loanRepository.delete(loan);

        return "Loan application deleted successfully.";
    }
}