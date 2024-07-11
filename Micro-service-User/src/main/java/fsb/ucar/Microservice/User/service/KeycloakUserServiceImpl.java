package fsb.ucar.Microservice.User.service;



import fsb.ucar.Microservice.User.entity.UserEntity;
import fsb.ucar.Microservice.User.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.*;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j

public class KeycloakUserServiceImpl implements KeycloakUserService{

    @Value("${keycloak.realm}")
    private String realm;
    @Autowired
    private Keycloak keycloak;
    @Autowired
    private UserRepository userRepository;




    public List<UserRepresentation> getAllUsers() {
        return keycloak.realm(realm).users().list();
    }

    private UsersResource getUsersResource() {
        RealmResource realm1 = keycloak.realm(realm);
        return realm1.users();
    }

    @Override
    public UserRepresentation getUserById(String userId) {
        return  getUsersResource().get(userId).toRepresentation();
    }

    @Override
    public void deleteUserById(String userId) {
        getUsersResource().delete(userId);
        this.userRepository.deleteById(userId);
    }


    @Override
    public void emailVerification(String userId){
        UsersResource usersResource = getUsersResource();
        usersResource.get(userId).sendVerifyEmail();
    }

    public UserResource getUserResource(String userId){
        UsersResource usersResource = getUsersResource();
        return usersResource.get(userId);
    }

    public RoleMappingResource getRoleResource(String userId){
        return getUsersResource().get(userId).roles();
    }

    @Override
    public void updatePassword(String userId) {
        UserResource userResource = getUserResource(userId);
        List<String> actions= new ArrayList<>();
        actions.add("UPDATE_PASSWORD");
        userResource.executeActionsEmail(actions);
    }

    public void synchronizeUsers() {
        List<UserRepresentation> keycloakUsers = getAllUsers();
        List<String> keycloakUsernames = keycloakUsers.stream()
                .map(UserRepresentation::getUsername)
                .collect(Collectors.toList());
        List<UserEntity> dbUsers = userRepository.findAll();
        for (UserEntity dbUser : dbUsers) {
            if (!keycloakUsernames.contains(dbUser.getUsername())) {
                userRepository.delete(dbUser);
            }
        }

        for (UserRepresentation keycloakUser : keycloakUsers) {
            Optional<UserEntity> existingUser = userRepository.findByUsername(keycloakUser.getUsername());
            if (existingUser.isPresent()) {
                UserEntity userEntity = existingUser.get();
                userEntity.setFirstName(keycloakUser.getFirstName());
                userEntity.setLastName(keycloakUser.getLastName());
                userEntity.setEmail(keycloakUser.getEmail());
                List<RoleRepresentation> roleRepresentations = getUserRoles(keycloakUser.getId());
                if(userEntity.getRoles() == null)
                    userEntity.setRoles(new ArrayList<>());
                roleRepresentations.stream()
                        .map(RoleRepresentation::getName)
                        .filter(name -> !userEntity.getRoles().contains(name))
                        .forEach(userEntity.getRoles()::add);
                userRepository.save(userEntity);
            } else {
                UserEntity newUser = new UserEntity();
                newUser.setId(keycloakUser.getId());
                newUser.setUsername(keycloakUser.getUsername());
                newUser.setEmail(keycloakUser.getEmail());
                newUser.setFirstName(keycloakUser.getFirstName());
                newUser.setLastName(keycloakUser.getLastName());
                List<RoleRepresentation> roleRepresentations = getUserRoles(keycloakUser.getId());
                newUser.setRoles(new ArrayList<>());
                roleRepresentations.forEach(r -> {
                    newUser.getRoles().add(r.getName());
                });
                userRepository.save(newUser);
            }
        }
    }


    @Override
    public List<UserEntity> AllUser() {
        return  this.userRepository.findAll();
    }

    public UserEntity findUserById(String id){
        Optional<UserEntity> optionalUser = this.userRepository.findById(id);
        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            return this.userRepository.save(user);
        }
        return null;
    }

    @Override
    public UserEntity findUserByUsername(String USerName) {
        Optional<UserEntity> optionalUser = this.userRepository.findByUsername(USerName);
        if(optionalUser.isPresent()){
            UserEntity user = optionalUser.get();
            return user;
        }
        return null;
    }

    public List<RoleRepresentation> getUserRoles(String userId) {
        RealmResource realmResource = keycloak.realm(realm);
        UserResource userResource = realmResource.users().get(userId);
        return userResource.roles().realmLevel().listEffective();
    }


}
