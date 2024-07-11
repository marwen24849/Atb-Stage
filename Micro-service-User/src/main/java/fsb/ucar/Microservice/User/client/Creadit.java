package fsb.ucar.Microservice.User.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "MS-CREADIT")
public interface Creadit {



}
