/*
 * To the extent possible under law, the ImageJ developers have waived
 * all copyright and related or neighboring rights to this tutorial code.
 *
 * See the CC0 1.0 Universal license for details:
 *     http://creativecommons.org/publicdomain/zero/1.0/
 *     
 *     Modification GM 16/11/2022
 */

package com.mycompany.imagej;

import ij.IJ;

import ij.ImagePlus;
import ij.gui.GenericDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import javax.swing.JOptionPane;


import ij.plugin.PlugIn;
import ij.ImageJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.List;

import java.awt.event.ActionEvent;

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JFileChooser;

/**
 * A template for processing each pixel of either
 * GRAY8, GRAY16, GRAY32 or COLOR_RGB images.
 *
 * public class Process_Pixels implements PlugInFilter {
 */
public class Process_Pixels implements PlugIn {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	protected ImagePlus image;
	private String input;
	private String output;

	// image property members
	private int width;
	private int height;

	// plugin parameters
	public double value;
	public String name;

	@Override
	public void run(String args) {
		if (IJ.versionLessThan("1.32c")) {
			return;
		}
		IJ.showMessage("Autocrop Tester en Java pour ImageJ !");
		IJ.showStatus("Plugin Test started.");

		LOGGER.info("run() Demarrage du plugin Autocrop");

		// Dialogue :
		//  - selection d'une image input
		//  - selection d'un directory output
		
        // JFileChooser(String directoryPath) utilise le chemin donne
	    JFileChooser jfc = new JFileChooser( 
	            FileSystemView
	            .getFileSystemView()
	            .getHomeDirectory()
	        );

	    // l'utilisateur verra seulement les images TIFF
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	            "Tiff Images", "tif", "tiff");
	    jfc.setFileFilter(filter);
	    // pas de filtre
	    //jfc.setAcceptAllFileFilterUsed(false);

		// Input image file ...
	    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

	    // Pops up an "Open File" file chooser dialog
	    if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
	    	System.out.println("Source approuvee!");
			File selectedInput = jfc.getSelectedFile();
			input = selectedInput.getPath();
		     System.out.println("You chose to open this file: " + input); // jfc.getSelectedFile().getName());
	    } else
	    	System.out.println("Source rejetee!");
/*
		// Output directory ...
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			// try-catch block to handle exceptions
	        try {
				File selectedOutput = jfc.getSelectedFile();
	            // Display the file path/file path of Canonical of the file object
	            System.out.println("Original file path : " + selectedOutput.getPath());
	            System.out.println("Canonical file path : " + selectedOutput.getCanonicalPath());
	        }
	        catch (Exception e) {
	            System.err.println(e.getMessage());
	        }
		}
*/
		jfc.setSelectedFile(null);

		validate();

		LOGGER.info("run() FIN run Autocrop");
	}

	
	
	
	// Verification/enregistrement des fichiers I/O
    // Recuperation des parametres de traitement
	private void validate() {

			//String input  = autocropDialog.getInput();  // input file
			//String input  = new String("C:/Users/frede/OneDrive/Desktop/WT-W1.TIF");  // input file

			//String output = autocropDialog.getOutput(); // output directory path
			//String output = new String("C:/Users/frede/OneDrive/Desktop/WT-W1-Output");  // output file

			System.out.println("Input image file : " + input);
			System.out.println("Output directory : " + output);

			if (input == null || input.equals("")) {
				IJ.error("Input file is missing");
			/*
			} else if (output == null || output.equals("")) {   
				IJ.error("Output directory is missing");
				*/
			} else {
				try {
					LOGGER.info("Validation des saisies utilisateur...");
					// l'objet autocropParameters stocke tous les parametres utiles au t/t de l'image
					//autocropParameters = new AutocropParameters(input, output);
					File file = new File(input);
					System.out.println("Appel autocrop runFile(String rawImage) " + input);
					runFile(input);
					LOGGER.info("Autocrop process has ended successfully");
				} catch (Exception e) {
					LOGGER.error("An error occurred during autocrop.", e);
				}
			}
		}

	
	public void runFile(String inputImage) {
		
	System.out.println("Autocrop_.runFile()");
	File currentImageFile = new File(inputImage);
	LOGGER.info("Autocrop_.runFile() Current file: {}", currentImageFile.getAbsolutePath());
	String fileImg = currentImageFile.toString();
	File outPutFileName = new File(fileImg);
	System.out.println("Autocrop_.runFile() trying calling Autocrop constructor...");
	try {
			// creer un objet AutoCrop avec les parametres de tt contenus dans l'objet autocropParameters
			// l'objet autocropParameters recupere toutes les constantes utiles au t/t de l'image
			//   AutocropParameters autocropParameters = new AutocropParameters(input, output);
			//   currentImageFile 	: inputFile ex:"WT-W1.TIF" (AutocropParameters.inputFile contient deja cet info!)
			//   prefix 			: output    ex:"WT-W1-Output" (AutocropParameters.outputFolder contient deja cet info!)
			//   autocropParameters : argument "autocropParameters"
			//AutoCrop autoCrop = new AutoCrop(currentImageFile, autocropParameters);
		  System.out.println("trying calling Autocrop constructor...");
		}
		catch (Exception e) {
			LOGGER.error("An error occurred. Cannot run autocrop on: " + currentImageFile.getName(), e);
			IJ.error("Cannot run autocrop on " + currentImageFile.getName());
		}
	}
	
	
	
	
	
	
	/*
	
	//@Override
	public int setup(String arg, ImagePlus imp) {
		if (arg.equals("about")) {
			showAbout();
			return DONE;
		}

		image = imp;
		return DOES_8G | DOES_16 | DOES_32 | DOES_RGB;
	}

	@Override
	public void run(ImageProcessor ip) {
		// get width and height
		width = ip.getWidth();
		height = ip.getHeight();

		if (showDialog()) {
			process(ip);
			image.updateAndDraw();
		}
	}

	private boolean showDialog() {
		GenericDialog gd = new GenericDialog("Process pixels");

		// default value is 0.00, 2 digits right of the decimal point
		gd.addNumericField("value", 0.00, 2);
		gd.addStringField("name", "John");

		gd.showDialog();
		if (gd.wasCanceled())
			return false;

		// get entered values
		value = gd.getNextNumber();
		name = gd.getNextString();

		return true;
	}
    */
	
	/*
	//
	// Process an image.
	// <p>
	// Please provide this method even if {@link ij.plugin.filter.PlugInFilter} does require it;
	// the method {@link ij.plugin.filter.PlugInFilter#run(ij.process.ImageProcessor)} can only
	// handle 2-dimensional data.
	// </p>
	// <p>
	// If your plugin does not change the pixels in-place, make this method return the results and
	// change the {@link #setup(java.lang.String, ij.ImagePlus)} method to return also the
	// <i>DOES_NOTHING</i> flag.
	// </p>
	//
	// @param image the image (possible multi-dimensional)
	//
	public void process(ImagePlus image) {
		// slice numbers start with 1 for historical reasons
		for (int i = 1; i <= image.getStackSize(); i++)
			process(image.getStack().getProcessor(i));
	}

	// Select processing method depending on image type
	public void process(ImageProcessor ip) {
		int type = image.getType();
		if (type == ImagePlus.GRAY8)
			process( (byte[]) ip.getPixels() );
		else if (type == ImagePlus.GRAY16)
			process( (short[]) ip.getPixels() );
		else if (type == ImagePlus.GRAY32)
			process( (float[]) ip.getPixels() );
		else if (type == ImagePlus.COLOR_RGB)
			process( (int[]) ip.getPixels() );
		else {
			throw new RuntimeException("not supported");
		}
	}

	// processing of GRAY8 images
	public void process(byte[] pixels) {
		for (int y=0; y < height; y++) {
			for (int x=0; x < width; x++) {
				// process each pixel of the line
				// example: add 'number' to each pixel
				pixels[x + y * width] += (byte)value;
			}
		}
	}

	// processing of GRAY16 images
	public void process(short[] pixels) {
		for (int y=0; y < height; y++) {
			for (int x=0; x < width; x++) {
				// process each pixel of the line
				// example: add 'number' to each pixel
				pixels[x + y * width] += (short)value;
			}
		}
	}

	// processing of GRAY32 images
	public void process(float[] pixels) {
		for (int y=0; y < height; y++) {
			for (int x=0; x < width; x++) {
				// process each pixel of the line
				// example: add 'number' to each pixel
				pixels[x + y * width] += (float)value;
			}
		}
	}

	// processing of COLOR_RGB images
	public void process(int[] pixels) {
		for (int y=0; y < height; y++) {
			for (int x=0; x < width; x++) {
				// process each pixel of the line
				// example: add 'number' to each pixel
				pixels[x + y * width] += (int)value;
			}
		}
	}

	public void showAbout() {
		IJ.showMessage("ProcessPixels",
			"a template for processing each pixel of an image"
		);
	}
  */
	/**
	 * Main method for debugging.
	 *
	 * For debugging, it is convenient to have a method that starts ImageJ, loads
	 * an image and calls the plugin, e.g. after setting breakpoints.
	 *
	 * @param args unused
	 */
	public static void main(String[] args) throws Exception {
		// set the plugins.dir property to make the plugin appear in the Plugins menu
		// see: https://stackoverflow.com/a/7060464/1207769
		
		// Class est une classe paramétrable, 
		// On peut donc utiliser la syntaxe Class<T> où T est un type. 
		// En écrivant Class<?>, on déclare un objet Class qui peut être de n'importe quel type (? est un caractère générique)
		
		Class<?> clazz = Process_Pixels.class;
		
		// Obtient l'URL correspondant à la classe
		java.net.URL url = clazz.getProtectionDomain().getCodeSource().getLocation();
		// Mainteant qu'on a l'URL -> on convertit en File
		java.io.File file = new java.io.File(url.toURI());
		
		// La classe System contient plusieurs champs et méthodes de classe utiles
		// Parmi les fonctionnalités fournies par la classe System figurent
        // les flux d'entrée standard, de sortie standard et de sortie d'erreur 
		// accès aux propriétés et variables d'environnement définies en externe
		// un moyen de charger des fichiers et des bibliothèques ; 
		// et un procédé utilitaire pour copier rapidement une partie d'un réseau.

		// java.lang.System.setProperty() methode met la propriete systeme à la key
		// string key −> c'est le nom de la propriété systeme 
		// string value -> c'est la valeur de la propriété systeme
		
		System.setProperty("plugins.dir", file.getAbsolutePath());
		
		// start ImageJ
		//new ImageJ();

		//String nom = JOptionPane.showInputDialog("Quel est ton nom ?");
		//String message = String.format("Salut %s. Java est cool, n'est ce pas ?", nom);
        //JOptionPane.showInternalMessageDialog(null,message);
        	
		// open the Clown sample
		//ImagePlus image = IJ.openImage("http://imagej.net/images/clown.jpg");
		//image.show();

		// run the plugin
		IJ.runPlugIn(clazz.getName(), "");
	}
}
