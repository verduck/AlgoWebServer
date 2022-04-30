package kr.ac.jj.algo.service;

import kr.ac.jj.algo.domain.Authority;
import kr.ac.jj.algo.domain.User;
import kr.ac.jj.algo.dto.AdminsListDTO;
import kr.ac.jj.algo.dto.UserDTO;
import kr.ac.jj.algo.exception.ErrorCode;
import kr.ac.jj.algo.exception.ApiException;
import kr.ac.jj.algo.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Autowired
    public UserService(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    public UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    public UserDTO.Detail loadUserDetailByUsername(String username) {
        var user = loadUserByUsername(username);
        return modelMapper.map(user, UserDTO.Detail.class);
    }

    public User loadUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));
    }

    public AdminsListDTO loadAdmins() {
        var result = new AdminsListDTO();
        result.setAdmins(loadUsersByAuthority(Authority.ROLE_ADMIN));
        result.setAdminAssistants(loadUsersByAuthority(Authority.ROLE_ADMIN_ASSISTANT));
        return result;
    }

    public List<UserDTO> loadUsersByAuthority(Authority authority) {
        return userRepository.findByAuthority(authority).stream().map(u -> modelMapper.map(u, UserDTO.class)).collect(Collectors.toList());
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

}
