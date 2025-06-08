package com.conjuntos.conjuntosback.user.service;

import com.conjuntos.conjuntosback.auth.model.User;

/**
 * Service interface for user-related operations.
 * Following the Interface Segregation Principle, this interface only contains methods related to user operations.
 */
public interface UserService {
    
    /**
     * Retrieves the currently authenticated user.
     *
     * @return the current user
     * @throws org.springframework.security.core.userdetails.UsernameNotFoundException if the user is not found
     */
    User getCurrentUser();
}