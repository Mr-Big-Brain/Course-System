package DataStructures;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private String description;
    @ManyToOne
    private User adminId;
    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> students;
    @ManyToMany (mappedBy = "moderatedCourses")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<User> moderators;
    @Column
    private int mainFolderId;

    public Course() {
    }

    public Course(String title, String description, User adminId, int mainFolderId) {
        this.title = title;
        this.description = description;
        this.adminId = adminId;
        this.mainFolderId = mainFolderId;
    }
    public void addStudentToCourse(User user)
    {
        this.students.add(user);
    }
    public void removeStudentFromCourse(User user)
    {
        for(int i=0;i<getStudents().size();i++)
        {
            if(this.students.get(i).getId() == user.getId())
            {
                this.students.remove(i);
            }
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public User getAdminId() {
        return adminId;
    }

    public void setAdminId(User adminId) {
        this.adminId = adminId;
    }

    public List<User> getStudents() {
        return students;
    }

    public void setStudents(List<User> students) {
        this.students = students;
    }

    public List<User> getModerators() {
        return moderators;
    }

    public void setModerators(List<User> moderators) {
        this.moderators = moderators;
    }

    public int getMainFolderId() {
        return mainFolderId;
    }

    public void setMainFolderId(int mainFolderId) {
        this.mainFolderId = mainFolderId;
    }
}
