package ma.enset.hospitalapp.service;

import ma.enset.hospitalapp.entities.Role;
import ma.enset.hospitalapp.entities.User;

public interface IUserService {
    User addNewUser(User user);

    Role addNewRole(Role role);

    User findUserByUserName(String userName);

    Role findRoleByRoleName(String roleName);

    void addRoleToUser(String username, String roleName);
}
