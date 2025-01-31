package web.springboot_pp.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import web.springboot_pp.model.User;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private List<User> usersList = new ArrayList<>();

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> getAllUsers() {
        return entityManager.createQuery("from User", User.class).getResultList();
    }

    @PostMapping
    @Transactional
    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @PostMapping
    @Override
    public void editUser(User user) {
        entityManager.merge(user);
    }

    @PostMapping
    @Override
    public void deleteUser(Long id) {
        User u = entityManager.find(User.class, id);
        if (u != null) {
            entityManager.remove(u);
        } else {
            throw new EntityNotFoundException("User with id " + id + " is not found");
        }
    }

    @Override
    public User getUserById(Long id) {
        return entityManager.find(User.class, id);
    }
}
