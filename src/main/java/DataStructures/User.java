package DataStructures;

import com.google.gson.annotations.SerializedName;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String login;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String password;
    @Enumerated(EnumType.STRING)
    private UserType userType;
    @Column
    private String companyName;
    @ManyToMany (mappedBy = "students")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Course> studentCourses;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Course> moderatedCourses;

    public User() {
    }

    public User(String login, String name, String surname, String password, UserType userType, String companyName) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.userType = userType;
        this.companyName = companyName;
    }
    public User(String login, String name, String surname, String password, UserType userType) {
        this.login = login;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.userType = userType;
    }
    public void addModeratedCourse(Course course)
    {
        this.moderatedCourses.add(course);
    }
    public void addStudentCourse(Course course)
    {
        this.studentCourses.add(course);
    }
    public void removeModeratedCourse(Course course)
    {
        for(int i=0;i<getModeratedCourses().size();i++)
        {
            if(this.moderatedCourses.get(i).getId()== course.getId())
            {
                this.moderatedCourses.remove(i);
            }

        }
    }
    public void removeStudentCourse(Course course)
    {
        for(int i=0;i<getStudentCourses().size();i++)
        {
            if(this.studentCourses.get(i).getId()== course.getId())
            {
                this.studentCourses.remove(i);
            }

        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<Course> getStudentCourses() {
        return studentCourses;
    }

    public void setStudentCourses(List<Course> studentCourses) {
        this.studentCourses = studentCourses;
    }

    public List<Course> getModeratedCourses() {
        return moderatedCourses;
    }

    public void setModeratedCourses(List<Course> moderatedCourses) {
        this.moderatedCourses = moderatedCourses;
    }
}
