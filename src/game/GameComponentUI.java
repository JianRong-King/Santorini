package game;

import javax.swing.*;
import java.awt.*;

/**
 * Creates UI component for the Santorini game.
 *
 * @author Louis Jeremie Ing
 *
 * @ModifiedBy Wilson Tan
 *
 * @version 2.2
 */
public class GameComponentUI {
    private static int locationButtonWidth;
    private static int locationButtonHeight;

    /**
     * Create a button for a location on the board.
     *
     * @return The created JButton.
     */
    public static JButton createLocationButton(){
        JButton button = new JButton();
        button.setPreferredSize(new Dimension(locationButtonWidth, locationButtonHeight));
        return button;
    }

    /**
     * Create a panel for the board.
     *
     * @return The created JPanel.
     */
    public static JPanel createBoardPanel(int width, int height){
        JPanel panel = new JPanel(null);
        panel.setPreferredSize((new Dimension(
                locationButtonWidth * width,
                locationButtonHeight * height)));
        return panel;
    }

    /**
     * Create a frame for the game.
     *
     * @return The created JFrame.
     */
    public static JFrame createGameFrame(int windowWidth, int windowHeight){
        JFrame frame = new JFrame("Santorini Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(windowWidth, windowHeight);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    /**
     * Set the width of the location button.
     *
     * @param newLocationButtonWidth The new width for the location button.
     */
    public static void setLocationButtonWidth(int newLocationButtonWidth) {
        locationButtonWidth = newLocationButtonWidth;
    }

    /**
     * Get the width of the location button.
     *
     * @return The width of the location button.
     */
    public static int getLocationButtonWidth() {
        return locationButtonWidth;
    }

    /**
     * Set the height of the location button.
     *
     * @param newLocationButtonHeight The new height for the location button.
     */
    public static void setLocationButtonHeight(int newLocationButtonHeight) {
        locationButtonHeight = newLocationButtonHeight;
    }

    /**
     * Get the height of the location button.
     *
     * @return The height of the location button.
     */
    public static int getLocationButtonHeight() {
        return locationButtonHeight;
    }
}
