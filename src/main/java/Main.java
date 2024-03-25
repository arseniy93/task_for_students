import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.PlotRenderingInfo;
import org.jfree.chart.plot.PlotState;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.security.KeyStore;
import java.util.*;
import java.util.List;

public class Main {
    public final double g = 9.81,
            R = 8.31,
            Mair = 0.029,
            k = 1.38e-23,
            T0 = 230,
            p0 = 1e5,
            Mdust = 8e-22,//change
            Vdust = 5e-22,
            h1 = 1.5,
            h2 = 15,
            pp = p0 * Mair / R / T0,//плотность воздуха
            wh1 = Math.exp(-(Mdust - pp * Vdust) * g * h1 / (k * T0)),
            wh2 = Math.exp(-(Mdust - pp * Vdust) * g * h2 / (k * T0));


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Main main = new Main();

        int quantity = scanner.nextInt();

        //constants
        Map<Double, Double> map = new HashMap<>(main.dpY(quantity));

        var series1 = new XYSeries("2024");
        for(Map.Entry<Double,Double> entry:map.entrySet()){
            series1.add(entry.getKey(),entry.getValue());
            System.out.println(entry.getKey()+":"+entry.getValue());
        }



        var dataset = new XYSeriesCollection();
        dataset.addSeries(series1);

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Концентраия пыли от выстоты",
                "Высоты, м",
                "Отношение концентрации",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );


        ChartPanel chartPanel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setSize(800, 600);
        frame.setContentPane(chartPanel);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private Map<Double, Double> dpY(int quality) {

        Map<Double, Double> list2 = new HashMap<>(quality);

        for (int i = 0; i < quality; i++) {

            list2.put((h2 - h1) / quality * i, Math.exp(-(Mdust - pp * Vdust) * g *((h2 - h1) / quality * i) / (k * T0)));
        }



        return list2;
    }

}
