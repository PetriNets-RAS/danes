/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Core.Logic;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

/**
 *
 * @author Michal
 */
public class SpeechBubble {

    private static final int ARROW_HEIGHT = 7;
    private static final int ARROW_WIDTH = 4;
    private static final int PADDING = 8;


    public static void drawString(String[] lines, int x, int y, int length, Graphics g) {
        for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], x + 5, y + i * 15);
        }
    }

    /**
     * Draw an speech bubble with text
     *
     * @param g, the graphics object to paint to
     * @param x, the x position of the object (relative to the pointy part)
     * @param y, the y position of the object (relative to the pointy part)
     * @param text, the text, that has to be written to the bubble 
     */
    public static void drawBubble(Graphics g, int x, int y, String text) {
        // Save the graphics object color and font so that we may restore
        // it later
        int numbOfTokens = 30;

        String[] lines = Logic.splitIntoLine(text, numbOfTokens);
        int wordlength = Logic.maxWorldLength(text);
        int lineLength = lineLength(lines, g);
        Color origColor = g.getColor();
        Font origFont = g.getFont();
        int width =  lineLength ;
        int height = lines.length * 15 + 10;
        if (text.length() < numbOfTokens) {
            width = 5 * text.length() + 10;
        } else if(wordlength >= numbOfTokens) {
            width = lineLength;
        }

        // Draw the base shape -- the rectangle the image will fit into as well as its outline
        g.setColor(Color.WHITE);
        g.fillRoundRect(x - width / 2, y - height - ARROW_HEIGHT, width, height, PADDING * 2, PADDING * 2);
        g.setColor(Color.BLACK);
        g.drawRoundRect(x - width / 2, y - height - ARROW_HEIGHT, width, height, PADDING * 2, PADDING * 2);

        // This is the outline to the pointy part
        g.setColor(Color.BLACK);
        g.drawLine(x, y, x - ARROW_WIDTH / 2, y - ARROW_HEIGHT);
        g.drawLine(x, y, x + ARROW_WIDTH / 2, y - ARROW_HEIGHT);

        // Restore the font and color
        g.setColor(origColor);
        g.setFont(origFont);

        //draw lines of text
        drawString(lines, x - width / 2, y - height - ARROW_HEIGHT + 18, 30, g);

    }

    public static int lineLength(String[] input, Graphics g) {
        int maxlength = 0;
        for (int i = 0; i < input.length; i++) {
            AffineTransform affinetransform = new AffineTransform();
            FontRenderContext frc = new FontRenderContext(affinetransform, true, true);
            Font font = new Font("Times New Roman", Font.PLAIN, g.getFont().getSize());
            int width = (int) (font.getStringBounds(input[i], frc).getWidth());
            System.out.println(width);

            if (width > maxlength) {
                maxlength = width;
            }

        }
        return maxlength;

    }
}
