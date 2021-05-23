
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;


public class ApplicationFrame extends JFrame {
	
	private JButton addStudentButton, printStudentsButton,loadStudentsButton;
	private JLabel nameLbl, gradeLbl;
	private JTextField nameTxt, gradeTxt;
	private StudentDatabaseManager studentManager;
	private JPanel masterPanel;
	private JPanel buttonsPanel;
	private JPanel listPanel;
	private JList studentList;
	
	public ApplicationFrame() {
		super("Student Database Manager");
		createComponents();
		this.pack();
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	private void createComponents() {
		studentManager = new StudentDatabaseManager();
		nameLbl = new JLabel("Name:");
		gradeLbl = new JLabel("Grade:");
		nameTxt = new JTextField(15);
		gradeTxt = new JTextField(3);
		studentList = new JList();
		loadStudentsButton = new JButton("Load Students");
		addStudentButton = new JButton("Add Student");
		printStudentsButton = new JButton("Print Average");
		MyButtonListener listener = new MyButtonListener();
		loadStudentsButton.addActionListener(listener);
		addStudentButton.addActionListener(listener);
		printStudentsButton.addActionListener(listener);
		masterPanel = new JPanel();
		buttonsPanel = new JPanel();
		listPanel = new JPanel();
		buttonsPanel.add(nameLbl); buttonsPanel.add(nameTxt);
		buttonsPanel.add(gradeLbl); buttonsPanel.add(gradeTxt);
		buttonsPanel.add(loadStudentsButton);	buttonsPanel.add(addStudentButton);	buttonsPanel.add(printStudentsButton);
		
		loadStudentsButton.doClick();
		
		studentList.setBounds(10, 30, 80, 100);
		studentList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		studentList.addKeyListener(new StudentListKeyListener());
	    JScrollPane scrollPane = new JScrollPane(studentList);
	    
		listPanel.add(scrollPane);
		masterPanel.add(listPanel, BorderLayout.LINE_START);
		masterPanel.add(buttonsPanel, BorderLayout.LINE_END);
		this.setContentPane(masterPanel);
		
	}
	
	private void clearFields(){
		nameTxt.setText("");
		gradeTxt.setText("");
	}
	
	class MyButtonListener implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == loadStudentsButton) {
				studentManager = StudentDatabaseManager.loadStudentList();
				if(studentManager != null){
					studentList.setListData(studentManager.getStudentsArray());
				}
				else{
					studentManager = new StudentDatabaseManager();
				}
				repaint();
			}
			else if(e.getSource() == addStudentButton){
				int gradeInt = Integer.parseInt(gradeTxt.getText());
				studentManager.addStudent(new Student(nameTxt.getText(), gradeInt));
				studentManager.saveStudentList();
				studentList.setListData(studentManager.getStudentsArray());
				clearFields();
			}
			else if (e.getSource() == printStudentsButton){
				studentManager.printAverage();
			}
		}
	}
	
	class StudentListKeyListener implements KeyListener{

		private static final int DELETE_KEY_CODE = 127;

		public void keyPressed(KeyEvent e) {
			Student student = (Student)studentList.getSelectedValue();
			int selectedIndex = studentList.getSelectedIndex();
			if(e.getKeyCode() == DELETE_KEY_CODE && selectedIndex != -1)
			{
				int choice = JOptionPane.showConfirmDialog(ApplicationFrame.this,"Are you sure you want to delete student "+student+" ?");
				if(choice == JOptionPane.OK_OPTION){
					studentManager.getStudentList().remove(selectedIndex);
					studentList.setListData(studentManager.getStudentsArray());
					studentManager.saveStudentList();
				}
			}
		}

		public void keyReleased(KeyEvent arg0) {
		
		}

		public void keyTyped(KeyEvent arg0) {

		}

	}

}
