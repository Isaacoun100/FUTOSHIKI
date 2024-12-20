package view;

import model.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class AboutUs {
    private JButton returnToHomeButton;
    private JPanel HelpScreenPanel;
    private JButton goToTopButton;
    private JButton previousPageButton;
    private JButton nextPageButton;
    private PDFViewerPanel pdfViewerPanel;
    private JPanel AboutUsPanel;

    /**
     * Constructor to the Panel
     * @param user The user to load
     */
    public AboutUs( User user ) {

        JFrame frame = new JFrame("Help");
        frame.setContentPane(AboutUsPanel);
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

    /**
     * Creates the pdf viewer frame
     */
    private void createUIComponents() {

        pdfViewerPanel = new PDFViewerPanel();

        try {
            pdfViewerPanel.loadPdf("Documentation/programa2_futoshiki_documentación_del_proyecto.pdf");
        } catch (IOException e) {
            System.out.println("Unable to load document");
            throw new RuntimeException(e);
        }
    }
}
