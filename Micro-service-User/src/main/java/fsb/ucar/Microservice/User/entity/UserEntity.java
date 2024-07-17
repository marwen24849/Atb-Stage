package fsb.ucar.Microservice.User.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "userEntity_T")
public class UserEntity {

    @Id
    private String id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles = new ArrayList<>();



}
