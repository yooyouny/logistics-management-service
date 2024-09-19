package com.sparta.company.infrastructure.repository.company;

import com.sparta.company.domain.Company;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, UUID>, CompanyRepositoryCustom {}
