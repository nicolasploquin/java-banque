package fr.eni.formation.demo;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Programme {

	public static void main(String[] args) {
		
		Scanner in = new Scanner(System.in);

		// - 1 -
		System.out.print("Prénom : ");
		String prenom = in.nextLine();
		
		System.out.print("Nom : ");
		String nom = in.nextLine();
		
		// String.format("Bonjour %s %s\n", prenom, nom)
		System.out.printf("Bonjour %s %s\n", prenom, nom);

		// - 2 -
		if (prenom.length() <= 5) {
			System.out.printf("Bonjour %s. %s\n", prenom.charAt(0), nom);
		} else {
			System.out.printf("Bonjour %s %s\n", prenom, nom);
		}

		// - 3a -
		System.out.println("Multiplication par 7");
		for (int i = 0; i <= 10; i++) {
			System.out.printf("%2s x 7 = %2s\n", i, i * 7);
		}

		// - 3b -

		boolean skip = true;
		
		// Génère un nombre aléatoire entre 1 et 10
		int nombre = new Random().nextInt(10) + 1;
		int val;
		do {
			// Lire le nombre entier saisi dans la console
			System.out.print("Proposez un nombre entre 1 et 10 : ");
			val = in.nextInt();
			
			if (val > nombre) {
				System.out.println(" Trop grand !");
			} else if (val < nombre) {
				System.out.println(" Trop petit !");
			}
	
		} while (val != nombre && !skip);
	
		System.out.printf("C'est gagné ! %s\n", val);

		// - 4 -
		int[] tab = { 6, 4, 8, 7, 2, 1, 10, 3, 9, 5 };

		int somme = 0;
		int minimum = Integer.MAX_VALUE;
		int posMin = -1;

		// Parcours chaque valeur
		for (int i = 0; i < tab.length; i++) {
			int valeur = tab[i];

			// Affiche la valeur
			System.out.print(valeur + " ");

			// Ajoute la valeur à la somme
			somme += valeur;

			// Teste si la valeur est plus petite que le minimum
			if (valeur < minimum) {
				minimum = valeur;
				posMin = i;
			}
		}
		System.out.println();

		System.out.printf("La somme des valeurs est %s.\n", somme);
		System.out.printf("La moyenne des valeurs est %.1f.\n", (double)somme / tab.length);
		System.out.printf("La plus petite valeur est %s, en position %s.\n", minimum, posMin);

		// - 5 -
		System.out.print("Prénom Nom : ");
		prenom = in.next();
		nom = in.next();
		System.out.printf("%s %s\n", prenom, nom);

		nom = nom.toUpperCase();

		// méthode 1
		// jeaN-mARc
		char premier = prenom.charAt(0);
		// String premier = prenom.substring(0,1);
		String reste = prenom.substring(1);
		prenom = Character.toUpperCase(premier) + reste.toLowerCase();
		
		// Jean-marc
		int posTiret = prenom.indexOf('-');
		if (posTiret != -1) {
			String debut = prenom.substring(0, posTiret + 1);
			char deuxieme = Character.toUpperCase(prenom.charAt(posTiret + 1));
			String fin = prenom.substring(posTiret + 2);
			prenom = debut + deuxieme + fin;
		}
		
		// méthode 2
		String[] prenoms = prenom.split("-");
		for (int i = 0; i < prenoms.length; i++) {
			prenoms[i] = 
					Character.toUpperCase(prenoms[i].charAt(0)) 
					+ prenoms[i].substring(1).toLowerCase();
		}
		prenom = String.join("-", prenoms);
		

		System.out.printf("%s %s\n", prenom, nom);
		
		in.close();
	}

	public static void main1(String[] args) {

		Scanner in = new Scanner(System.in);

		System.out.print("Saisir un prénom : ");
		String prenom = in.nextLine();

		System.out.print("Quel age as-tu ? ");
		int age = in.nextInt();
		in.nextLine();

		System.out.println(prenom + age);

		// Méthode 1
		int nombre = (int) Math.round(Math.floor(Math.random() * 10)) + 1;
		System.out.println(nombre);

		// Méthode 2
		System.out.println(new Random().nextInt(10) + 1);

		int[] tab = { 2, 5, 4, 8, 7, 9, 6, 3, 1, 0 };

		System.out.println(Arrays.toString(tab));
		
		in.close();
	}

	/**
	 * Méthode principale du programme. Montre les éléments de base de la syntaxe
	 * Java.
	 * 
	 * @param args Ce programme n'attend pas de paramètre
	 * 
	 */
	public static void main0(String[] args) {

		System.out.println("Mon premier programme Java !");

		float unNombre = 5.6f;

		byte unAutreNombre = 24;

		double unPetitNombre = unAutreNombre + unNombre;

		char car = 'À';

		System.out.println((int) car);
		System.out.println((char) 192);
		System.out.println((char) (4096 + 65));
		System.out.println(unPetitNombre);
		System.out.println(unNombre + unPetitNombre);

		int a = 2;
		int b = 3;

		System.out.println("a + b = " + (a + b));

		String prenom = "Marc";
		String nom = "MARC";
		// prenom = prenom + "-Olivier";

		prenom = prenom.toUpperCase();

		System.out.println(prenom);
		System.out.println(nom);

		System.out.println(prenom == nom);
		System.out.println(prenom.equals(nom));

		System.out.println(prenom.codePointAt(0));
		System.out.println(prenom.indexOf("R"));
		System.out.println(prenom.length());
		System.out.println(prenom.substring(2));
		System.out.println(prenom.substring(0, 3));

		System.out.println(Arrays.toString("02.13.45.67.89".split("\\.")));

		// b == 3
		System.out.println(b % 2 == 0 && b++ > 10);

		b -= 2;
		System.out.println(++b); // b = b + 1;

		int c = 10;

		System.out.println(c << 1);

		if (c > 10)
			System.out.println("c > 10");

		if (c < 10) {
			System.out.println("c < 10");
		}

		c = c > 10 ? c / 2 : c + 5;

	}

}
