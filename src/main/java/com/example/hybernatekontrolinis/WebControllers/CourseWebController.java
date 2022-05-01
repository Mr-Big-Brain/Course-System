package com.example.hybernatekontrolinis.WebControllers;

import DataStructures.Course;
import DataStructures.File;
import DataStructures.User;
import com.example.hybernatekontrolinis.HibernateControllers.CourseHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.FileHibernateController;
import com.example.hybernatekontrolinis.HibernateControllers.UserHibernateController;
import com.google.gson.Gson;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class CourseWebController {
    @RequestMapping(value = "/course/all", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getAllCoursesFromDataBase() {
        CourseHibernateController courseHibernateController = new CourseHibernateController();
        List<Course> allCoursesFromDataBase = courseHibernateController.getAllCourseListFromDataBase();
        Gson gson = new Gson();
        String json = "";
        for(int i=0;i<allCoursesFromDataBase.size();i++)
        {
            allCoursesFromDataBase.get(i).setAdminId(null);
            allCoursesFromDataBase.get(i).setModerators(null);
            allCoursesFromDataBase.get(i).setStudents(null);
            json+= gson.toJson(allCoursesFromDataBase.get(i));
        }
        return json;
    }
    @RequestMapping(value = "/course/userCourses/{id}", method = RequestMethod.GET)
    @ResponseStatus(value = HttpStatus.OK)
    @ResponseBody
    public String getCoursesForStudent(@PathVariable("id") int id) {

        ArrayList<Course> allStudentCourses = studentCourses(id);
        Gson gson = new Gson();
        String json = "[";
        for(int i=0;i<allStudentCourses.size();i++)
        {
            if(i!=0)
            {
                json+=",";
            }
            allStudentCourses.get(i).setAdminId(null);
            allStudentCourses.get(i).setModerators(null);
            allStudentCourses.get(i).setStudents(null);
            json+= gson.toJson(allStudentCourses.get(i));
        }
        json+="]";
        return json;
    }

    public ArrayList<Course> studentCourses(int studentId)
    {
        CourseHibernateController courseHibernateController = new CourseHibernateController();
        UserHibernateController userHibernateController = new UserHibernateController();
        List<User> allUsersFromDataBase = userHibernateController.getUserListFromDatabase();
        ArrayList<Course> allThisStudentCourses = new ArrayList<>();
        for(int i=allUsersFromDataBase.size()-1;i>=0;i--)
        {
            if(allUsersFromDataBase.get(i).getId()==studentId)
            {
                for(int j=0;j<allUsersFromDataBase.get(i).getStudentCourses().size();j++)
                {
                    allThisStudentCourses.add(allUsersFromDataBase.get(i).getStudentCourses().get(j));
                }
                break;
            }
        }
        return allThisStudentCourses;
    }
}
