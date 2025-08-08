package com.itj.app.service;

import com.itj.app.model.User;
import com.itj.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    /**
     * Obtener todos los usuarios
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Obtener un usuario por ID
     */
    public Optional<User> getUserById(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        return userRepository.findById(id);
    }

    /**
     * Crear un nuevo usuario
     */
    public User createUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        
        // Validar que el email no esté duplicado
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con el email: " + user.getEmail());
        }
        
        // Validar campos requeridos
        validateUserFields(user);
        
        return userRepository.save(user);
    }

    /**
     * Actualizar un usuario existente
     */
    public User updateUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("El usuario no puede ser nulo");
        }
        
        if (user.getId() == null || user.getId() <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido para actualización");
        }
        
        // Verificar que el usuario existe
        if (!userRepository.existsById(user.getId())) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + user.getId());
        }
        
        // Validar que el email no esté duplicado (excluyendo el usuario actual)
        Optional<User> existingUserWithEmail = userRepository.findByEmail(user.getEmail());
        if (existingUserWithEmail.isPresent() && !existingUserWithEmail.get().getId().equals(user.getId())) {
            throw new IllegalArgumentException("Ya existe otro usuario con el email: " + user.getEmail());
        }
        
        // Validar campos requeridos
        validateUserFields(user);
        
        return userRepository.save(user);
    }

    /**
     * Eliminar un usuario
     */
    public void deleteUser(Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("ID de usuario inválido");
        }
        
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuario no encontrado con ID: " + id);
        }
        
        userRepository.deleteById(id);
    }

    /**
     * Validar campos del usuario
     */
    private void validateUserFields(User user) {
        if (user.getFirstName() == null || user.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        
        if (user.getLastName() == null || user.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es obligatorio");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new IllegalArgumentException("El email es obligatorio");
        }
        
        // Validar formato de email básico
        if (!user.getEmail().matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
            throw new IllegalArgumentException("El formato del email no es válido");
        }
    }

    /**
     * Buscar usuario por email
     */
    public Optional<User> getUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email no puede estar vacío");
        }
        return userRepository.findByEmail(email);
    }

    /**
     * Verificar si existe un usuario con el email dado
     */
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }
}
