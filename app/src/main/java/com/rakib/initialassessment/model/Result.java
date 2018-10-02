package com.rakib.initialassessment.model;

public class Result {
    private long id;
    private String assessmentDate;
    private int vocalImitation;
    private int matching;
    private int labeling;
    private int receptiveByFFC;
    private int conversationalSkills;
    private int lettersNumbers;
    private long studentID;
    private int assessmentNo;

    public Result(){}

    public Result(long id, String assessmentDate, int vocalImitation, int matching, int labeling, int receptiveByFFC, int conversationalSkills, int lettersNumbers, long studentID, int assessmentNo)
    {
        this.id = id;
        this.assessmentDate = assessmentDate;
        this.vocalImitation = vocalImitation;
        this.matching = matching;
        this.labeling = labeling;
        this.receptiveByFFC = receptiveByFFC;
        this.conversationalSkills = conversationalSkills;
        this.lettersNumbers = lettersNumbers;
        this.studentID = studentID;
        this.assessmentNo = assessmentNo;
    }

    public long getId() {
        return id;
    }

    public String getAssessmentDate() {
        return assessmentDate;
    }

    public int getVocalImitation() {
        return vocalImitation;
    }

    public int getMatching() {
        return matching;
    }

    public int getLabeling() {
        return labeling;
    }

    public int getReceptiveByFFC() {
        return receptiveByFFC;
    }

    public int getConversationalSkills() {
        return conversationalSkills;
    }

    public int getLettersNumbers() {
        return lettersNumbers;
    }

    public long getStudentID() {
        return studentID;
    }

    public int getAssessmentNo() {
        return assessmentNo;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setAssessmentDate(String assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public void setVocalImitation(int vocalImitation) {
        this.vocalImitation = vocalImitation;
    }

    public void setMatching(int matching) {
        this.matching = matching;
    }

    public void setLabeling(int labeling) {
        this.labeling = labeling;
    }

    public void setReceptiveByFFC(int receptiveByFFC) {
        this.receptiveByFFC = receptiveByFFC;
    }

    public void setConversationalSkills(int conversationalSkills) {
        this.conversationalSkills = conversationalSkills;
    }

    public void setLettersNumbers(int lettersNumbers) {
        this.lettersNumbers = lettersNumbers;
    }

    public void setStudentID(long studentID) {
        this.studentID = studentID;
    }

    public void setAssessmentNo(int assessmentNo) {
        this.assessmentNo = assessmentNo;
    }
}
