package com.example.hybernatekontrolinis.WebControllers;

import DataStructures.User;
import com.example.hybernatekontrolinis.HibernateControllers.UserHibernateController;
import com.google.gson.GsonBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.google.gson.Gson;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserWebController {

    private UserHibernateController userHibernateController = new UserHibernateController();
    @RequestMapping(value = "/user/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllUsersFromDataBase()
    {
        UserHibernateController userHibernateController = new UserHibernateController();
        List<User> allUsersFromDataBase = userHibernateController.getUserListFromDatabase();
        Gson gson = new Gson();
        String json = "";
        for(int i=0;i<allUsersFromDataBase.size();i++)
        {
            allUsersFromDataBase.get(i).setStudentCourses(null);
            allUsersFromDataBase.get(i).setModeratedCourses(null);
            json+= gson.toJson(allUsersFromDataBase.get(i));
        }
        return json;
    }
    @RequestMapping(value = "/user/findByLoginAndPassword/{login}/{password}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getUserByLoginAndPassword(@PathVariable("login") String login, @PathVariable("password") String password)
    {
        UserHibernateController userHibernateController = new UserHibernateController();
        if(userHibernateController.checkIfUserExist(login,password))
        {
            User requestedUser = (userHibernateController.getUserById(userHibernateController.getUserIdByLoginAndPassword(login,password)));
            requestedUser.setStudentCourses(null);
            requestedUser.setModeratedCourses(null);
            String json = "[{\"id\":"+requestedUser.getId()+",\"login\":\""+requestedUser.getLogin()+"\",\"name\":\""+requestedUser.getName()+"\",\"surname\":\""+requestedUser.getSurname()+"\",\"password\":\""+requestedUser.getPassword()+"\",\"userType\":\""+requestedUser.getUserType()+"\",\"companyName\":\""+requestedUser.getCompanyName()+"\"}]";
            json+="";
            System.out.println(json);
            return json;
        }
        else return "No such user";

    }
}