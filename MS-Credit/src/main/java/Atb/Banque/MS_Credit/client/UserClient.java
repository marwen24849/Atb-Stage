package Atb.Banque.MS_Credit.client;


import Atb.Banque.MS_Credit.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MICRO-SERVICE-USER")
public interface UserClient {

    @GetMapping("/api/integration/users/{id}")
    public UserDto findUser(@PathVariable String id);

    @GetMapping("/api/integration/users/name/{username}")
    public UserDto findUserByUserName(@PathVariable String username);
}
