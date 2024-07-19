package com.secret.platform.group_code;

import java.util.List;
import java.util.Optional;

public interface GroupCodesService {
    GroupCodes createGroupCode(GroupCodes groupCode);
    Optional<GroupCodes> getGroupCodeById(Long id);
    List<GroupCodes> getAllGroupCodes();
    GroupCodes updateGroupCode(Long id, GroupCodes groupCode);
    void deleteGroupCode(Long id);
    boolean existsByGroupCode(String groupCode);
}
