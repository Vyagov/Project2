package backend.service.impl;

import backend.exception.UserIsNotExistException;
import backend.model.entity.Role;
import backend.model.entity.User;
import backend.model.service.NewRolesServiceModel;
import backend.repository.RoleRepository;
import backend.repository.UserRepository;
import backend.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void editRoles(NewRolesServiceModel newRolesServiceModel) {
        User user = this.findUserById(newRolesServiceModel.getUserId());

        if (user == null) {
            throw new UserIsNotExistException("User is not Exist!");
        }
        user.getAuthorities().clear();

        Role userRole = new Role("USER");
        user.getAuthorities().add(userRole);

        if (newRolesServiceModel.getRoleAdmin().equals("1")) {
            Role adminRole = new Role("ADMIN");
            user.getAuthorities().add(adminRole);
        }
        this.createUser(user);
    }

    private User findUserById(String id) {
        return this.userRepository.findById(id).orElse(null);
    }

    private void createUser(User user) {
        this.userRepository.saveAndFlush(user);
    }
}
