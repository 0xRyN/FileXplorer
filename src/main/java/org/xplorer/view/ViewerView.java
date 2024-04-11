package org.xplorer.view;

import org.xplorer.util.Consts;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ViewerView extends JPanel {
    private JLabel titleLabel;
    private JLabel imageLabel;
    private JTextArea textArea, fileInfoTextArea;
    private JScrollPane textScrollPane;
    private JButton openFileButton;
    private JPanel contentPanel;
    private JPanel fileInfoPanel;

    public ViewerView() {
        initUI();
        setPreferredSize(new Dimension(Consts.SCREEN_WIDTH / 8, getHeight()));
    }

    private void initUI() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));


        titleLabel = new JLabel("Visualizer", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);


        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);


        imageLabel = new JLabel();
        imageLabel.setHorizontalAlignment(JLabel.CENTER);
        imageLabel.setVerticalAlignment(JLabel.CENTER);


        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textScrollPane = new JScrollPane(textArea);
        textScrollPane.setAlignmentX(Component.CENTER_ALIGNMENT);


        fileInfoTextArea = new JTextArea(2, 20);
        fileInfoTextArea.setEditable(false);
        fileInfoTextArea.setLineWrap(true);
        fileInfoTextArea.setWrapStyleWord(true);
        fileInfoTextArea.setAlignmentX(Component.CENTER_ALIGNMENT);


        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        openFileButton = new JButton("Open File");


        buttonPanel.add(openFileButton);
        add(titleLabel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(contentPanel);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(fileInfoTextArea);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(buttonPanel);
    }

    public void displayImage(ImageIcon image, String fileInfo) {
        contentPanel.removeAll();
        Image scaledImage = scaleImageIcon(image);
        imageLabel.setIcon(new ImageIcon(scaledImage));
        contentPanel.add(imageLabel, BorderLayout.CENTER);
        setTitle("Image Viewer");
        setFileInfo(fileInfo);
        revalidateAndRepaint();
    }

    public void displayText(String text, String fileInfo) {
        contentPanel.removeAll();
        textArea.setText(text);
        contentPanel.add(textScrollPane, BorderLayout.CENTER);
        setTitle("Text Viewer");
        setFileInfo(fileInfo);
        revalidateAndRepaint();
    }

    public void displayDefault(String fileInfo) {

        displayText("Extension non reconnue. Affichage du fichier par défaut.", fileInfo);
        setTitle("Default Viewer");
    }

    public void setFileInfo(String info) {
        fileInfoTextArea.setText(info);
    }

    private void setTitle(String title) {
        titleLabel.setText(title);
    }

    private Image scaleImageIcon(ImageIcon icon) {
        double scaleFactor = Math.min(1d, Math.min(200 / (double) icon.getIconWidth(), 200 / (double) icon.getIconHeight()));
        int width = (int) (icon.getIconWidth() * scaleFactor);
        int height = (int) (icon.getIconHeight() * scaleFactor);
        return icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
    }

    private void revalidateAndRepaint() {
        validate();
        repaint();
    }

    public void addOpenFileButtonListener(ActionListener listener) {
        openFileButton.addActionListener(listener);
    }
}
