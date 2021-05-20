package main;

import java.awt.GridLayout;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;


import javax.swing.*;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class ItemListenerDemo {
	JFrame f;  
	private JPanel panel1;
	private JPanel panel2;
	private ItemListenerDemo() throws JsonParseException, JsonMappingException, IOException, SQLException{  
	    f=new JFrame();  
	    JTextArea textArea1=new JTextArea("main text");  
	    textArea1.setBounds(10,30, 200,200);  
	    
	    JTextArea textArea2=new JTextArea("visit text");  
	    textArea2.setBounds(10,30, 200,200);  
	    
	    JTextArea textArea3=new JTextArea("help text");  
	    textArea3.setBounds(10,30, 200,200);  
	    
	    
	    itemTabPanel1(); 
	    itemTabPanel2();
//	    JPanel p2=new JPanel();  
//	    p2.add(textArea2);
	    JPanel p3=new JPanel();
	    p3.add(textArea3);
	    
	    
	    JTabbedPane tp=new JTabbedPane();  
	    tp.setBounds(0,0,600,300);  
	    //tp.addTab("main",p1);
	    tp.add("Traveller Creation",panel1);  
	    tp.add("visit",panel2);  
	    tp.add("help",p3);    
	    f.add(tp);  
	    f.setSize(400,400);  
	    f.setLayout(null);  
	    f.setVisible(true);  
	}  
	public void itemTabPanel1() {
		panel1 = new JPanel();
		panel1.setLayout(new GridLayout(9,4));
		
		JLabel nameLabel = new JLabel("Name: ");
		panel1.add(nameLabel);
		nameLabel.setBounds(10,10,0,0);
		JTextField nameTf = new JTextField();
		panel1.add(nameTf);
		
		JLabel phoneLabel = new JLabel("Phone: ");
		panel1.add(phoneLabel);
		
		JTextField phoneTf = new JTextField();
		panel1.add(phoneTf);
		
		JLabel ageLabel = new JLabel("Age: ");
		panel1.add(ageLabel);
		
		JTextField ageTf = new JTextField();
		panel1.add(ageTf);
		JLabel nullLabel = new JLabel("");
		panel1.add(nullLabel);
		JLabel nullLabel2 = new JLabel("");
		panel1.add(nullLabel2);
		
		JLabel latLabel = new JLabel("Latitude: ");
		panel1.add(latLabel);
		
		JTextField latTf = new JTextField();
		panel1.add(latTf);
		
		JLabel lonLabel = new JLabel("Longitude: ");
		panel1.add(lonLabel);
		
		JTextField lonTf = new JTextField();
		panel1.add(lonTf);
		
		Integer[] ratings = {1,2,3,4,5,6,7,8,9,10};

		JComboBox<Integer> comboBox1 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox2= new JComboBox<>(ratings);
		JComboBox<Integer> comboBox3 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox4 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox5 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox6 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox7 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox8 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox9 = new JComboBox<>(ratings);
		JComboBox<Integer> comboBox10 = new JComboBox<>(ratings);
		
		JLabel term1Label = new JLabel("Cafe: ");
		panel1.add(term1Label);
		panel1.add(comboBox1);
		
		JLabel term2Label = new JLabel("Sea: ");
		panel1.add(term2Label);
		panel1.add(comboBox2);
		JLabel term3Label = new JLabel("Museum: ");
		panel1.add(term3Label);
		panel1.add(comboBox3);
		JLabel term4Label = new JLabel("Restaurant: ");
		panel1.add(term4Label);
		panel1.add(comboBox4);
		JLabel term5Label = new JLabel("Stadium: ");
		panel1.add(term5Label);
		panel1.add(comboBox5);
		JLabel term6Label = new JLabel("Cinema: ");
		panel1.add(term6Label);
		panel1.add(comboBox6);
		JLabel term7Label = new JLabel("Mountain: ");
		panel1.add(term7Label);
		panel1.add(comboBox7);
		JLabel term8Label = new JLabel("Lake: ");
		panel1.add(term8Label);
		panel1.add(comboBox8);
		JLabel term9Label = new JLabel("River: ");
		panel1.add(term9Label);
		panel1.add(comboBox9);
		JLabel term10Label = new JLabel("Bar: ");
		panel1.add(term10Label);
		panel1.add(comboBox10);
		
		JButton addBtn = new JButton("Add Traveller");
		panel1.add(addBtn);
	}
	
	public void itemTabPanel2() throws JsonParseException, JsonMappingException, IOException, SQLException {
		panel2 = new JPanel();
		panel2.setLayout(new GridLayout(1,2));

		JLabel nameLabel = new JLabel("Select Traveller: ");
		panel2.add(nameLabel);
		
		ArrayList<Traveller> travellers = new ArrayList<Traveller>();
		travellers = Ergasia.tester.readJson();
		
		JComboBox<Traveller> testCombo = new JComboBox<Traveller>();
		testCombo.setModel(new DefaultComboBoxModel<Traveller>(travellers.toArray(new Traveller[0])));
		panel2.add(testCombo);
		
		
	}
	
	
	public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException, SQLException {  
	    
		new ItemListenerDemo();  
	} 
}
