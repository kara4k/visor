package com.kara4k.visor.ui;

import com.kara4k.visor.main.ResultPrinter;
import com.kara4k.visor.model.Params;
import com.kara4k.visor.ui.MainFrameController.Callback;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import javax.swing.*;

public class MainFrame extends JFrame implements Callback {

	private final JLabel label = new JLabel();
	private final JTextField textField = new JTextField();
	private final JButton button = new JButton("write");
	private Color color = Color.WHITE;
	private MainFrameController controller;

	public MainFrame() throws HeadlessException {
		this.setTitle("Visor");
		this.setSize(315, 140);
		this.setBounds(400, 400, 315, 140);
		this.setResizable(false);
		this.setLayout(null);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		label.setBounds(30, 20, 300, 30);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(label);

		textField.setBounds(28, 65, 190, 30);
		this.add(textField);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setBounds(225, 65, 60, 30);
		button.setFont((new Font("Arial", Font.PLAIN, 10)));
		button.addActionListener(e -> controller.appendText(textField.getText()));
		this.add(button);

		final String saveCoordsKey = "SaveCoords";
		final String saveCoordsWithDescriptionKey = "SaveCoordsWithText";
		final ActionMap actionMap = this.getRootPane().getActionMap();
		actionMap.put(saveCoordsKey, new AbstractAction() {
			@Override
			public void actionPerformed(final ActionEvent actionEvent) {
				controller.appendPoint();
			}
		});
		actionMap.put(saveCoordsWithDescriptionKey, new AbstractAction() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				final String coords = controller.freezePoint();
				final String description = JOptionPane.showInputDialog(coords);
				if (description != null) {
					controller.appendFrostPoint(description);
				}
			}
		});
		final InputMap im = this.getRootPane().getInputMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_DOWN_MASK), saveCoordsKey);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_DOWN_MASK), saveCoordsWithDescriptionKey);
	}

	@Override
	public void paint(final Graphics g) {
		super.paint(g);
		g.setColor(color);
		g.fillRect(30, 45, 30, 30);
	}

	public void start(final Params params) throws InterruptedException {
		controller = new MainFrameController(params, this);
		controller.run();
	}

	@Override
	public void showWindow(final String filename) {
		textField.setToolTipText(filename);
		button.setToolTipText(filename);
		this.setVisible(true);
	}

	@Override
	public void showError(final String message) {
		JOptionPane.showMessageDialog(this, message);
	}

	@Override
	public void update(final Point point, final Color color) {
		label.setText(ResultPrinter.createUiFormatString(point, color));
		this.color = color;
		this.repaint(30, 45, 30, 30);
	}
}
