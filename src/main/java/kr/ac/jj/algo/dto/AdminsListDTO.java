package kr.ac.jj.algo.dto;

import java.util.List;

public class AdminsListDTO {
    private List<UserDTO> admins;
    private List<UserDTO> adminAssistants;

    public AdminsListDTO() {}

    public AdminsListDTO(List<UserDTO> admins, List<UserDTO> adminAssistants) {
        this.admins = admins;
        this.adminAssistants = adminAssistants;
    }

    public List<UserDTO> getAdmins() {
        return admins;
    }

    public void setAdmins(List<UserDTO> admins) {
        this.admins = admins;
    }

    public List<UserDTO> getAdminAssistants() {
        return adminAssistants;
    }

    public void setAdminAssistants(List<UserDTO> adminAssistants) {
        this.adminAssistants = adminAssistants;
    }
}
