package EnrollmentHelper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class frontend {
	public String checkInput(String s) {
		if (s.equals("")) {
			JOptionPane.showMessageDialog(null, "Username or password is empty");
			throw new IllegalArgumentException();
		} else {
			return s;
		}
	}

	public String checkCatalogue(String s) {
		if (s.equals("")) {
			JOptionPane.showMessageDialog(null, "Catalogue number is empty");
			throw new IllegalArgumentException();
		} else if (s.length() != 6) {
			JOptionPane.showMessageDialog(null, "Catalogue number must be 6 digits");
			throw new IllegalArgumentException();
		} else {
			return s;
		}
	}

	public void createWindow() {
		Font font1 = new Font("SansSerif", Font.PLAIN, 20);
		JFrame window = new JFrame("YorkU Enrollment Helper");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel outer = new JPanel(new BorderLayout());
		outer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		JPanel upper = new JPanel(new BorderLayout());
		JPanel button = new JPanel();
		button.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		namePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel pwPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		pwPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel coursePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		coursePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		JPanel cataloguePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		cataloguePanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

		JButton submit = new JButton("START");
		submit.setFont(font1);
		button.add(submit);

		upper.add(namePanel, BorderLayout.NORTH);
		upper.add(pwPanel, BorderLayout.CENTER);
		upper.add(coursePanel, BorderLayout.SOUTH);
		outer.add(upper, BorderLayout.NORTH);
		outer.add(cataloguePanel, BorderLayout.CENTER);
		outer.add(button, BorderLayout.SOUTH);

		JLabel name = new JLabel("USERNAME: ");
		name.setFont(font1);
		JTextField nameField = new JTextField(12);
		nameField.setFont(font1);
		namePanel.add(name);
		namePanel.add(nameField);

		JLabel pw = new JLabel("PASSWORD: ");
		pw.setFont(font1);
		JPasswordField pwField = new JPasswordField(12);
		pwField.setFont(font1);
		pwPanel.add(pw);
		pwPanel.add(pwField);

		JLabel course = new JLabel("COURSE CODE: ");
		course.setFont(font1);
		JTextField courseField = new JTextField(10);
		courseField.setFont(font1);
		coursePanel.add(course);
		coursePanel.add(courseField);

		JLabel catalogue = new JLabel("CATALOGUE #(6 digits): ");
		catalogue.setFont(font1);
		JTextField catalogueField = new JTextField(6);
		catalogueField.setFont(font1);
		cataloguePanel.add(catalogue);
		cataloguePanel.add(catalogueField);

		submit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String name = checkInput(nameField.getText());
				String password = checkInput(pwField.getText());
				String course = checkInput(courseField.getText());
				String catalogue = checkCatalogue(catalogueField.getText());
				EnrollmentHelper myHelper = new EnrollmentHelper(name, password, course, catalogue);
				ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);
				exec.scheduleAtFixedRate(myHelper.run, 0, 60, TimeUnit.SECONDS);
			}
		});

		window.setContentPane(outer);
		window.pack();
		window.setLocationRelativeTo(null);
		window.setVisible(true);
	}

	public static void main(String args[]) {
		frontend myWindow = new frontend();
		myWindow.createWindow();
	}
}
