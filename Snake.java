package doublesnake;

// Snake class is being called in RenderPanel class, and is used for setting snakes through arrays and how the value of arrays changes help their movements.
public class Snake {
    private int length = 20;

    // The x and y together could be regard as a point. Simply set them apart into x, y values and create two arrays, we can get the snakes' positions and initial attributes.
    public Snake(int[] x, int[] y, int scale, int pos) {
        for (int i = 0; i < length; i++) {
            x[i] = pos - i * scale;
            y[i] = pos;
        }
    }

    // Change the values of the arrays so that the different "parts" (points) of the snakes are able to move continuously.
    public void move(int[] x, int[] y) {
        for (int i = length; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
    }
}