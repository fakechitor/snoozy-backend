package org.snoozy.snoozygroup.repository;

import org.snoozy.snoozygroup.model.Group;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Long> {

    @EntityGraph(attributePaths = {"owner", "avatar"})
    Optional<Group> findDetailedById(Long id);
}
