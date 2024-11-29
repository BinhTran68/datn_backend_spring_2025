package com.poly.app.domain.auth.repsitory;


import com.poly.app.domain.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthenAccountRepository extends AccountRepository {
    Optional<User> findByEmail(String email);
}
