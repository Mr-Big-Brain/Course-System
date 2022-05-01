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

public class FolderHibernateController {
    private EntityManagerFactory entityManagerFactory;
    private EntityManager getEntityManager()
    {
        entityManagerFactory = Persistence.createEntityManagerFactory("kontrolinis");
        return entityManagerFactory.createEntityManager();
    }
    public void create(Folder folder)
    {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(folder);
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
    public void update(Folder folder) {
        EntityManager entityManager = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            folder = entityManager.merge(folder);
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
            Folder folder = null;
            try {
                folder = entityManager.getReference(Folder.class, id);
                folder.getId();
            } catch (Exception e) {
                System.out.println("Folder with given id doesn't exist");
            }
            entityManager.remove(folder);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }
    public Folder getFolderById(int id) {
        EntityManager entityManager = null;
        Folder folder = null;

        try {
            entityManager = getEntityManager();
            entityManager.getTransaction().begin();
            folder = entityManager.getReference(Folder.class, id);
            folder.getId();
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            System.out.println("Folder with given id doesn't exist");
        }
        return folder;
    }
    public List<Folder> getAllFoldersListFromDataBase(){
        EntityManager entityManager = getEntityManager();
        try {
            CriteriaQuery query = entityManager.getCriteriaBuilder().createQuery();
            query.select(query.from(Folder.class));
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
    public int getLastCreatedFolderId()
    {
        List<Folder> allFoldersFromDataBase;
        allFoldersFromDataBase = getAllFoldersListFromDataBase();
        int maxi = -1;
        for(int i=0;i<allFoldersFromDataBase.size();i++)
        {
            if(maxi<allFoldersFromDataBase.get(i).getId())
            {
                maxi = allFoldersFromDataBase.get(i).getId();
            }
        }
        return maxi;
    }
    public List<Folder> getAllSubFoldersList(int folderId)
    {
        List<Folder> allFoldersFromDataBase;
        allFoldersFromDataBase = getAllFoldersListFromDataBase();
        if(allFoldersFromDataBase.size()!=0)
        {
            for(int i=allFoldersFromDataBase.size()-1;i>=0;i--)
            {
                if(allFoldersFromDataBase.get(i).getParentFolderId()!=folderId)
                {
                    allFoldersFromDataBase.remove(i);
                }
            }
        }
        return allFoldersFromDataBase;
    }
}
