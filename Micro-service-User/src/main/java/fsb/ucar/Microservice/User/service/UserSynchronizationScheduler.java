package fsb.ucar.Microservice.User.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class UserSynchronizationScheduler {

    private final KeycloakUserServiceImpl keycloakUserService;
    @Scheduled(fixedRate = 30000)
    public void synchronizeUsers() {
        keycloakUserService.synchronizeUsers();
    }
}
