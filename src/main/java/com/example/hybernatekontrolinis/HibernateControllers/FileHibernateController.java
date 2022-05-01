package com.example.hybernatekontrolinis.HibernateControllers;

import DataStructures.Course;
import DataStructures.File;
import DataStructures.Folder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

public class FileHibernateController {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager getEntityManager()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("kontrolinis");
        return entityManagerFactory.createEntityManager();
    }
    public void create(File file)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(file);
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
    public void update(File file) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            file = entityManager.merge(file);
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
            File file = null;
            try {
                file = entityManager.getReference(File.class, id);
                file.getId();
            } catch (Exception e) {
                System.out.println("File with given id doesn't exist");
            }
            entityManager.remove(file);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public File getFileById(int id) {
        EntityManager entityManager = null;
        File file = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            file = entityManager.getReference(File.class, id);
            file.getId();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("File with given id doesn't exist");
        }
        return file;
    }
    public List<File> getAllFilesListFromDataBase(){
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(File.class));
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
    public List<File> getAllSubFilesList(int folderId)
    {
        List<File> allFilesFromDataBase;
        allFilesFromDataBase = getAllFilesListFromDataBase();
        if(allFilesFromDataBase.size()!=0)
        {
            for(int i=allFilesFromDataBase.size()-1;i>=0;i--)
            {
                if(allFilesFromDataBase.get(i).getFolderId()!=folderId)
                {
                    allFilesFromDataBase.remove(i);
                }
            }
        }
        return allFilesFromDataBase;
    }
}
