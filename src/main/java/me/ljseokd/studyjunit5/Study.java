package me.ljseokd.studyjunit5;

public class Study {

    private StudyStatus status = StudyStatus.DRAFT;
    private int limit;
    public StudyStatus getStatus() {
        return status;
    }

    public int getLimit() {
        return limit;
    }

    public Study(int limit) {
        if (limit < 0){
            throw new IllegalArgumentException("limit은 0보다 커야 합니다.");
        }
        this.limit = limit;
    }

    public Study() {
    }
}
