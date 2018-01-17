package com.nitsoft.ecommerce.service;

import com.nitsoft.ecommerce.database.model.Company;
import com.nitsoft.ecommerce.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Iterable<Company> findAll() {
        return companyRepository.findAll();
    }

    public Company findByCompanyId(long companyId) {
        return companyRepository.findByCompanyId(companyId);
    }
}
