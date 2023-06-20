package Structures;

import Modele.Coup;
import Modele.Niveau;

import java.util.ArrayList;

public class ArbrePossibilite {

    static final int VIDE = 0;
    static final int MUR = 1;
    static final int POUSSEUR = 2;
    static final int CAISSE = 4;
    static final int BUT = 8;
    public Noeud racine;


    public ArbrePossibilite(Niveau niveau) {
        this.racine = new Noeud(null, 0, null, null, 0, 0);
    }

    public static class Noeud implements Comparable<Noeud> {
        public int depth;
        public Noeud noeud_pere = null;
        public int priorite = 0;
        public ArrayList<Noeud> noeudsFils = new ArrayList<>();
        public Coup coup;

        public int[][] priority_map;
        public int player_x = 0;
        public int player_y = 0;

        public static class Coords {
            public int x;
            public int y;
            public Coords(int x, int player_x) {
                this.x = x;
                this.y = y;
            }
        }


        public Noeud(Coup coup, int depth, Noeud noeud_pere, int[][] priority_map_save, int player_x, int player_y) {
            this.coup = coup;
            this.depth = depth;
            this.noeud_pere = noeud_pere;
            this.player_x = player_x;
            this.player_y = player_y;

            if (coup != null && (coup.caisse() != null || priority_map_save == null)) {
                this.player_x = coup.niv.colonnePousseur();
                this.player_y = coup.niv.lignePousseur();

                // MAP: -2 A visiter    -1 WALL     0 BOX       restes cases
                // un nombre inférieur à un autre a la priorité
                int[][] priority_map = new int[coup.niv.lignes()][coup.niv.colonnes()];

                for (int i = 1; i < coup.niv.lignes() - 1; i++) {
                    for (int j = 1; j < coup.niv.colonnes() - 1; j++) {
                        priority_map[i][j] = -2;
                        if (coup.niv.aMur(i, j)) {
                            priority_map[i][j] = -1;
                        }
                        if (coup.niv.aCaisse(i, j)) {
                            priority_map[i][j] = 0;
                        }
                    }
                }

                while (priority_map[coup.niv.lignePousseur()][coup.niv.colonnePousseur()] == -2) {
                    for (int i = 1; i < coup.niv.lignes() - 1; i++) {
                        for (int j = 1; j < coup.niv.colonnes() - 1; j++) {
                            if (priority_map[i][j] == -2) {
                                if ((priority_map[i + 1][j] > -1 || priority_map[i][j + 1] > -1 || priority_map[i - 1][j] > -1 || priority_map[i][j - 1] > -1)) {

                                    int priority_value = priority_map[i + 1][j];
                                    if (priority_value < priority_map[i][j + 1]) {
                                        priority_value = priority_map[i][j + 1];
                                    }
                                    if (priority_value < priority_map[i - 1][j]) {
                                        priority_value = priority_map[i - 1][j];
                                    }
                                    if (priority_value < priority_map[i][j - 1]) {
                                        priority_value = priority_map[i][j - 1];
                                    }

                                    priority_map[i][j] = priority_value + 1;
                                }
                            }
                        }
                    }
                }


                this.priority_map = priority_map;
                this.priorite = priority_map[coup.niv.lignePousseur()][coup.niv.colonnePousseur()];

                for (int i = 1; i < coup.niv.lignes() - 1; i++) {
                    for (int j = 1; j < coup.niv.colonnes() - 1; j++) {
                        if (coup.niv.aCaisse(i, j) && coup.niv.aBut(i, j)) {
                            this.priorite /= 3;
                        }
                    }
                }
            } else {
                if (coup == null) {
                    this.priorite = 0;
                } else {
                    this.priorite = priority_map_save[coup.niv.lignePousseur()][coup.niv.colonnePousseur()];
                    this.priority_map = priority_map_save;
                }
            }

            //displayMap();
        }

        public Noeud() {

        }

        @Override
        public int compareTo(Noeud o) {
            if (this.priorite > o.priorite) {
                return 1;
            } else if (this.priorite < o.priorite) {
                return -1;
            } else {
                return 0;
            }
        }

        public void displayMap() {
            for (int i = 0; i < priority_map.length; i++) {
                for (int j = 0; j < priority_map[0].length; j++) {
                    System.out.print(priority_map[i][j]);
                }
                System.out.println();
            }
        }
    }
}