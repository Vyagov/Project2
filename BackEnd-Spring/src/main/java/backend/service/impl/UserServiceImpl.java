package backend.service.impl;

import backend.exception.*;
import backend.model.binding.UserRegisterBindingModel;
import backend.model.entity.Role;
import backend.model.entity.User;
import backend.model.service.RoleServiceModel;
import backend.model.service.UserServiceModel;
import backend.model.view.UserViewModel;
import backend.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import backend.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User does not exists!");
        }
        return user;
    }

    @Override
    public UserServiceModel register(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        User saved = this.getUserByUsername(userServiceModel.getUsername());

        if (saved != null) {
            throw new UsernameAlreadyExistException("User with username " + saved.getUsername() + " already exists!");
        }
        if (this.userRepository.count() == 0) {
            Role mainAdmin = new Role("MAIN_ADMIN");
            Role adminRole = new Role("ADMIN");
            Role userRole = new Role("USER");

            user.setAuthorities(Set.of(mainAdmin, adminRole, userRole));
        } else {
            Role userRole = new Role("USER");

            user.setAuthorities(Set.of(userRole));
        }
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));

        try {
            createUser(user);
        } catch (Exception ignored) {
            throw new UserRegistrationException("Cannot register user with username " + user.getUsername());
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public UserServiceModel updateProfile(UserRegisterBindingModel userRegisterBindingModel) {
        User user = this.getUserByUsername(this.getLoggedUser());
        if (user != null) {
            user.setPassword(this.passwordEncoder.encode(userRegisterBindingModel.getPassword()));
            user.setFirstName(userRegisterBindingModel.getFirstName());
            user.setLastName(userRegisterBindingModel.getLastName());
            user.setEmail(userRegisterBindingModel.getEmail());
            createUser(user);
        } else {
            throw new UsernameAlreadyExistException("User is not Exist (internal error)!");
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public List<RoleServiceModel> getAllUserRoles(String username) {
        User user = this.getUserByUsername(username);

        if (user == null) {
            throw new UserIsNotExistException(username);
        }
        return user.getAuthorities().stream()
                .map(role -> this.modelMapper.map(role, RoleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserServiceModel> getAllUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserViewModel> getAllUsersForView() {
        return this.userRepository
                .findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<UserViewModel> getAllUsersWithoutAuthAdmin() {
        List<UserViewModel> users = this.userRepository
                .findAll()
                .stream()
                .map(user -> this.modelMapper.map(user, UserViewModel.class))
                .collect(Collectors.toList());
        return users.stream()
                .filter(userViewModel -> userViewModel
                        .getAuthorities().stream()
                        .noneMatch(role -> role.getAuthority().contains("ADMIN")))
                .collect(Collectors.toList());
    }

    @Override
    public UserServiceModel findUserByUsername(String loggedUserStr) {
        User user = this.getUserByUsername(loggedUserStr);

        return user != null ? this.modelMapper.map(user, UserServiceModel.class) : null;
    }

    @Override
    public UserServiceModel findUserById(String userId) {
        User user = getUserById(userId);

        if (user == null) {
            throw new UserIdNotFoundException(userId);
        }
        return this.modelMapper.map(user, UserServiceModel.class);
    }

    @Override
    public void delete(String id) {
        User user = this.getUserById(id);

        if (user == null) {
            throw new UserIdNotFoundException(id);
        }
        if (user.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("MAIN_ADMIN"))) {
            throw new UserIsNotHaveAccessException("Do not have access to delete MAIN ADMIN!");
        }
        this.userRepository.deleteById(id);
    }

    private User getUserByUsername(String username) {
        return this.userRepository.findByUsername(username).orElse(null);
    }

    private User getUserById(String userId) {
        return this.userRepository.findById(userId).orElse(null);
    }

    private void createUser(User user) {
        this.userRepository.saveAndFlush(user);
    }

    private String getLoggedUser() {
        String username = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }
        return new String(username);
    }
}
