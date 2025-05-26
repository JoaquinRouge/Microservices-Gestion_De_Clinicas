package com.joaquinrouge.clinics.auth.dto;

import java.util.Set;

public record RoleModel(String role,Set<PermissionModel> permissions) {

}
