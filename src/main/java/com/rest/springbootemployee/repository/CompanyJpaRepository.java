package com.rest.springbootemployee.repository;

import com.rest.springbootemployee.pojo.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyJpaRepository extends JpaRepository<Company, Integer> {
}
