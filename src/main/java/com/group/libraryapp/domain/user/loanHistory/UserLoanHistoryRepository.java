package com.group.libraryapp.domain.user.loanHistory;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserLoanHistoryRepository extends JpaRepository<UserLoanHistory,Long> {
    boolean existsByBookNameAndIsReturn(String bookName, Boolean isReturn);

    UserLoanHistory findByUserIdAndBookName(Long userId, String bookName);
}
