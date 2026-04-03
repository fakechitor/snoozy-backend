package org.snoozy.snoozygroup.controller;

import lombok.RequiredArgsConstructor;
import org.snoozy.snoozygroup.dto.GroupRequestDto;
import org.snoozy.snoozygroup.dto.GroupResponseDto;
import org.snoozy.snoozygroup.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable Long id) {
        return ResponseEntity.ok(groupService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<GroupResponseDto>> getAllUserGroups(
            @RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(groupService.getAllUserGroups(userId));
    }

    @PostMapping
    public ResponseEntity<GroupResponseDto> createGroup(
            @RequestHeader("X-User-Id") Long userId,
            @RequestBody GroupRequestDto groupRequestDto) {
        return ResponseEntity.ok(groupService.createGroup(groupRequestDto, userId));
    }
}
