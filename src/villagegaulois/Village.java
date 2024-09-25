package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	int nombreEtalsMArche;
	private Marche marche;
	

	public Village(String nom, int nbVillageoisMaximum, int nombreEtalsMarche) {
		this.nombreEtalsMArche = nombreEtalsMarche;
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nombreEtalsMarche);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef "
					+ chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom()
					+ " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+". \n");
		int etalLibre = marche.trouverEtalLibre();
		marche.utiliserEtal(etalLibre, vendeur, produit, nbProduit);
		int numeroEtal = etalLibre+1; //Car Numero different d'indice
		chaine.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" à l'étal n°"+numeroEtal+". \n");
		return chaine.toString();
	}
	
	
	
	private static class Marche{
		private Etal[] etals;
		int nombreEtals;
		
		private Marche(int nombreEtals) {
			//this.nombreEtalsMax = nombreEtalsMax;
			this.nombreEtals = nombreEtals;
			etals = new Etal[nombreEtals];
			
		}
		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit,int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		private int trouverEtalLibre() {
			for(int i=0;i<nombreEtals;i++) {
				if(etals[i].isEtalOccupe()==false)
					return i;
			}
			return -1;	
		}
		private Etal[] trouverEtals(String produit) {
			int nbEtalsAvecProduit=0;
			//Boucle pour trouver le nb de produits
			for(int i=0;i<nombreEtals;i++) {
				if(etals[i].contientProduit(produit))
					nbEtalsAvecProduit+=1;
			}
			Etal[] etalsOuOnVendProduit = new Etal[nbEtalsAvecProduit];
			int indiceTab = 0;
			//Boucle pour remplir le tableau
			for(int i=0;i<nombreEtals;i++) {
				if(etals[i].contientProduit(produit)) {
					etalsOuOnVendProduit[indiceTab] = etals[i];
					indiceTab+=1;
				}
			}
			return etalsOuOnVendProduit;
		}
		private Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<nombreEtals;i++) {
				if(etals[i].getVendeur()==gaulois)
					return etals[i];
			}
			return null;	
		}
		private String afficherMarche() {
			int nbEtalsVide=0;
			for(int i=0;i<nombreEtals;i++) {
				if(etals[i].isEtalOccupe()==false)
					nbEtalsVide+=1;
			}
			return "Il reste " + nbEtalsVide +" étals non utilisés dans le marché. \n";
			
		}
	}
}