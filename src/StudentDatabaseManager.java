import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;


public class StudentDatabaseManager implements Serializable {

	private static final long serialVersionUID = 1L;
	public static final String DATABASE_FILE_NAME = "StudentDatabase.ser";
	public static final String DATABASE_FOLDER_NAME = "Student Database Files";
	private ArrayList<Student> studentList;
	
	public StudentDatabaseManager() {
		studentList = new ArrayList<Student>();
	}
	
	public void addStudent(Student s){
		studentList.add(s);
	}
	
	public Student[] getStudentsArray() {
		return studentList.toArray(new Student[1]);
	}
	
	public ArrayList<Student> getStudentList(){
		return studentList;
	}
	
	public double getAverageGrade(){
		double average = 0;
		for(Student stud: studentList) {	
			average += stud.getGrade();
		}
		average /= studentList.size();
		return average;
	}
	
	public void saveStudentList() {
		try{	
			File outDir = new File(DATABASE_FOLDER_NAME);
			if(!outDir.exists())
				outDir.mkdir();
			FileOutputStream fos = new FileOutputStream(outDir + File.separator + DATABASE_FILE_NAME );
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(this);
			oos.flush();
			oos.close();
			fos.close();
		}
		catch(Exception ioe) { ioe.printStackTrace(); }
	}
	
	public static StudentDatabaseManager loadStudentList() {
		StudentDatabaseManager studentManager = null;;
		try{	
			try {
				FileInputStream fileIn = new FileInputStream(DATABASE_FOLDER_NAME + File.separator + DATABASE_FILE_NAME);
				ObjectInputStream in = new ObjectInputStream(fileIn);
				studentManager = (StudentDatabaseManager) in.readObject();
				in.close();
				fileIn.close();
			}
			catch(Exception ex) {
				System.out.println("Database file not found");
			}
		}
		catch(Exception ioe) { ioe.printStackTrace(); }
		
		return studentManager;
	}
	
	public void printAverage(){
		for(Student stud: studentList) {	
			System.out.println(stud.getName() + " " +stud.getGrade());
		}
		double average = getAverageGrade();
		System.out.println("Average = "+average);
		JOptionPane.showMessageDialog(null, " Average = "+average);
	}

}
