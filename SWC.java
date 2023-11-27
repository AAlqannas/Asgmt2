import java.io.BufferedWriter;
import java.io.FileWriter;

public class SensorDataProcessor {

    // Sensor data and limits.
    private double[][][] data;
    private double[][] limit;

    // Constructor
    public SensorDataProcessor(double[][][] data, double[][] limit) {
        this.data = data;
        this.limit = limit;
    }

    // Calculates the average of sensor data
    private double average(double[] array) {
        double val = 0;
        for (int i = 0; i < array.length; i++) {
            val += array[i];
        }
        return val / array.length;
    }

    // Calculate data
    public void calculate(double d) {
        double[][][] data2 = new double[data.length][data[0].length][data[0][0].length];
        BufferedWriter out = null;

        // Write racing stats data into a file
        try {
            out = new BufferedWriter(new FileWriter("RacingStatsData.txt"));

            for (int i = 0; i < data.length; i++) {
                for (int j = 0; j < data[0].length; j++) {
                    for (int k = 0; k < data[0][0].length; k++) {
                        data2[i][j][k] = data[i][j][k] / d - Math.pow(limit[i][j], 2.0);

                        if (average(data2[i][j]) > 10 && average(data2[i][j]) < 50) {
                            break;
                        } else if (Math.max(data[i][j][k], data2[i][j][k]) > data[i][j][k]) {
                            break;
                        } else if (Math.pow(Math.abs(data[i][j][k]), 3) <
                                Math.pow(Math.abs(data2[i][j][k]), 3) &&
                                average(data[i][j]) < data2[i][j][k] && (i + 1) * (j + 1) > 0) {
                            data2[i][j][k] *= 2;
                        } else {
                            continue;
                        }
                    }
                }
            }

            for (int i = 0; i < data2.length; i++) {
                for (int j = 0; j < data2[0].length; j++) {
                    for (int k = 0; k < data2[0][0].length; k++) {
                        out.write(data2[i][j][k] + "\t");
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (Exception e) {
                    System.out.println("Error closing the file: " + e);
                }
            }
        }
    }
}