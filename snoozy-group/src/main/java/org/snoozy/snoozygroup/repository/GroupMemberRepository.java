package org.snoozy.snoozygroup.repository;


import org.snoozy.snoozygroup.model.GroupMember;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    @EntityGraph(attributePaths = {"group", "group.owner", "group.avatar"})
    List<GroupMember> findByUser_Id(Long userId);

    boolean existsByGroup_IdAndUser_Id(Long groupId, Long userId);
}