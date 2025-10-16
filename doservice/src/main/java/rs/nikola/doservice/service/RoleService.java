package rs.nikola.doservice.service;


import org.springframework.stereotype.Service;
import rs.nikola.doservice.entity.Role;
import rs.nikola.doservice.repository.RoleRepository;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;

    }


    public List<Role> getAll(){
        return roleRepository.findAll();
    }
}
