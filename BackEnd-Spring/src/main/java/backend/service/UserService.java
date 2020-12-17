package backend.service;

import backend.model.binding.UserRegisterBindingModel;
import backend.model.service.RoleServiceModel;
import backend.model.service.UserServiceModel;
import backend.model.view.UserViewModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserServiceModel register(UserServiceModel userServiceModel);

    UserServiceModel updateProfile(UserRegisterBindingModel userRegisterBindingModel);

    List<UserServiceModel> getAllUsers();

    List<UserViewModel> getAllUsersForView();

    UserServiceModel findUserByUsername(String username);

    UserServiceModel findUserById(String userId);

    List<RoleServiceModel> getAllUserRoles(String username);

    List<UserViewModel> getAllUsersWithoutAuthAdmin();

    void delete(String id);
}
