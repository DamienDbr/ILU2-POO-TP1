package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum,int nombreEtals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nombreEtals);
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
	//debut modifs
	private static class Marche{
		private Etal[] etals;
		private int nbEtals = etals.length; 
		private Marche(int nombreEtals) {
			etals = new Etal[nombreEtals];
		}
		void utiliserEtal(int indiceEtal,Gaulois vendeur,String produit,int nbProduit){
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}
		int trouverEtalLibre() {   //Pas précisé -> protection par défaut
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe()==false) 
					return i;
			}
			return -1;
		}
		Etal[] trouverEtals(String produit) {
			int nombreEtalsAvecProduit = 0;
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].contientProduit(produit)) {
					nombreEtalsAvecProduit+=1;
				}
			}
			int indiceTab = 0;
			Etal[] etalsAvecProduit = new Etal[nombreEtalsAvecProduit];
			for(int j=0;j<nbEtals;j++) {
				if(etals[j].contientProduit(produit))
					etalsAvecProduit[indiceTab] = etals[j];
					indiceTab+=1;
			}
			return etalsAvecProduit;
		}
		Etal trouverVendeur(Gaulois gaulois) {
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].getVendeur()==gaulois)
					return etals[i];
			}
			return null;
		}
		String afficherMarche(){
			int nbEtalVide = 0;
			String chaine = "";
			for(int i=0;i<nbEtals;i++) {
				if(etals[i].isEtalOccupe())
					chaine+=etals[i].afficherEtal();
				else 
					nbEtalVide +=1;
			}
			if(nbEtalVide>0) {
				chaine += "Il reste "+nbEtalVide+ " étals non utilisés dans le marché.\n";
			}
			return chaine;
		}
		//fin modifs
	}
}