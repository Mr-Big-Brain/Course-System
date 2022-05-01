package com.example.hybernatekontrolinis.WebControllers;

import DataStructures.Folder;
import DataStructures.User;
import com.example.hybernatekontrolinis.HibernateControllers.FolderHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.UserHibernateController;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Controller
public class FolderWebController {
    private FolderHibernateController folderHibernateController = new FolderHibernateController();
    @RequestMapping(value = "/folder/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllFoldersFromDataBase()
    {
        FolderHibernateController folderHibernateController = new FolderHibernateController();
        List<Folder> allFoldersFromDataBase = folderHibernateController.getAllFoldersListFromDataBase();
        Gson gson = new Gson();
        String json = "[";
        for(int i=0;i<allFoldersFromDataBase.size();i++)
        {
            if(i!=0)
            {
                json+=",";
            }
            json+= gson.toJson(allFoldersFromDataBase.get(i));
        }
        json+="]";
        return json;
    }
}
