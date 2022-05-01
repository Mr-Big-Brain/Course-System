package com.example.hybernatekontrolinis.HibernateControllers;

import DataStructures.Course;
import DataStructures.File;
import DataStructures.User;
import com.google.gson.Gson;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserHibernateController {
    private EntityManagerFactory entityManagerFactory;
    public UserHibernateController() //removed void
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("kontrolinis");
    }
    private EntityManager getEntityManager()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("kontrolinis");
        return entityManagerFactory.createEntityManager();
    }
    public void create(User user)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(user);
            entityManager.getTransaction().commit();
        }
        catch (Exception e)
        {
            System.out.println("Vartotojas nesukurtas, klaida");
            e.printStackTrace();
        }
        finally {
            if (entityManager!=null)
            {
                entityManager.close();
            }
        }
    }
    public void update(User user) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            user = entityManager.merge(user);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public List<User> getUserListFromDatabase(){
        EntityManager  entityManager = getEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(User.class));
            Query q = entityManager.createQuery(query);

            return q.getResultList();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (entityManager!=null)
            {
                entityManager.close();
            }
        }
        return null;
    }
    public User getUserById(int id) {
        EntityManager entityManager = null;
        User user = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            user = entityManager.getReference(User.class, id);
            user.getId();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("User with given id doesn't exist");
        }
        return user;
    }
    public boolean checkIfUserExist(String login, String password)
    {
        List<User> allUsers;
        allUsers = getUserListFromDatabase();
        for(int i=0;i<allUsers.size();i++)
        {
            if(Objects.equals(allUsers.get(i).getLogin(), login) && Objects.equals(allUsers.get(i).getPassword(), password))
            {
                return true;
            }
        }
        return false;
    }
    public List<User> getUsersNotModerators(Course course)
    {
        List<User> allUsersFromDataBase;
        allUsersFromDataBase = getUserListFromDatabase();
        boolean contains = false;
        if(allUsersFromDataBase.size()!=0)
        {
            for(int i=allUsersFromDataBase.size()-1;i>=0;i--)
            {
                contains = false;
                for(int j=0;j<allUsersFromDataBase.get(i).getModeratedCourses().size();j++)
                {
                    if(allUsersFromDataBase.get(i).getModeratedCourses().get(j).getId()==course.getId())
                    {
                        contains = true;
                        break;
                    }
                }
                if(contains == true)
                {
                    allUsersFromDataBase.remove(i);
                }
                else if(allUsersFromDataBase.get(i).getId() == course.getAdminId().getId())
                {
                    allUsersFromDataBase.remove(i);
                }
            }
        }
        return allUsersFromDataBase;
    }
    public List<User> getUsersNotStudents(Course course)
    {
        List<User> allUsersFromDataBase;
        allUsersFromDataBase = getUserListFromDatabase();
        boolean contains = false;
        if(allUsersFromDataBase.size()!=0)
        {
            for(int i=allUsersFromDataBase.size()-1;i>=0;i--)
            {
                contains = false;
                for(int j=0;j<allUsersFromDataBase.get(i).getStudentCourses().size();j++)
                {
                    if(allUsersFromDataBase.get(i).getStudentCourses().get(j).getId()==course.getId())
                    {
                        contains = true;
                        break;
                    }
                }
                if(contains == true)
                {
                    allUsersFromDataBase.remove(i);
                }
                else if(allUsersFromDataBase.get(i).getId() == course.getAdminId().getId())
                {
                    allUsersFromDataBase.remove(i);
                }
            }
        }
        return allUsersFromDataBase;
    }
    public List<User> getModeratorsNotUsers(Course course)
    {
        List<User> allUsersFromDataBase;
        allUsersFromDataBase = getUserListFromDatabase();
        boolean contains = false;
        if(allUsersFromDataBase.size()!=0)
        {
            for(int i=allUsersFromDataBase.size()-1;i>=0;i--)
            {
                contains = false;
                for(int j=0;j<allUsersFromDataBase.get(i).getModeratedCourses().size();j++)
                {
                    if(allUsersFromDataBase.get(i).getModeratedCourses().get(j).getId()==course.getId())
                    {
                        contains = true;
                        break;
                    }
                }
                if(contains == false)
                {
                    allUsersFromDataBase.remove(i);
                }
                else if(allUsersFromDataBase.get(i).getId() == course.getAdminId().getId())
                {
                    allUsersFromDataBase.remove(i);
                }
            }
        }
        return allUsersFromDataBase;
    }
    public List<User> getStudentsNotUsers(Course course)
    {
        List<User> allUsersFromDataBase;
        allUsersFromDataBase = getUserListFromDatabase();
        boolean contains = false;
        if(allUsersFromDataBase.size()!=0)
        {
            for(int i=allUsersFromDataBase.size()-1;i>=0;i--)
            {
                contains = false;
                for(int j=0;j<allUsersFromDataBase.get(i).getStudentCourses().size();j++)
                {
                    if(allUsersFromDataBase.get(i).getStudentCourses().get(j).getId()==course.getId())
                    {
                        contains = true;
                        break;
                    }
                }
                if(contains == false)
                {
                    allUsersFromDataBase.remove(i);
                }
                else if(allUsersFromDataBase.get(i).getId() == course.getAdminId().getId())
                {
                    allUsersFromDataBase.remove(i);
                }
            }
        }
        return allUsersFromDataBase;
    }
    public int getUserIdByLoginAndPassword(String login, String password)
    {
        List<User> allUsers;
        allUsers = getUserListFromDatabase();
        for(int i=0;i<allUsers.size();i++)
        {
            if(Objects.equals(allUsers.get(i).getLogin(), login) && Objects.equals(allUsers.get(i).getPassword(), password))
            {
                return allUsers.get(i).getId();
            }
        }
        return 0;
    }
}
