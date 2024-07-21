package com.secret.platform.group_code;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupCodesServiceImpl implements GroupCodesService {

    private final GroupCodesRepository groupCodesRepository;

    @Autowired
    public GroupCodesServiceImpl(GroupCodesRepository groupCodesRepository) {
        this.groupCodesRepository = groupCodesRepository;
    }

    @Override
    public GroupCodes createGroupCode(GroupCodes groupCode) {
        return groupCodesRepository.save(groupCode);
    }

    @Override
    public Optional<GroupCodes> getGroupCodeById(Long id) {
        return groupCodesRepository.findById(id);
    }

    @Override
    public List<GroupCodes> getAllGroupCodes() {
        return groupCodesRepository.findAll();
    }

    @Override
    public GroupCodes updateGroupCode(Long id, GroupCodes groupCode) {
        if (groupCodesRepository.existsById(id)) {
            groupCode.setId(id);
            return groupCodesRepository.save(groupCode);
        }
        throw new IllegalArgumentException("Group code not found");
    }

    @Override
    public void deleteGroupCode(Long id) {
        groupCodesRepository.deleteById(id);
    }

    @Override
    public boolean existsByGroupCode(String groupCode) {
        return groupCodesRepository.existsByGroupCode(groupCode);
    }
}
