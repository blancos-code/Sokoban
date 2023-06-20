package Modele;

import Global.Configuration;
import Structures.*;

import java.util.*;


public class IAAssistance extends IA {

    public static class AbstractLevel {
        int key;
        int[][] map;
        public AbstractLevel(int[][] map) {
            this.key = Arrays.deepHashCode(map);
            this.map = map.clone();
        }

    }
    int loading_value = 0;
    int depth = 3;


    public IAAssistance() {

    }


    @Override
    public Sequence<Coup> joue() {

        Runtime.getRuntime().gc();

        ArbrePossibilite arbre = new ArbrePossibilite(niveau);
        Sequence<Coup> sequence = Configuration.nouvelleSequence();



        ArbrePossibilite.Noeud racine = arbre.racine;
        ArbrePossibilite.Noeud noeudParcours;
        noeudParcours = chercherChemin(racine, niveau);
        if (noeudParcours == null) {

            return sequence;
        };

        while (noeudParcours != null) {
            if (noeudParcours.noeudsFils.isEmpty()) {
                noeudParcours = null;
            } else {
                noeudParcours = noeudParcours.noeudsFils.get(0);
                sequence.insereQueue(noeudParcours.coup);
            }
        }
        return sequence;
    }

    public ArbrePossibilite.Noeud chercherChemin(ArbrePossibilite.Noeud noeud, Niveau niveau_courant) {

        FAPListe<ArbrePossibilite.Noeud> noeuds = new FAPListe<>();

        HashMap<String, int[][]> niveau_enregistres = new HashMap<>();

        noeuds.insere(noeud);


        Coup coup1 = null;
        Coup coup2 = null;
        Coup coup3 = null;
        Coup coup4 = null;



        while (!noeuds.estVide()) {

            ArbrePossibilite.Noeud noeud1 = noeuds.extrait();
            while (noeud1.depth >= 150) {
                noeud1 = noeuds.extrait();
            }

            if (noeud1 == noeud) {
                coup1 = niveau_courant.clone().deplace(1, 0);
                coup2 = niveau_courant.clone().deplace(0, 1);
                coup3 = niveau_courant.clone().deplace(-1, 0);
                coup4 = niveau_courant.clone().deplace(0, -1);

            } else {

                // Bas
                coup1 = noeud1.coup.niv.clone().deplace(1, 0);

                // Droite
                coup2 = noeud1.coup.niv.clone().deplace(0, 1);


                // Haut
                coup3 = noeud1.coup.niv.clone().deplace(-1, 0);

                // Gauche
                coup4 = noeud1.coup.niv.clone().deplace(0, -1);
            }



            boolean deleteNoeud = true;


            ////////////////////////////////////////////////
            /// COUP 1
            ///////////////////////////////////////////////
            if (coup1 != null) {
                if (!isThereAStuckBox(coup1.niv)) {
                    boolean sameMap = false;
                    String level_string = "";
                    for (int i = 0; i < coup1.niv.lignes(); i++) {
                        for (int j = 0; j < coup1.niv.colonnes(); j++) {
                            level_string += String.valueOf(coup1.niv.cases[i][j]);
                        }
                    }
                    level_string = String.valueOf(level_string.hashCode());


                    if (niveau_enregistres.containsKey(level_string)) {
                        if (isSameMap(coup1.niv.cases, niveau_enregistres.get(level_string), coup1.niv.colonnes(),  coup1.niv.lignes())) {
                            sameMap = true;
                        }
                    }
                    if (!sameMap) {
                        niveau_enregistres.put(level_string, coup1.niv.cases);



                        ArbrePossibilite.Noeud noeudFils = new ArbrePossibilite.Noeud(coup1, noeud1.depth + 1, noeud1, noeud1.priority_map, noeud1.player_x, noeud1.player_y);

                        noeud1.noeudsFils.add(noeudFils);
                        noeuds.insere(noeudFils);


                        if (coup1.niv.estTermine()) {
                            return clearTree(noeudFils);
                        }
                        deleteNoeud = false;
                    }
                }
            }

            ////////////////////////////////////////////////
            /// COUP 2
            ///////////////////////////////////////////////
            if (coup2 != null) {
                if (!isThereAStuckBox(coup2.niv)) {
                    boolean sameMap = false;
                    String level_string = "";
                    for (int i = 0; i < coup2.niv.lignes(); i++) {
                        for (int j = 0; j < coup2.niv.colonnes(); j++) {
                            level_string += String.valueOf(coup2.niv.cases[i][j]);
                        }
                    }
                    level_string = String.valueOf(level_string.hashCode());


                    if (niveau_enregistres.containsKey(level_string)) {

                        if (isSameMap(coup2.niv.cases, niveau_enregistres.get(level_string), coup2.niv.colonnes(),  coup2.niv.lignes())) {
                            sameMap = true;
                        }
                    }
                    if (!sameMap) {
                        niveau_enregistres.put(level_string, coup2.niv.cases);



                        ArbrePossibilite.Noeud noeudFils = new ArbrePossibilite.Noeud(coup2, noeud1.depth + 1, noeud1, noeud1.priority_map, noeud1.player_x, noeud1.player_y);

                        noeud1.noeudsFils.add(noeudFils);
                        noeuds.insere(noeudFils);


                        if (coup2.niv.estTermine()) {
                            return clearTree(noeudFils);
                        }
                        deleteNoeud = false;
                    }
                }
            }

            ////////////////////////////////////////////////
            /// COUP 3
            ///////////////////////////////////////////////
            if (coup3 != null) {
                if (!isThereAStuckBox(coup3.niv)) {
                    boolean sameMap = false;
                    String level_string = "";
                    for (int i = 0; i < coup3.niv.lignes(); i++) {
                        for (int j = 0; j < coup3.niv.colonnes(); j++) {
                            level_string += String.valueOf(coup3.niv.cases[i][j]);
                        }
                    }
                    level_string = String.valueOf(level_string.hashCode());


                    if (niveau_enregistres.containsKey(level_string)) {

                        if (isSameMap(coup3.niv.cases, niveau_enregistres.get(level_string), coup3.niv.colonnes(),  coup3.niv.lignes())) {
                            sameMap = true;
                        }
                    }
                    if (!sameMap) {
                        niveau_enregistres.put(level_string, coup3.niv.cases);



                        ArbrePossibilite.Noeud noeudFils = new ArbrePossibilite.Noeud(coup3, noeud1.depth + 1, noeud1, noeud1.priority_map, noeud1.player_x, noeud1.player_y);

                        noeud1.noeudsFils.add(noeudFils);
                        noeuds.insere(noeudFils);

                        if (coup3.niv.estTermine()) {
                            return clearTree(noeudFils);
                        }
                        deleteNoeud = false;
                    }
                }
            }


            ////////////////////////////////////////////////
            /// COUP 4
            ///////////////////////////////////////////////
            if (coup4 != null) {
                if (!isThereAStuckBox(coup4.niv)) {
                    boolean sameMap = false;
                    String level_string = "";
                    for (int i = 0; i < coup4.niv.lignes(); i++) {
                        for (int j = 0; j < coup4.niv.colonnes(); j++) {
                            level_string += String.valueOf(coup4.niv.cases[i][j]);
                        }
                    }
                    level_string = String.valueOf(level_string.hashCode());


                    if (niveau_enregistres.containsKey(level_string)) {
                        if (isSameMap(coup4.niv.cases, niveau_enregistres.get(level_string), coup4.niv.colonnes(),  coup4.niv.lignes())) {
                            sameMap = true;
                        }
                    }
                    if (!sameMap) {
                        niveau_enregistres.put(level_string, coup4.niv.cases);



                        ArbrePossibilite.Noeud noeudFils = new ArbrePossibilite.Noeud(coup4, noeud1.depth + 1, noeud1, noeud1.priority_map, noeud1.player_x, noeud1.player_y);

                        noeud1.noeudsFils.add(noeudFils);

                        noeuds.insere(noeudFils);

                        if (coup4.niv.estTermine()) {
                            return clearTree(noeudFils);
                        }
                        deleteNoeud = false;
                    }
                }
            }

            if (deleteNoeud) {

                ArbrePossibilite.Noeud noeudASupprimer = noeud1;

                while (noeudASupprimer.noeudsFils.isEmpty() && noeudASupprimer != noeud) {
                    ArbrePossibilite.Noeud tempNoeud = noeudASupprimer.noeud_pere;
                    tempNoeud.noeudsFils.remove(noeudASupprimer);
                    noeudASupprimer = tempNoeud;
                }

            }
        }
        return null;
    }

    public boolean isThereAStuckBox(Niveau niveau) {
        for (int i = 0; i < niveau.l; i++) {
            for (int j = 0; j < niveau.c; j++) {
                if (niveau.aCaisse(i, j)) {
                    if (!niveau.aBut(i, j)) {
                        if (niveau.aMur(i, j - 1) || niveau.aMur(i, j + 1)) {
                            if (niveau.aMur(i - 1, j) || niveau.aMur(i - 1, j)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean isSameMap(int[][] map1, int[][] map2, int width, int height) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (map1[i][j] != map2[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    public ArbrePossibilite.Noeud clearTree(ArbrePossibilite.Noeud noeud) {
        ArbrePossibilite.Noeud noeudPere = noeud.noeud_pere;
        ArbrePossibilite.Noeud noeudFils = noeud;

        while (noeudPere != null) {
            noeudPere.noeudsFils.clear();
            noeudPere.noeudsFils.add(noeudFils);
            noeudFils = noeudPere;
            noeudPere = noeudPere.noeud_pere;
        }

        if (noeudFils.noeudsFils.isEmpty()) {
            return noeudFils;
        }
        ArbrePossibilite.Noeud noeudFilstemp = noeudFils.noeudsFils.get(0);
        while (noeudFilstemp != null) {
            if (noeudFilstemp.noeudsFils.isEmpty()) {
                return noeudFils;
            } else {
                noeudFilstemp = null;
            }
        }


        return noeudFils;
    }
}
