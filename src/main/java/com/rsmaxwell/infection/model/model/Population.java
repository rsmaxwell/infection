package com.rsmaxwell.infection.model.model;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.apache.batik.svggen.SVGGraphics2DIOException;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.encoders.SunJPEGEncoderAdapter;
import org.jfree.chart.encoders.SunPNGEncoderAdapter;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import com.rsmaxwell.infection.model.config.Group;
import com.rsmaxwell.infection.model.integrate.Integrate;
import com.rsmaxwell.infection.model.output.Result;
import com.rsmaxwell.infection.model.quantity.Infected;
import com.rsmaxwell.infection.model.quantity.Quantity;
import com.rsmaxwell.infection.model.quantity.Recovered;
import com.rsmaxwell.infection.model.quantity.Susceptible;

public class Population {

	public String id;
	public Group group;

	public Quantity S;
	public Quantity I;
	public Quantity R;

	public List<Result> results = new ArrayList<Result>();

	public Population(String id, Group group) {
		this.id = id;
		this.group = group;

		double iStart = group.iStart;
		double sStart = 1.0 - group.iStart;
		double rStart = 0;

		S = new Susceptible(sStart);
		I = new Infected(iStart);
		R = new Recovered(rStart);
	}

	public void store(double t) {
		results.add(new Result(t, S.value, I.value, R.value));
	}

	public void step(double t, double dt, Integrate integrate, double totalPopulation) {
		integrate.step(t, dt, this, totalPopulation);
	}

	public void zero() {
		S.value = 0;
		I.value = 0;
		R.value = 0;
	}

	public void add(Population population, double totalPopulation) {

		double factor = population.group.population / totalPopulation;

		S.value += factor * population.S.value;
		I.value += factor * population.I.value;
		R.value += factor * population.R.value;
	}

	public boolean matches(String[] filter) {

		if (filter == null) {
			return true;
		}

		for (String i : filter) {
			if (i.equals(id)) {
				return true;
			}
		}

		return false;
	}

	public void print(File file) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			PrintWriter out = new PrintWriter(fileOutputStream);
			print(out);
		}
	}

	public void print(PrintWriter out) {

		out.println("");
		out.println("Population: " + id);
		out.println("");
		out.println("Time   Susceptible    Infected    Recovered");
		out.println("-------------------------------------------");

		for (Result result : results) {
			result.print(out);
		}
	}

	public void toJson(File file) throws IOException {
		try (FileOutputStream fileOutputStream = new FileOutputStream(file)) {
			PrintWriter out = new PrintWriter(fileOutputStream);
			toJson(out);
		}
	}

	public void toJson(PrintWriter out) {

		out.println("      \"" + id + "\": {");
		out.println("         \"Results\": [");

		String separator = "";
		for (Result result : results) {
			out.printf("%s            ", separator);
			result.toJson(out);
			separator = ", \n";
		}

		out.printf("\n");
		out.printf("         ]\n");
		out.printf("      }");
	}

	private JFreeChart createChart() {

		XYSeries seriesS = new XYSeries("Susceptible");
		XYSeries seriesI = new XYSeries("Infected");
		XYSeries seriesR = new XYSeries("Recovered");

		for (Result result : results) {
			seriesS.add(result.t, result.S);
			seriesI.add(result.t, result.I);
			seriesR.add(result.t, result.R);
		}

		XYSeriesCollection dataset = new XYSeriesCollection();
		dataset.addSeries(seriesS);
		dataset.addSeries(seriesI);
		dataset.addSeries(seriesR);

		String title = "Population: " + id;
		String xLabel = "Time";
		String yLabel = "Fraction of Population";
		boolean legend = true;
		boolean tooltips = true;
		boolean urls = false;
		JFreeChart chart = ChartFactory.createXYLineChart(title, xLabel, yLabel, dataset, PlotOrientation.VERTICAL, legend, tooltips, urls);

		XYPlot plot = chart.getXYPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setRangeGridlinesVisible(true);
		plot.setRangeGridlinePaint(Color.BLACK);
		plot.setDomainGridlinesVisible(true);
		plot.setDomainGridlinePaint(Color.BLACK);

		XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
		renderer.setSeriesStroke(0, new BasicStroke(2.0f));
		renderer.setSeriesStroke(1, new BasicStroke(2.0f));
		renderer.setSeriesStroke(2, new BasicStroke(2.0f));

		chart.getLegend().setFrame(BlockBorder.NONE);
		chart.setTitle(new TextTitle(title, new Font("Serif", java.awt.Font.BOLD, 18)));

		return chart;
	}

	public void output_swing() {
		JFreeChart chart = createChart();

		// ******************************************
		// * Output to a Panel in a Swing app
		// ******************************************
		ChartPanel chartPanel = new ChartPanel(chart);
		chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
		chartPanel.setBackground(Color.white);

		JFrame frame = new JFrame("Infection Simulation");
		frame.add(chartPanel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public void output_jpeg(File outputDirectory, int width, int height) throws IOException {
		File file = new File(outputDirectory, id + ".jpeg");
		FileOutputStream out = new FileOutputStream(file);
		output_jpeg(out, width, height);
	}

	public void output_jpeg(OutputStream out, int width, int height) throws IOException {
		JFreeChart chart = createChart();
		BufferedImage image = chart.createBufferedImage(width, height);

		// ********************************************************
		// * We need to remove the alpha channel from the image
		// * or SunJPEGEncoderAdapter will blow up with:
		// * "javax.imageio.IIOException: Bogus input colorspace"
		// ********************************************************

		// Create a second BufferedImage of type TYPE_INT_RGB...
		BufferedImage copy = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);

		// Paint the original to the copy...
		Graphics2D g2d = copy.createGraphics();
		g2d.setColor(Color.WHITE); // Or what ever fill color you want...
		g2d.fillRect(0, 0, copy.getWidth(), copy.getHeight());
		g2d.drawImage(image, 0, 0, null);
		g2d.dispose();

		SunJPEGEncoderAdapter encoder = new SunJPEGEncoderAdapter();
		encoder.encode(copy, out);
	}

	public void output_png(File outputDirectory, int width, int height) throws IOException {
		File file = new File(outputDirectory, id + ".png");
		FileOutputStream out = new FileOutputStream(file);
		output_png(out, width, height);
	}

	public void output_png(OutputStream out, int width, int height) throws IOException {
		JFreeChart chart = createChart();
		BufferedImage image = chart.createBufferedImage(width, height);
		SunPNGEncoderAdapter encoder = new SunPNGEncoderAdapter();
		encoder.encode(image, out);
	}

	public void output_svg(File outputDirectory, int width, int height) throws IOException {

		File file = new File(outputDirectory, id + ".svg");
		FileOutputStream stream = new FileOutputStream(file);

		output_svg(stream, width, height);

		// no exception, that means ok
		System.out.println("Population.output_svg: SVG = " + file.getAbsolutePath());
	}

	public void output_svg(OutputStream out, int width, int height)
			throws UnsupportedEncodingException, FileNotFoundException, SVGGraphics2DIOException {

		JFreeChart chart = createChart();

		// ******************************************
		// * Output to SVG file
		// ******************************************
		DOMImplementation domImpl = GenericDOMImplementation.getDOMImplementation();

		// Create an instance of org.w3c.dom.Document
		Document document = domImpl.createDocument(null, "svg", null);

		// Create an instance of the SVG Generator
		SVGGraphics2D svgGenerator = new SVGGraphics2D(document);

		// set the precision to avoid a null pointer exception in Batik 1.5
		svgGenerator.getGeneratorContext().setPrecision(6);

		// Ask the chart to render into the SVG Graphics2D implementation
		chart.draw(svgGenerator, new Rectangle2D.Double(0, 0, width, height), null);

		// Finally, stream out SVG to a file using UTF-8 character to byte encoding
		boolean useCSS = true;
		Writer writer = new OutputStreamWriter(out, "UTF-8");
		svgGenerator.stream(writer, useCSS);
	}
}
