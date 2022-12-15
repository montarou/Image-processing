package Utils;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import java.text.SimpleDateFormat;
import java.util.Calendar;


/** This class extend plugin parameters and contain the list of specific parameters available for Autocrop function. */
public class AutocropParameters { // extends PluginParameters {
	/** Logger */
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


	public String  inputFile;				/** Input file */
	//public String  inputFolder;			/** Input folder */
	public String  outputFolder;			/** Output folder */

	public AutocropParameters() {
	}

	// Constructor with default parameter
	public AutocropParameters(String inputFile, String outputFolder) {
		//super(inputFolder, outputFolder);
		inputFile 		= inputFile;	// inputFolder  Path folder containing Images
		outputFolder 	= outputFolder;	// outputFolder Path folder output analyse
	}

	// Getter : input path file
	public String getInputFile() {
		return this.inputFile;
	}
	// Getter : output path
	public String getOutputFolder() {
		return this.outputFolder;
	}
	// get local time start analyse information yyyy-MM-dd:HH-mm-ss format
	public String getLocalTime() {
		return new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss").format(Calendar.getInstance().getTime());
	}
}