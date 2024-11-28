package view;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFViewerPanel extends JPanel {
    private PDDocument document;          // Current PDF document
    private PDFRenderer pdfRenderer;     // Renderer for the document
    private int pageNumber = 0;          // Current page number

    /**
     * Panel constructor
     */
    public PDFViewerPanel() {
        // Default constructor: no PDF loaded initially
        this.setPreferredSize(new Dimension(800, 1000)); // Default size
    }

    /**
     * Load a new PDF file into the viewer.
     *
     * @param pdfPath Path to the PDF file.
     * @throws IOException If the file cannot be loaded.
     */
    public void loadPdf(String pdfPath) throws IOException {
        // Close the current document if any
        if (document != null) {
            document.close();
        }

        // Load the new document
        document = PDDocument.load(new File(pdfPath));
        pdfRenderer = new PDFRenderer(document);

        // Reset to the first page
        pageNumber = 0;

        // Repaint to show the new document
        repaint();
    }

    /**
     * It transforms the pdf to a graphic
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (document == null) {
            // If no document is loaded, show a placeholder
            g.drawString("No PDF Loaded", getWidth() / 2 - 50, getHeight() / 2);
            return;
        }

        try {
            // Render the specified page as an image
            BufferedImage image = pdfRenderer.renderImageWithDPI(pageNumber, 90);
            g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Changes the PDF to the specified page
     * @param pageNumber The page number
     */
    public void setPageNumber(int pageNumber) {
        if (document == null) {
            throw new IllegalStateException("No PDF document loaded.");
        }
        if (pageNumber >= 0 && pageNumber < document.getNumberOfPages()) {
            this.pageNumber = pageNumber;
            repaint(); // Repaint to show the new page
        } else {
            throw new IllegalArgumentException("Invalid page number.");
        }
    }

    /**
     * It returns the page number
     * @return The active page number
     */
    public int getPageNumber() {
        return pageNumber;
    }

    /**
     * Gets the total number of pages
     * @return The total sum
     */
    public int getPageCount() {
        if (document == null) {
            return 0;
        }
        return document.getNumberOfPages();
    }

    /**
     * Closes the current PDF
     * @throws IOException If there is no file to close
     */
    public void closeDocument() throws IOException {
        if (document != null) {
            document.close();
            document = null;
            repaint(); // Clear the panel
        }
    }
}
