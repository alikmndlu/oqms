package com.alikmndlu.oqms.service;

import com.alikmndlu.oqms.model.Role;

public interface RoleService extends BaseService<Role, Long> {

    void addRoleToUser(String username, String roleName);
}
