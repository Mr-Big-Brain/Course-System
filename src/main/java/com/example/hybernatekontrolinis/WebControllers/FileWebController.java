package com.example.hybernatekontrolinis.WebControllers;

import DataStructures.File;
import DataStructures.User;
import com.example.hybernatekontrolinis.HibernateControllers.FileHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.UserHibernateController;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Controller
public class FileWebController {
    @RequestMapping(value = "/file/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllFilesFromDataBase() {
        FileHibernateController fileHibernateController = new FileHibernateController();
        List<File> allFilesFromDataBase = fileHibernateController.getAllFilesListFromDataBase();
        Gson gson = new Gson();
        String json = "[";
        for(int i=0;i<allFilesFromDataBase.size();i++)
        {
            if(i!=0)
            {
                json+=",";
            }
            json+= gson.toJson(allFilesFromDataBase.get(i));
        }
        json+="]";
        return json;
    }
}
