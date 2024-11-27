package com.grabbingthecode.authservice.token.repo;

import com.grabbingthecode.authservice.token.modal.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Integer> {
}
