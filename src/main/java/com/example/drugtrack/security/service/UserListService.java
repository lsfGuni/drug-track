package com.example.drugtrack.security.service;

import com.example.drugtrack.security.dto.UserListDTO;
import com.example.drugtrack.security.entity.User;
import com.example.drugtrack.security.repository.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserListService {

    @PersistenceContext
    private EntityManager entityManager;

    private final UserRepository userRepository;

    public UserListService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserListDTO> getAllUsers() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX");

        return userRepository.findAll().stream()
                .map(user -> new UserListDTO(
                        user.getSeq(),
                        user.getId(),
                        user.getUsername(),
                        user.getRole(),
                        user.getCompanyType(),
                        user.getCompanyName(),
                        user.getCompanyRegNumber(),
                        user.getPhoneNumber(),
                        user.getEmail(),
                        user.getActive(),
                        user.getRegDate() != null ? LocalDateTime.parse(user.getRegDate(), formatter) : null // Convert String to LocalDateTime
                ))
                .collect(Collectors.toList());
    }

    // New paginated method to fetch a page of UserListDTOs
    public Page<UserListDTO> getPaginatedUsers(Pageable pageable) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return userRepository.findAll(pageable)
                .map(user -> {
                    try {
                        return new UserListDTO(
                                user.getSeq(),
                                user.getId(),
                                user.getUsername(),
                                user.getRole(),
                                user.getCompanyType(),
                                user.getCompanyName(),
                                user.getCompanyRegNumber(),
                                user.getPhoneNumber(),
                                user.getEmail(),
                                user.getActive(),
                                user.getRegDate() != null ? LocalDateTime.parse(user.getRegDate(), formatter) : null
                        );
                    } catch (Exception e) {
                        // Log the error for better debugging
                        System.err.println("Error mapping user: " + e.getMessage());
                        return null;  // Handle the mapping error
                    }
                });
    }



    //유저 상세정보 조회
    public Optional<User> getUserBySeq(Long seq) {
        System.out.println("Entering getUserBySeq with seq: " + seq);
        return userRepository.findBySeq(seq); // Call the repository method to find user by sequence number
    }


    //페이징
    public Page<User> findAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    public List<User> searchUsers(String companyRegNumber, String id, String companyName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = cb.createQuery(User.class);
        Root<User> user = query.from(User.class);

        List<Predicate> predicates = new ArrayList<>();

        if (companyRegNumber != null && !companyRegNumber.isEmpty()) {
            predicates.add(cb.equal(user.get("companyRegNumber"), companyRegNumber));
        }

        if (id != null && !id.isEmpty()) {
            predicates.add(cb.equal(user.get("id"), id));
        }

        if (companyName != null && !companyName.isEmpty()) {
            predicates.add(cb.like(user.get("companyName"), "%" + companyName + "%"));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
