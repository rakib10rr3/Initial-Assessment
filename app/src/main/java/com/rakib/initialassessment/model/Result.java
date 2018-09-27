package com.rakib.initialassessment.model;

public class Result {
    private long id;
    private int vocalImitation;
    private int matching;
    private int labeling;
    private int receptiveByFFC;
    private int conversationalSkills;
    private int lettersNumbers;
    private long studentID;

    public Result(){}

    public Result(long id, int vocalImitation, int matching, int labeling, int receptiveByFFC, int conversationalSkills, int lettersNumbers, long studentID)
    {
        this.id = id;
        this.vocalImitation = vocalImitation;
        this.matching = matching;
        this.labeling = labeling;
        this.receptiveByFFC = receptiveByFFC;
        this.conversationalSkills = conversationalSkills;
        this.lettersNumbers = lettersNumbers;
        this.studentID = studentID;
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
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
}
