package view;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class HelpScreen {
    private JButton returnToHomeButton;
    private JPanel HelpScreenPanel;
    private PDFViewerPanel pdfViewerPanel;
    private JButton goToTopButton;
    private JButton previousPageButton;
    private JButton nextPageButton;

    public HelpScreen( User user ) {

        JFrame frame = new JFrame("Help");
        frame.setContentPane(HelpScreenPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(775, 1080);
        frame.setResizable(false);
        frame.setVisible(true);

        returnToHomeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                new HomeScreen( user );
            }
        });
        nextPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( pdfViewerPanel.getPageNumber() < pdfViewerPanel.getPageCount()-1 )
                    pdfViewerPanel.setPageNumber( pdfViewerPanel.getPageNumber() + 1 );
            }
        });
        previousPageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if( pdfViewerPanel.getPageNumber() > 0 )
                    pdfViewerPanel.setPageNumber( pdfViewerPanel.getPageNumber() - 1 );
            }
        });
        goToTopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                pdfViewerPanel.setPageNumber(0);
            }
        });
    }

    private void createUIComponents() {

        pdfViewerPanel = new PDFViewerPanel();

        try {
            pdfViewerPanel.loadPdf("Documentation/Programa2Futoshiki.pdf");
        } catch (IOException e) {
            System.out.println("Unable to load document");
            throw new RuntimeException(e);
        }
    }
}
