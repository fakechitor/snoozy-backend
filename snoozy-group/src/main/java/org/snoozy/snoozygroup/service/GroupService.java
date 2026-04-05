package org.snoozy.snoozygroup.service;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozygroup.dto.GroupMemberDto;
import org.snoozy.snoozygroup.dto.GroupRequestDto;
import org.snoozy.snoozygroup.dto.GroupResponseDto;
import org.snoozy.snoozygroup.model.Avatar;
import org.snoozy.snoozygroup.model.Group;
import org.snoozy.snoozygroup.model.GroupMember;
import org.snoozy.snoozygroup.model.User;
import org.snoozy.snoozygroup.repository.GroupMemberRepository;
import org.snoozy.snoozygroup.repository.GroupRepository;
import org.snoozy.snoozygroup.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;
    private final GroupMemberRepository groupMemberRepository;
    private final UserRepository userRepository;

    public GroupResponseDto getById(Long groupId) {
        Group group =  groupRepository.findDetailedById(groupId)
                .orElseThrow(() -> new RuntimeException("Group with id " + groupId + " not found"));

        return groupToDto(group);
    }

    public List<GroupResponseDto> getAllUserGroups(Long userId) {
        List<Group> userGroups =  groupMemberRepository.findByUser_Id(userId)
                .stream()
                .map(GroupMember::getGroup)
                .toList();

        return userGroups
                .stream()
                .map(this::groupToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GroupResponseDto createGroup(GroupRequestDto request, Long userId) {
        User owner = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User with id " + userId + " not found"));

        Group group = Group.builder()
                .name(request.name())
                .owner(owner)
                .build();

        Group savedGroup = groupRepository.save(group);

        Set<Long> memberIds = request.membersId() == null
                ? new HashSet<>()
                : request.membersId().stream()
                .filter(id -> !id.equals(userId))
                .collect(Collectors.toSet());

        List<User> members = userRepository.findAllById(memberIds);

        if (members.size() != memberIds.size()) {
            throw new RuntimeException("Some users were not found");
        }

        Set<GroupMember> groupMembers = new HashSet<>();

        groupMembers.add(GroupMember.builder()
                .group(savedGroup)
                .user(owner)
                .build());

        for (User member : members) {
            groupMembers.add(GroupMember.builder()
                    .group(savedGroup)
                    .user(member)
                    .build());
        }

        groupMemberRepository.saveAll(groupMembers);

        savedGroup.setMembers(groupMembers);

        return groupToDto(savedGroup);
    }

    private GroupMemberDto groupMemberToDto(GroupMember groupMember) {
        User user =  groupMember.getUser();

        Avatar avatar = groupMember.getGroup().getAvatar();
        String avatarUrl = avatar != null ? avatar.getObjectKey() : null;

        return new GroupMemberDto(
                user.getId(),
                user.getUsername(),
                avatarUrl
        );
    }

    private GroupResponseDto groupToDto(Group group) {
        Set<GroupMemberDto> groupMembers = group.getMembers()
                .stream()
                .map(this::groupMemberToDto)
                .collect(Collectors.toSet());

        String avatarUrl;

        Avatar avatar = group.getAvatar();
        avatarUrl = avatar != null ? avatar.getObjectKey() : null;


        return new GroupResponseDto(
                group.getId(),
                group.getName(),
                group.getOwner().getId(),
                 avatarUrl,
                groupMembers
        );
    }
}