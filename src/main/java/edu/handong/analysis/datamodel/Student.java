package edu.handong.analysis.datamodel;

import java.util.ArrayList;
import java.util.HashMap;

public class Student {
	private String studentld;
	public String getStudentld() {
		return studentld;
	}

	private ArrayList<Course> coursesTaken = new ArrayList<Course>(); // List of courses student has taken
	public ArrayList<Course> getCoursesTaken() {
		return coursesTaken;
	}

	private HashMap<String, Integer> semestersByYearAndSemester = new HashMap<String, Integer>();
											// key: Year-Semester
											// e.g., 2003-1
	
	public HashMap<String, Integer> getSemestersByYearAndSemester() {
		int semesters = 1;
		semestersByYearAndSemester = new HashMap<String, Integer>();
		String yearTaken_semesterCourseTaken1 = "2018_1";
		String yearTaken_semesterCourseTaken2 = "2018_1";
		for(Course course : coursesTaken) {
			yearTaken_semesterCourseTaken1 = yearTaken_semesterCourseTaken2;
			yearTaken_semesterCourseTaken2 = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			if(!(yearTaken_semesterCourseTaken2.equals(yearTaken_semesterCourseTaken1))) {
				semesters++;
			}
			semestersByYearAndSemester.put(yearTaken_semesterCourseTaken2, semesters);
		}
		
		return semestersByYearAndSemester;
	}

	public Student(String studentld) {
		// constructor
		this.studentld = studentld;
	}

	public void addCourse(Course newRecord) {
		coursesTaken.add(newRecord);
	}
	
	public int getNumCourseInNthSementer(int semester) {
		int count = 0;
		String yearTaken_semesterCourseTaken = null;
		for(Course course : coursesTaken) {
			yearTaken_semesterCourseTaken = course.getYearTaken() + "-" + course.getSemesterCourseTaken();
			
			if(semestersByYearAndSemester.get(yearTaken_semesterCourseTaken) == semester) {
				count++;
			}
		}
		
		return count;
	}



}
