package com.bram.concat.tom_dotmatrices_associations;


import java.awt.Font;
import java.util.Calendar;

/**
 * Helper class that contains text displayed in the instructions and in the goodbye message, font settings, datafile headers, ...
 * @author Bram Van Rensbergen (http://fac.ppw.kuleuven.be/lep/concat/bram/)
 */
public abstract class Text {
	/**
	 * Font used in both instruction screens and the goodbye screen.
	 */
	public static final Font instructionFont = new Font("Serif", Font.PLAIN, 24);
	public static final Font cueFont = new Font("Serif", Font.PLAIN, 50);
	public static final Font prevAssoFont = new Font("Serif", Font.PLAIN, 30);
	public static final Font assoTextfieldFont = new Font("Serif", Font.PLAIN, 40);
	
	
	/**
	 * Headers i.e. first line of every datafile.
	 */
	public static final String headers = "trialNb\tgroupNb\tindexInGroup\tcue\tassociation\tassoNb\ttimeToFirstKeypress\ttimeToSubmission\tlist\tset\t"
			+ "load\toriginal_pattern\treproduced_pattern\tcorrect\thits\tmisses\tfalseAlarms";
	
	/**
	 * Displayed at the beginning of the experiment, after having asked the ss information. After this, the training phase starts.
	 */
	public static final String[] mainInstructions = {  
		"Welkom."+
		"<br><br>In dit experiment zal je twee dingen moeten doen. "
		+ "<br><br>Eerst zal je een 4 bij 4 veld te zien krijgen waarin een aantal punten staan. "
		+ "Jouw taak is om de locatie van de punten te memoriseren. Let op, het patroon wordt slechts voor een korte tijd aangeboden. "
		+ "Na enige tijd krijg je een leeg 4 bij 4 veld te zien waarin je vervolgens het originele patroon moet invullen. "
		+ "Dit doe je door met de muis op de velden te klikken waarin volgens jou een punt moet verschijnen. "
		+ "Als je per ongeluk een punt op de foute plaats hebt ingevuld, kan je dit terug verwijderen door er een tweede maal op te klikken. "
		+ "Het is de bedoeling dat je zo accuraat mogelijk werkt. "
//		,
//		
//				"Dus je krijgt eerst een patroon te zien dat je moet onthouden en later krijg je een leeg veld te zien waarin je het patroon moet invullen. "
//			+ "In de tijd daartussen, ga je een tweede opdracht krijgen.<br>"
//			+ "Hierbij ga je voor een korte tijd een woord in hoofdletters te zien krijgen (bvb. 'NAGEL') en vervolgens een letterreeks in kleine letters. "
//			+ "Deze letters vormen een bestaand Nederlands woord (bvb. 'otter') of een onbestaand woord (bvb. 'schuik'). "
//			+ "Jouw taak is om het woord in hoofdletters aandachtig te bekijken en daarna voor de letterreeks in kleine letters aan "
//			+ "te geven of het een bestaand woord of een onbestaand woord is."
//			+ "<br>Dit doe je door op het toetsenbord op de pijltjestoetsen te drukken. "
//			+ "De pijl naar links staat voor bestaand woord, de pijl naar rechts staat voor onbestaand woord. "
//			+ "Dus bij 'otter' zou je op de pijl naar links moeten drukken en bij 'schuik' zou je op de pijl naar rechts moeten drukken. "
//			+ "Het is de bedoeling dat je zo snel en accuraat mogelijk antwoordt. Dit wordt vijf keer herhaald. "
//			+ "Na de vijfde keer krijg je het lege veld te zien waarin je het puntenpatroon moet invullen.",
//		
//			"Zowel voor de geheugentaak als voor de woordtaak is het de bedoeling dat je zo <b>accuraat</b> mogelijk bent. "
//			+ "Voor de woordtaak is het daarnaast ook de bedoeling dat je zo <b>snel</b> mogelijk antwoordt. "
//			+ "Snelheid is niet belangrijk in de geheugentaak. Beide taken zijn even belangrijk."
//			+ "Eerst zal je de procedure kunnen oefenen in een oefenfase."
			}; 

	
	/**
	 * Displayed after the training phase, and before the actual experiment.
	 */
	public static final String firstBlockPostTrainingInstructions =
			"Nu begint het echte experiment.";
	
	public static final String interBlockInstructions = "Je kan nu even pauzeren. Klik op 'Klaar' als je aan het volgende deel wil beginnen.";
	
	
	/**
	 * Displayed at the end of the experiment.
	 */
	public static final String xpOverText = "" +
			"<br><br><br><br>Het experiment is afgelopen." +
			"<br><br>Bedankt voor je medewerking!" +
			"<br><br>Je mag het programma nu sluiten.";

	/**
	 * @return The current date.
	 */
	public static String getDate() {
		Calendar cal = Calendar.getInstance();
		String month = String.valueOf(cal.get(Calendar.MONTH) + 1);	
		String day = String.valueOf(cal.get(Calendar.DATE));
		return month + "_" + day;
	}
	
	/**
	 * @return The current time.
	 */
	public static String getTime() {	
		Calendar cal = Calendar.getInstance();
		String hour = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
		if(hour.length() == 1) hour = "0" + hour;
		String min = String.valueOf(cal.get(Calendar.MINUTE));
		if(min.length() == 1) min = "0" + min;
		String sec = String.valueOf(cal.get(Calendar.SECOND));
		if(sec.length() == 1) sec = "0" + sec;
		return hour + "_" + min + "_" + sec;	
	}
}