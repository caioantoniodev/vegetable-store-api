package tech.dev.vegetablestoreapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.dev.vegetablestoreapi.domain.Nuts;

import java.util.UUID;

@Repository
public interface NutsRepository extends JpaRepository<Nuts, UUID> {
}
