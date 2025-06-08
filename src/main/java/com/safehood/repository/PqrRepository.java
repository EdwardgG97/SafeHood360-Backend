package com.safehood.repository;

import com.safehood.model.Pqr;
import com.safehood.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PqrRepository extends JpaRepository<Pqr, Long> {
    List<Pqr> findByUser(User user);
}
