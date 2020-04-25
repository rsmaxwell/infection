package com.rsmaxwell.infection.model.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import com.rsmaxwell.infection.model.config.Config;
import com.rsmaxwell.infection.model.config.Group;
import com.rsmaxwell.infection.model.integrate.Integrate;

public class Populations {

	public Map<String, Population> populations = new HashMap<String, Population>();
	public Population everyone;
	public double totalPopulation = 0;

	public static Populations INSTANCE;

	public Populations() throws Exception {
		for (String id : Config.INSTANCE.groups.keySet()) {
			Group group = Config.INSTANCE.groups.get(id);
			populations.put(id, new Population(id, group));
		}

		Group all = new Group();
		all.name = "Everyone";

		everyone = new Population("All", all);
		INSTANCE = this;

		// ************************************************************************
		// * Sum the total population
		// ************************************************************************
		totalPopulation = 0.0;
		for (String id : Config.INSTANCE.groups.keySet()) {
			Group group = Config.INSTANCE.groups.get(id);

			if (group.population < 0) {
				throw new Exception("The population of group [ " + id + " : " + group.name + " ]: cannot be negative: " + group.population);
			}

			totalPopulation += group.population;
		}

		if (totalPopulation < 1e-6) {
			throw new Exception("The totalPopulation cannot be negative: " + totalPopulation);
		}
	}

	// The SIR deltas for ALL the populations are calculated first, before adding
	// the deltas to the SIR values
	//
	// This is because ALL the current SIR values are used to calculate the SIR
	// deltas for each population, so they must not be changed till we know all the
	// deltas
	public void step(double t, double dt, Integrate integrate) {

		// Calculate the SIR deltas for this step
		for (String id : populations.keySet()) {
			Population population = populations.get(id);
			population.step(t, dt, integrate, totalPopulation);
		}

		// Add the SIR deltas to the SIR values
		for (String id : populations.keySet()) {
			Population population = populations.get(id);

			population.S.value += population.S.delta;
			population.I.value += population.I.delta;
			population.R.value += population.R.delta;
		}
	}

	public void store(double t) {

		everyone.zero();

		for (String id : populations.keySet()) {
			Population population = populations.get(id);
			population.store(t);

			everyone.add(population, totalPopulation);
		}

		everyone.store(t);
	}

	public void print(PrintStream stream, String[] filter) {
		PrintWriter out = new PrintWriter(stream);
		print(out, filter);
	}

	public void print(PrintWriter out, String[] filter) {
		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.print(out);
			}
		}
		if (everyone.matches(filter)) {
			everyone.print(out);
		}
	}

	public void print(File outputDirectory, String[] filter) throws IOException {
		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.print(outputDirectory);
			}
		}
		if (everyone.matches(filter)) {
			everyone.print(outputDirectory);
		}
	}

	public void toJson(PrintStream stream, String[] filter) {
		PrintWriter out = new PrintWriter(stream);
		toJson(out, filter);
	}

	public void toJson(PrintWriter out, String[] filter) {

		out.println("{ ");
		out.println("   \"Populations\": {");

		String separator = "";
		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				out.printf("%s", separator);
				population.toJson(out);
				separator = ", \n";
			}
		}
		out.printf("%s", separator);

		if (everyone.matches(filter)) {
			everyone.toJson(out);
		}

		out.printf("\n");
		out.println("   }");
		out.println("}");
	}

	public void toJson(File outputDirectory, String[] filter) throws IOException {
		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.toJson(outputDirectory);
			}
		}
		if (everyone.matches(filter)) {
			everyone.toJson(outputDirectory);
		}
	}

	public void swing(String[] filter) {
		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.output_swing();
			}
		}
		if (everyone.matches(filter)) {
			everyone.output_swing();
		}
	}

	private int count(String[] filter) {

		int count = 0;

		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				count++;
			}
		}
		if (everyone.matches(filter)) {
			count++;
		}

		return count;
	}

	private boolean deleteDirectory(File directoryToBeDeleted) {
		File[] allContents = directoryToBeDeleted.listFiles();
		if (allContents != null) {
			for (File file : allContents) {
				deleteDirectory(file);
			}
		}
		return directoryToBeDeleted.delete();
	}

	// ************************************************************************************************
	//
	// ************************************************************************************************
	public void output_jpeg_archive(OutputStream stream, String[] filter, int width, int height) throws Exception {

		int count = count(filter);
		if (count < 1) {
			throw new Exception("No populations match filter: " + Arrays.toString(filter));
		}

		File tempDir = Files.createTempDirectory("xyz").toFile();

		System.out.println("Populations.output_jpeg_archive: tempDir: " + tempDir.getAbsolutePath());

		try {
			for (Population population : populations.values()) {
				if (population.matches(filter)) {
					population.output_jpeg(tempDir, width, height);
				}
			}
			if (everyone.matches(filter)) {
				everyone.output_jpeg(tempDir, width, height);
			}

			File[] srcFiles = tempDir.listFiles();
			ZipOutputStream zipOut = new ZipOutputStream(stream);
			for (File srcFile : srcFiles) {
				FileInputStream fis = new FileInputStream(srcFile);
				ZipEntry zipEntry = new ZipEntry(srcFile.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();

		} finally {
			deleteDirectory(tempDir);
		}
	}

	public void output_jpeg(OutputStream stream, String[] filter, int width, int height) throws Exception {

		int count = count(filter);
		if (count < 1) {
			throw new Exception("No populations match filter: " + Arrays.toString(filter));
		}
		if (count > 1) {
			throw new Exception("Too many populations match filter: count:" + count + ", filter: " + Arrays.toString(filter));
		}

		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.output_jpeg(stream, width, height);
			}
		}
		if (everyone.matches(filter)) {
			everyone.output_jpeg(stream, width, height);
		}
	}

	public void output_jpeg(File outputDirectory, String[] filter, int width, int height) throws IOException {
		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.output_jpeg(outputDirectory, width, height);
			}
		}
		if (everyone.matches(filter)) {
			everyone.output_jpeg(outputDirectory, width, height);
		}
	}

	// ************************************************************************************************
	//
	// ************************************************************************************************
	public void output_png_archive(OutputStream stream, String[] filter, int width, int height) throws Exception {

		int count = count(filter);
		if (count < 1) {
			throw new Exception("No populations match filter: " + Arrays.toString(filter));
		}

		File tempDir = Files.createTempDirectory("xyz").toFile();

		System.out.println("Populations.output_png_archive: tempDir: " + tempDir.getAbsolutePath());

		try {
			for (Population population : populations.values()) {
				if (population.matches(filter)) {
					population.output_png(tempDir, width, height);
				}
			}
			if (everyone.matches(filter)) {
				everyone.output_png(tempDir, width, height);
			}

			File[] srcFiles = tempDir.listFiles();
			ZipOutputStream zipOut = new ZipOutputStream(stream);
			for (File srcFile : srcFiles) {
				FileInputStream fis = new FileInputStream(srcFile);
				ZipEntry zipEntry = new ZipEntry(srcFile.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();

		} finally {
			deleteDirectory(tempDir);
		}
	}

	public void output_png(OutputStream stream, String[] filter, int width, int height) throws Exception {

		int count = count(filter);
		if (count < 1) {
			throw new Exception("No populations match filter: " + Arrays.toString(filter));
		}
		if (count != 1) {
			throw new Exception("Too many populations match filter: count:" + count + ", filter: " + Arrays.toString(filter));
		}

		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.output_png(stream, width, height);
			}
		}
		if (everyone.matches(filter)) {
			everyone.output_png(stream, width, height);
		}
	}

	public void output_png(File outputDirectory, String[] filter, int width, int height) throws IOException {
		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.output_png(outputDirectory, width, height);
			}
		}
		if (everyone.matches(filter)) {
			everyone.output_png(outputDirectory, width, height);
		}
	}

	public void output_svg_archive(OutputStream stream, String[] filter, int width, int height) throws Exception {

		int count = count(filter);
		if (count < 1) {
			throw new Exception("No populations match filter: " + Arrays.toString(filter));
		}

		File tempDir = Files.createTempDirectory("xyz").toFile();
		try {
			for (Population population : populations.values()) {
				if (population.matches(filter)) {
					population.output_svg(tempDir, width, height);
				}
			}
			if (everyone.matches(filter)) {
				everyone.output_svg(tempDir, width, height);
			}

			File[] srcFiles = tempDir.listFiles();
			ZipOutputStream zipOut = new ZipOutputStream(stream);
			for (File srcFile : srcFiles) {
				FileInputStream fis = new FileInputStream(srcFile);
				ZipEntry zipEntry = new ZipEntry(srcFile.getName());
				zipOut.putNextEntry(zipEntry);

				byte[] bytes = new byte[1024];
				int length;
				while ((length = fis.read(bytes)) >= 0) {
					zipOut.write(bytes, 0, length);
				}
				fis.close();
			}
			zipOut.close();

		} finally {
			deleteDirectory(tempDir);
		}
	}

	public void output_svg(OutputStream stream, String[] filter, int width, int height) throws Exception {

		int count = count(filter);
		if (count < 1) {
			throw new Exception("No populations match filter: " + Arrays.toString(filter));
		}
		if (count != 1) {
			throw new Exception("Too many populations match filter: count:" + count + ", filter: " + Arrays.toString(filter));
		}

		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.output_svg(stream, width, height);
			}
		}
		if (everyone.matches(filter)) {
			everyone.output_svg(stream, width, height);
		}
	}

	public void output_svg(File outputDirectory, String[] filter, int width, int height) throws IOException {
		for (Population population : populations.values()) {
			if (population.matches(filter)) {
				population.output_svg(outputDirectory, width, height);
			}
		}
		if (everyone.matches(filter)) {
			everyone.output_svg(outputDirectory, width, height);
		}
	}
}
