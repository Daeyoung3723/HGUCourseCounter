package edu.handong.analysis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import edu.handong.analysis.datamodel.Course;
import edu.handong.analysis.datamodel.Student;
import edu.handong.analysise.utils.NotEnoughArgumentException;
import edu.handong.analysise.utils.Utils;

public class HGUCoursePatternAnalyzer {

	private HashMap<String,Student> students;
	
	/**
	 * This method runs our analysis logic to save the number courses taken by each student per semester in a result file.
	 * Run method must not be changed!!
	 * @param args
	 */
	public void run(String[] args) {
		
		try {
			// when there are not enough arguments from CLI, it throws the NotEnoughArgmentException which must be defined by you.
			if(args.length<5)
				throw new NotEnoughArgumentException();
		} catch (NotEnoughArgumentException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}
		
		String dataPath = args[0]; // csv file to be analyzed
		String resultPath = args[1]; // the file path where the results are saved.
		List<String[]> data = new ArrayList<String[]>();
		 
        CSVRead read = new CSVRead();
        data = read.readCsv(dataPath);
        ArrayList<String> lines = new ArrayList<String>();



        for(String[] str : data) {
        	int yearTaken = Integer.parseInt(str[7].trim());

        	if(yearTaken >= Integer.parseInt(args[2]) && yearTaken <= Integer.parseInt(args[3])) {
        		lines.add(str[0] + "," + str[1] + "," + str[2] + "," + str[3] + "," 
        				+ str[4] + "," + str[5] + "," + str[6] + "," + str[7] + "," + str[8]);
        	}
        }

        students = loadStudentCourseRecords(lines);

        // To sort HashMap entries by key values so that we can save the results by student ids in ascending order.
        Map<String, Student> sortedStudents = new TreeMap<String,Student>(students);
        if(args[4].equals("1")) {
        	// Generate result lines to be saved.
        	ArrayList<String> linesToBeSaved = countNumberOfCoursesTakenInEachSemester(sortedStudents);

        	// Write a file (named like the value of resultPath) with linesTobeSaved.
        	Utils.writeAFile(linesToBeSaved, resultPath);
        } else {
        	HashMap<String, Integer> totalStudents = new HashMap<String, Integer>();
    		HashMap<String, Integer> studentsTaken = new HashMap<String, Integer>();
    		String courseName = new String();
    		
    		ArrayList<String> yearTaken_SemesterCourseTaken = findYearTaken_SemesterCourseTaken(sortedStudents);
    		totalStudents = fineTotalStudents(yearTaken_SemesterCourseTaken, sortedStudents);
    		studentsTaken = findStudentsTaken(yearTaken_SemesterCourseTaken, args[5], sortedStudents);
    		courseName = findCourseName(args[5], sortedStudents);
    		
    		Collections.sort(yearTaken_SemesterCourseTaken);
    		
    		ArrayList<String> linesToBeSaved = analyzerTwo(yearTaken_SemesterCourseTaken, totalStudents, studentsTaken, courseName, args[5]);
    		
    		Utils.writeAFile(linesToBeSaved, resultPath, "analyse2");
        }
        
        
	}
	
	private ArrayList<String> analyzerTwo(ArrayList<String> yearTaken_SemesterCourseTaken,
			HashMap<String, Integer> totalStudents, HashMap<String, Integer> studentsTaken, String courseName,
			String courseCode) {
		ArrayList<String> lines = new ArrayList<String>();
		
		for(String str : yearTaken_SemesterCourseTaken) {
			float x = (float) (studentsTaken.get(str)*100.0/totalStudents.get(str));
			String no = String.format("%.1f", x);
			lines.add(str.split("_")[0] + "," + str.split("_")[1] + "," + courseCode + "," + courseName + "," + totalStudents.get(str) + 
					"," + studentsTaken.get(str) + "," + no + "%");
		}
		
		
		return lines;
	}

	private String findCourseName(String coursecode, Map<String, Student> sortedStudents) {
		String courseName = null;
		
		for(int i = 1; i < sortedStudents.size()+1; i++) {
			Student student = null;
			if(i < 10) {
				student = sortedStudents.get("000" + i);
			} else if(i < 100) {
				student = sortedStudents.get("00" + i);
			} else if(i < 1000) {
				student = sortedStudents.get("0" + i);
			}
			if(student == null) {
				continue;
			}
			
			for(Course course : student.getCoursesTaken()) {
				if(course.getCourseCode().equals(coursecode)) {
					courseName = course.getCourseName();
					return courseName;
				}
				
				
			}
			
		}
		return courseName;
		
	}

	private HashMap<String, Integer> findStudentsTaken(ArrayList<String> yearTaken_SemesterCourseTaken, String coursecode, Map<String, Student> sortedStudents) {
		HashMap<String, Integer> totalStudents = new HashMap<String, Integer>();
		
		
		for(String str : yearTaken_SemesterCourseTaken) {
			int stack = 0;
			for(int i = 1; i < sortedStudents.size()+1; i++) {
				
				Student student = null;
				if(i < 10) {
					student = sortedStudents.get("000" + i);
				} else if(i < 100) {
					student = sortedStudents.get("00" + i);
				} else if(i < 1000) {
					student = sortedStudents.get("0" + i);
				}
				if(student == null) {
					continue;
				}
				for(Course course : student.getCoursesTaken()) {
					String yearTaken_semesterCourseTaken = course.getYearTaken() + "_" + course.getSemesterCourseTaken();
					if(yearTaken_semesterCourseTaken.equals(str) && course.getCourseCode().equals(coursecode)) {
						stack++;
						break;
					}
				}
				
				
			}
			totalStudents.put(str, stack);
						
		}
		
		
		
		return totalStudents;
	}

	private HashMap<String, Integer> fineTotalStudents(ArrayList<String> yearTaken_SemesterCourseTaken, Map<String, Student> sortedStudents) {
		HashMap<String, Integer> totalStudents = new HashMap<String, Integer>();
		
		
		for(String str : yearTaken_SemesterCourseTaken) {
			int stack = 0;
			for(int i = 1; i < sortedStudents.size()+1; i++) {
				Student student = null;
				
				if(i < 10) {
					student = sortedStudents.get("000" + i);
				} else if(i < 100) {
					student = sortedStudents.get("00" + i);
				} else if(i < 1000) {
					student = sortedStudents.get("0" + i);
				}
				if(student == null) {
					continue;
				}
				
				for(Course course : student.getCoursesTaken()) {
					String yearTaken_semesterCourseTaken = course.getYearTaken() + "_" + course.getSemesterCourseTaken();
					if(yearTaken_semesterCourseTaken.equals(str)) {
						stack++;
						break;
					}			
					
				}				
			}
			totalStudents.put(str, stack);
			
		}
		
		
		
		return totalStudents;
	}

	private ArrayList<String> findYearTaken_SemesterCourseTaken(Map<String, Student> sortedStudents) {
		ArrayList<String> lines = new ArrayList<String>();
		
		for(int i = 1; i < sortedStudents.size()+1; i++) {
			Student student = null;
			if(i < 10) {
				student = sortedStudents.get("000" + i);
			} else if(i < 100) {
				student = sortedStudents.get("00" + i);
			} else if(i < 1000) {
				student = sortedStudents.get("0" + i);
			}
			if(student == null) {
				continue;
			}
			String yearTaken_semesterCourseTaken1 = "2018_1";
			String yearTaken_semesterCourseTaken2 = "2018_1";
			
			for(Course course : student.getCoursesTaken()) {
				yearTaken_semesterCourseTaken1 = yearTaken_semesterCourseTaken2;
				yearTaken_semesterCourseTaken2 = course.getYearTaken() + "_" + course.getSemesterCourseTaken();
				
				if(!(yearTaken_semesterCourseTaken2.equals(yearTaken_semesterCourseTaken1))) {
					if(!(isExist(lines, yearTaken_semesterCourseTaken2))){
						lines.add(yearTaken_semesterCourseTaken2);
					}
				}
				
			}
			
		}
		
		return lines;
	}

	private boolean isExist(ArrayList<String> lines, String yearTaken_semesterCourseTaken2) {
		boolean isExist = false;
		for(String str : lines) {
			if(str.equals(yearTaken_semesterCourseTaken2)) {
				isExist = true;
				break;
			}
		}
		return isExist;
	}

	/**
	 * This method create HashMap<String,Student> from the data csv file. Key is a student id and the corresponding object is an instance of Student.
	 * The Student instance have all the Course instances taken by the student.
	 * @param lines
	 * @return
	 */
	private HashMap<String,Student> loadStudentCourseRecords(ArrayList<String> lines) {
		HashMap<String, Student> csv = new HashMap<String, Student>();
		String line = lines.remove(0);
		String[] ary = line.split(",");
		String beforeId = ary[0].trim();
		Student student = new Student(ary[0].trim());
		
		while(true) {
			while(beforeId.equals(ary[0].trim())) {
				student.addCourse(new Course(line));
				csv.put(ary[0].trim(), student);
				
				if(!(lines.isEmpty())) {
					line = lines.remove(0);
				} else {
					break;
				}
				
				beforeId = ary[0].trim();
				ary = line.split(",");
			}
			if(lines.isEmpty()) {
				break;
			}
			
			student = new Student(ary[0].trim());
			student.addCourse(new Course(line));
			csv.put(ary[0].trim(), student);
			
			if(!(lines.isEmpty())) {
				line = lines.remove(0);
			} else {
				break;
			}
			
			beforeId = ary[0].trim();
			ary = line.split(",");
			
			
		}
		return csv; // do not forget to return a proper variable.
	}

	/**
	 * This method generate the number of courses taken by a student in each semester. The result file look like this:
	 * StudentID, TotalNumberOfSemestersRegistered, Semester, NumCoursesTakenInTheSemester
	 * 0001,14,1,9
     * 0001,14,2,8
	 * ....
	 * 
	 * 0001,14,1,9 => this means, 0001 student registered 14 semeters in total. In the first semester (1), the student took 9 courses.
	 * 
	 * 
	 * @param sortedStudents
	 * @return
	 */
	private ArrayList<String> countNumberOfCoursesTakenInEachSemester(Map<String, Student> sortedStudents) {
		ArrayList<String> lines = new ArrayList<String>();
		
		for(int i = 1; i < sortedStudents.size()+1; i++) {
			Student student = null;
			if(i < 10) {
				student = sortedStudents.get("000" + i);
			} else if(i < 100) {
				student = sortedStudents.get("00" + i);
			} else if(i < 1000) {
				student = sortedStudents.get("0" + i);
			}
			if(student == null) {
				continue;
			}
			for(int k = 0; k < student.getSemestersByYearAndSemester().size(); k++) {
				lines.add(student.getStudentld() + "," + student.getSemestersByYearAndSemester().size() + "," + (k+1) + "," + student.getNumCourseInNthSementer(k+2));
			}
		}
		
		return lines; // do not forget to return a proper variable.
	}
}
