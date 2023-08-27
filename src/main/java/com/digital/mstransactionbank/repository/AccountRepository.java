package com.digital.mstransactionbank.repository;

import com.digital.mstransactionbank.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    @Transactional
    @Modifying
    @Query("update accounts a set a.availableLimit = :newLimit where a.id = :id")
    void updateAvailableLimit(@Param(value = "newLimit") Double newLimit,
                              @Param(value = "id") Long id);

    Optional<Account> findAccountByNumber(String documentNumber);
}
