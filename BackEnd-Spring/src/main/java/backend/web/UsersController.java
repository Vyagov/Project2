package backend.web;

import backend.exception.ValidationResponse;
import backend.model.binding.UserRegisterBindingModel;
import backend.model.entity.User;
import backend.model.service.UserServiceModel;
import backend.service.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UsersController {

    public static final Logger logger = LoggerFactory.getLogger(UsersController.class);
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UsersController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @CrossOrigin
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterBindingModel userRegisterBindingModel,
                                      BindingResult bindingResult) {
        ValidationResponse validationResponse = new ValidationResponse();

        if (bindingResult.hasErrors()) {
            validationResponse.setErrorMessageList(bindingResult.getAllErrors());
            validationResponse.setStatus("FAIL");

            return new ResponseEntity<>(validationResponse, HttpStatus.CONFLICT);
        }
        this.userService.register(this.modelMapper.map(userRegisterBindingModel, UserServiceModel.class));

        return new ResponseEntity<User>(HttpStatus.CREATED);
    }

    @CrossOrigin
    @GetMapping("/login")
    public Principal user(Principal principal) {
        logger.info("user logged " + principal);
        return principal;
    }
}
