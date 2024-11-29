package com.poly.app.domain.auth.repsitory;


import com.poly.app.domain.model.User;
import com.poly.app.infrastructure.constant.RoleAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<User, String> {
    Optional<User> findByEmail(String email);

    Optional<User> findByPhoneNumber(String phoneNumber);

    Optional<User> findByEmailAndPassword(String email, String password);

    Integer countAllByRole(RoleAccount roleAccount);
}
