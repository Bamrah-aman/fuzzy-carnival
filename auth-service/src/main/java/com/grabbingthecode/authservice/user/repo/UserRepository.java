package com.grabbingthecode.authservice.user.repo;

import com.grabbingthecode.authservice.user.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
}
