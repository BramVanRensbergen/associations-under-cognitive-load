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
	public static final Font tooLateFont = new Font("Serif", Font.PLAIN, 60);
	public static final Font prevAssoFont = new Font("Serif", Font.PLAIN, 30);
	public static final Font assoTextfieldFont = new Font("Serif", Font.PLAIN, 40);
	
	
	/**
	 * Headers i.e. first line of every datafile.
	 */
	public static final String headers = "trialNb\tgroupNb\tindexInGroup\tcue\tassociation\tassoNb\ttimeToFirstKeypress\ttimeToSubmission\tlist\t"
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
		+ "Het is de bedoeling dat je zo accuraat mogelijk werkt. ",
		
		"Dus je krijgt eerst een patroon te zien dat je moet onthouden en later krijg je een leeg veld te zien waarin je het patroon moet invullen. "
		+ "In de tijd daartussen, ga je een tweede opdracht krijgen." 
		+ "<br><br>Hierbij gaat er gevraagd worden om <b>drie</b> associaties te geven bij een bepaald woord. Concreet zal je bovenaan een woord te zien krijgen met daaronder een leeg vakje. "
		+ "Het is de bedoeling dat je in dat vakje de <b>eerste</b> associatie schrijft die direct bij je opkomt als je dit woord leest. Hiervoor kan je gewoon het toetsenbord gebruiken en "
		+ "wanneer je de associatie hebt ingetypt, druk je op ENTER. Vervolgens zal er een nieuw leeg vakje verschijnen waarin je de <b>tweede</b> associatie kan invullen. Wanneer je dit hebt "
		+ "gedaan en opnieuw op ENTER hebt gedrukt, verschijnt er weer een leeg vakje voor de <b>derde</b> associatie. Druk je ten slotte nogmaals op ENTER verschijnt er een nieuw woord "
		+ "waarvoor je drie associaties moet geven." 
		+ "<br><br>Belangrijk: Geef enkel associaties voor het woord dat bovenaan getoond wordt (niet op basis van een vorig antwoord) en vermijd het gebruik van zinnen. "
		+ "Klik op de Onbekend Woord knop rechtsonder als je het woord niet kent en op de Geen associatie knop als je geen andere associaties vlot kan bedenken.",

		"Voor de geheugentaak is het de bedoeling dat je zo <b>accuraat</b> mogelijk bent. Voor de associatietaak is het daarnaast ook de bedoeling dat je zo <b>snel</b> mogelijk antwoordt, "
		+ "als je te lang wacht verschijnt de boodschap “Te traag!”. Snelheid is niet belangrijk in de geheugentaak. Beide taken zijn even belangrijk. Eerst zal je de procedure "
		+ "kunnen oefenen in een oefenfase." 		

			}; 

	
	/**
	 * Displayed after the training phase, and before the actual experiment.
	 */
	public static final String postTrainingInstructions =
			"Nu begint het echte experiment. Het experiment bestaat uit drie delen, tussenin heb je de mogelijkheid om te pauzeren. Mocht je nog vragen hebben, roep dan de proefleider.";
	
	public static final String interBlockInstructions = "Je kan nu even pauzeren. Klik op 'Klaar' als je aan het volgende deel wil beginnen.";
	public static final String interBlockInstructionsLast = "Je kan nu even pauzeren. Klik op 'Klaar' als je aan het laatste deel wil beginnen.";
	
	
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