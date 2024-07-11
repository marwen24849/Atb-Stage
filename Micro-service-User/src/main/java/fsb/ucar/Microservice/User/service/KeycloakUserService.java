package fsb.ucar.Microservice.User.service;

import fsb.ucar.Microservice.User.entity.UserEntity;
import org.keycloak.admin.client.resource.RoleMappingResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;

import java.util.List;

public interface KeycloakUserService {




    UserRepresentation getUserById(String userId);
    void deleteUserById(String userId);
    void emailVerification(String userId);
    UserResource getUserResource(String userId);
    void updatePassword(String userId);
    public List<UserRepresentation> getAllUsers();

    public void synchronizeUsers();

    public RoleMappingResource getRoleResource(String userId);

    public List<UserEntity> AllUser();

    public UserEntity findUserById(String id);

    public UserEntity findUserByUsername(String USerName);

    public List<RoleRepresentation> getUserRoles(String userId);
}