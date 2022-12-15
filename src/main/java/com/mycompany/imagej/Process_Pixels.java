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
import Utils.AutocropParameters; 
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
		
		// Utilisation de la classe JFileChooser (extends JComponent)
		// JFileChooser fournit un mécanisme simple permettant à l'utilisateur de choisir un fichier
        // C'est un selecteur qui peut etre integrer dans une boite de dialogue
		//   le constructeur par default l'instancie dans le repertoire 'Documents'
		
		//   Si on veut l'u=instancier on peut utiliser le contructeur prenant une chaine de caracterere
		//   qui sera le chemin au repertoire souhaité
		
		//   On peut utiliser une reference de type File en parametre, qui sera le repertoire dans 
		//   lequel s'ouvrira le selecteur de fichier
		
		// JFileChooser(String directoryPath) utilise le chemin donne
	    JFileChooser jfc = new JFileChooser( 
	            FileSystemView
	            .getFileSystemView()
	            .getHomeDirectory()    // Ouvre le selecteur dans le repertoire Deskstop
	        );

	    //String s1 = FileSystemView.getFileSystemView().getHomeDirectory().toString();
	    //System.out.println("test" + s1);
	    
	    // l'utilisateur verra seulement les images TIFF
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Tiff Images", "tif", "tiff");
	    jfc.setFileFilter(filter);  /// assigne le filtre au JFileChooser
	    
	    // pas de filtre
	    //jfc.setAcceptAllFileFilterUsed(false);

		// Input image file ...
	    jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);

	    // Pops up an "Open File" file chooser dialog
	    //   public int showOpenDialog(Component parent)
	    //    -> le parametre parent determine le frame dont depend la boite de dialogue depend
	    //    ->  en cas de parametre parent null le dialogue ne depend pas d'une fenetre visible
	    
	    
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
