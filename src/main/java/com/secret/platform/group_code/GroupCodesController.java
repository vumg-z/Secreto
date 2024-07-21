package com.secret.platform.group_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/group-codes")
public class GroupCodesController {

    private final GroupCodesService groupCodesService;

    @Autowired
    public GroupCodesController(GroupCodesService groupCodesService) {
        this.groupCodesService = groupCodesService;
    }

    @PostMapping
    public ResponseEntity<GroupCodes> createGroupCode(@RequestBody GroupCodes groupCode) {
        GroupCodes createdGroupCode = groupCodesService.createGroupCode(groupCode);
        return ResponseEntity.ok(createdGroupCode);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupCodes> getGroupCodeById(@PathVariable Long id) {
        Optional<GroupCodes> groupCode = groupCodesService.getGroupCodeById(id);
        return groupCode.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<GroupCodes>> getAllGroupCodes() {
        List<GroupCodes> groupCodes = groupCodesService.getAllGroupCodes();
        return ResponseEntity.ok(groupCodes);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupCodes> updateGroupCode(@PathVariable Long id, @RequestBody GroupCodes groupCode) {
        GroupCodes updatedGroupCode = groupCodesService.updateGroupCode(id, groupCode);
        return ResponseEntity.ok(updatedGroupCode);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroupCode(@PathVariable Long id) {
        groupCodesService.deleteGroupCode(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{groupCode}")
    public ResponseEntity<Boolean> existsByGroupCode(@PathVariable String groupCode) {
        boolean exists = groupCodesService.existsByGroupCode(groupCode);
        return ResponseEntity.ok(exists);
    }
}
