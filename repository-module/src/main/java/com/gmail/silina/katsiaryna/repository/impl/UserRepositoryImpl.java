package com.gmail.silina.katsiaryna.repository.impl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Repository
public class UserRepositoryImpl /*extends GenericRepositoryImpl<Long, User> implements UserRepository*/ {

    /*@Override
    public User getUserByEmail(String email) {
        String hql = "SELECT u FROM User u WHERE u.email = :email";
        var query = entityManager.createQuery(hql);
        query.setParameter("email", email);
        try {
            return (User) query.getSingleResult();
        } catch (NoResultException e) {
            log.error("User with email {} doesn't exist", email);
            return null;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll(int pageNumber, int pageSize) {
        //String hql = "SELECT u FROM User u";
        String hql = "FROM User u ORDER BY u.email";
        var query = entityManager.createQuery(hql);
        query.setFirstResult(RepositoryUtil.getStartPosition(pageNumber, pageSize));
        query.setMaxResults(pageSize);
        return query.getResultList();
    }*/
}
