package com.pruebatecnica.usuarioapi.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.pruebatecnica.usuarioapi.entity.User;
import com.pruebatecnica.usuarioapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Secret key para JWT - debería ser más compleja y guardarse de manera segura
    private final String JWT_SECRET = "eDq9H3jv6CvRfUjX1zS2tWxYbZc4v5w7";


    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User createUser(User user) throws Exception {

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new Exception("El correo ya está registrado.");
        }


        user.setPassword(passwordEncoder.encode(user.getPassword()));


        user.setToken(generateJWTToken(user));


        user.setCreated(new Date());
        user.setModified(new Date());
        user.setLastLogin(new Date());
        user.setIsActive(true);


        return userRepository.save(user);
    }

    private String generateJWTToken(User user) {

        Algorithm algorithm = Algorithm.HMAC256(JWT_SECRET);


        return JWT.create()
                .withSubject(user.getEmail())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000)) // 24 hrs
                .sign(algorithm);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}