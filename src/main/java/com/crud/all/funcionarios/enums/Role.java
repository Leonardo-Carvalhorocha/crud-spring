package com.crud.all.funcionarios.enums;

public enum Role {
        ADMIN_ROLE("administrador"),
        MANAGER_ROLE("Gerente"),
        EMPLOYEE_ROLE("Funcionario");

    private final String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescricao() {
        return description;
    }
}
