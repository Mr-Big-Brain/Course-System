package com.example.hybernatekontrolinis.HibernateControllers;

import DataStructures.Course;
import DataStructures.File;
import DataStructures.Folder;
import DataStructures.User;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class CourseHibernateController {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager getEntityManager()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("kontrolinis");
        return entityManagerFactory.createEntityManager();
    }
    public void create(Course course)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(entityManager.merge(course));
            entityManager.getTransaction().commit();
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
    }
    public void update(Course course) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            course = entityManager.merge(course);
            entityManager.getTransaction().commit();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public void delete(int id) {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            Course course = null;
            try {
                course = entityManager.getReference(Course.class, id);
                course.getId();
            } catch (Exception e) {
                System.out.println("Course with given id doesn't exist");
            }
            entityManager.remove(course);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public List<Course> getAllCourseListFromDataBase(){
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Course.class));
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
    public List<Course> getAdministratedByUserIdCourses(int adminId)
    {
        List<Course> administratedCourses;
        administratedCourses = getAllCourseListFromDataBase();
        if(administratedCourses.size()!=0)
        {
            for(int i=administratedCourses.size()-1;i>=0;i--)
            {
                if(administratedCourses.get(i).getAdminId().getId() != adminId)
                {
                    administratedCourses.remove(i);
                }
            }
        }
        return administratedCourses;
    }

    public Course getCourseById(int id) {
        EntityManager entityManager = null;
        Course course = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            course = entityManager.getReference(Course.class, id);
            course.getId();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Course with given id doesn't exist");
        }
        return course;
    }
}
