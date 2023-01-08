package ru.rsatu.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

//@Entity()
//@Table(name = "contract")
//public class Contract extends PanacheEntityBase {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO, generator = "contract_gen")
//    private Long contractId;
//
//    @Column(name = "user_id")
//    public String userID;
//
//    @Column(name = "username")
//    public String username;
//
//    @Column(name = "enrollment_date")
//    public Date enrollmentDate = new Date();
//
//    @Column(name = "is_complete")
//    public Boolean isComplete;
//
//    @ManyToOne
//    @JoinColumn(name="studyProgramId")
//    @JsonIgnore
//    public StudyProgram studyProgram;
//
//    @OneToMany(mappedBy="contract",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    public Set<TestTry> testTries = new HashSet<>();
//
//    public Contract() {
//    }
//
//    public Contract(String userID, String username, StudyProgram studyProgram, boolean isComplete) {
//        this.userID = userID;
//        this.username = username;
//        this.studyProgram = studyProgram;
//        this.isComplete = isComplete;
//    }
//
//    public Long getContractId() {
//        return contractId;
//    }
//
//    public void setContractId(Long contractId) {
//        this.contractId = contractId;
//    }
//
//    public String getUserID() {
//        return userID;
//    }
//
//    public void setUserID(String userID) {
//        this.userID = userID;
//    }
//
//    public Date getEnrollmentDate() {
//        return enrollmentDate;
//    }
//
//    public void setEnrollmentDate(Date enrollmentDate) {
//        this.enrollmentDate = enrollmentDate;
//    }
//
//    public Boolean getComplete() {
//        return isComplete;
//    }
//
//    public void setComplete(Boolean complete) {
//        isComplete = complete;
//    }
//
//    public StudyProgram getStudyProgram() {
//        return studyProgram;
//    }
//
//    public void setStudyProgram(StudyProgram studyProgram) {
//        this.studyProgram = studyProgram;
//    }
//
//    public Set<TestTry> getTestTries() {
//        return testTries;
//    }
//
//    public void setTestTries(Set<TestTry> testTries) {
//        this.testTries = testTries;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//}
