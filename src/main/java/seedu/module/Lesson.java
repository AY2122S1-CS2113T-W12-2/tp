package seedu.module;

public class Lesson {
    private String classNo;
    private String startTime;
    private String endTime;
    //private int[] weeks; //weeks currently not handled because special term has weeks:
    // {start,end} instead of weeks: [] which breaks the program
    private String venue;
    private String day;
    private String lessonType; //placeholder
    private int size;

    public Lesson(String classNo, String startTime, String endTime, String venue,
                  String lessonType, String day) {
        this.classNo = classNo;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.lessonType = lessonType;
        this.day = day;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getClassNo() {
        return classNo;
    }

    public String getDay() {
        return day;
    }

    public String getLessonType() {
        return lessonType;
    }

    public String getVenue() {
        return venue;
    }
}
