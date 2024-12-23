package diy.mqml.datalayer.persistence.entity.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
@Getter
public enum UserRole {
    APPLICATION_MANAGER(UserRoleConstants.SECURITY_ROLE_APPLICATION_MANAGER, UserRoleConstants.AUTHORITY_APPLICATION_MANAGER , "Application Manager", "Manage application configuration"),
    MILESTONE_EDITOR(UserRoleConstants.SECURITY_ROLE_MILESTONE_EDITOR, UserRoleConstants.AUTHORITY_MILESTONE_EDITOR, "Milestone Editor", "Manage and edit milestones"),
    WORKFLOW_EDITOR(UserRoleConstants.SECURITY_ROLE_WORKFLOW_EDITOR, UserRoleConstants.AUTHORITY_WORKFLOW_EDITOR, "Workflow Editor", "Manage and edit workflows"),
    VIEWER(UserRoleConstants.SECURITY_ROLE_VIEWER, UserRoleConstants.AUTHORITY_VIEWER, "Viewer", "View and track samples");

    public static final String ROLE_PREFIX = "ROLE_";
    public static final String AUTHORIZED_SECURITY_ROLE = ROLE_PREFIX + "AUTHORIZED";
    public static final String UNAUTHORIZED_SECURITY_ROLE = ROLE_PREFIX + "UNAUTHORIZED";

    private final String securityRole;
    private final String authority;
    private final String displayName;
    private final String description;

    public static UserRole getRole(String roleName) {
        if(roleName != null) {
            if(roleName.startsWith(ROLE_PREFIX)) {
                roleName = roleName.substring(ROLE_PREFIX.length());
            }
            return UserRole.valueOf(roleName);
        }
        return null;
    }

    UserRole(String securityRole, String authority, String displayName, String description) {
        this.securityRole = securityRole;
        this.authority = authority;
        this.displayName = displayName;
        this.description = description;
    }

    public String getName() {
        return name();
    }

    @Override
    public String toString() {
        return getDisplayName();
    }
}
