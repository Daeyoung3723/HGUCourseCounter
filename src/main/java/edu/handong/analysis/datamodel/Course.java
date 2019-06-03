package edu.handong.analysis.datamodel;

public class Course {
	private String studentld;
	private String yearMonthGraduated;
	private String firstMajor;
	private String secondMajor;
	private String courseCode;
	public String getCourseCode() {
		return courseCode;
	}

	public String getCourseName() {
		return courseName;
	}

	private String courseName;
	private String courseCredit;

	private int yearTaken;
	public int getYearTaken() {
		return yearTaken;
	}

	private int semesterCourseTaken;
	
	public int getSemesterCourseTaken() {
		return semesterCourseTaken;
	}

	public Course(String line) {
		// Split the line from constructor to initialize the field
		String[] ary = line.split(",");
		studentld = ary[0].trim();
		yearMonthGraduated = ary[1].trim();
		firstMajor = ary[2].trim();
		secondMajor = ary[3].trim();
		courseCode = ary[4].trim();
		courseName = ary[5].trim();
		courseCredit = ary[6].trim();
		yearTaken = Integer.parseInt(ary[7].trim());
		semesterCourseTaken = Integer.parseInt(ary[8].trim());
	}
	
	
}
