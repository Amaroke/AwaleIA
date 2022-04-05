package awale.awaleia;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Author : Thibaud LIMBACH
 * Date : 30 / 03 / 2022
 */

public class Client {

    /**
     * Code client du jeu Awale
     * 1 er argument : ip
     * 2e argument : port
     * Les mouvements à envoyer doivent être entre 1 et 6
     */
    public static void main(String[] args) {

        final Socket clientSocket;
        final BufferedReader in;
        final PrintWriter out;
        boolean begin;

        try {
            clientSocket = new Socket(args[0], Integer.parseInt(args[1]));

            out = new PrintWriter(clientSocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            System.out.println("En attente du serveur");
            String message = in.readLine();

            System.out.println(message);

            message = in.readLine();
            begin = Boolean.parseBoolean(message);

            if(begin) {
                System.out.println("Tu commences");
            } else {
                System.out.println("L'autre joueur commence");
            }

            Partie b = new Partie();
            b.setTourDeLIA(true);

            if(!begin) {
                b.changementDeTour(); //à modifer avec fonction équivalente
                b.jouer(Integer.parseInt(in.readLine()) - 1); //à modifer avec fonction équivalente
            }

            while(!b.isTermine()) {
                int move = b.getIa().minimaxAvecElagageAlphaBeta(b, 7); //à modifer avec fonction équivalente
                b.jouer(move); //à modifer avec fonction équivalente
                if(move != -1) {
                    out.println(move + 1); //Envoyer un nombre entre 1 et 6
                }
                System.out.println(b.getPlateau().getNbGraines(0));
                out.flush();

                if(!b.isTermine()) {
                    move = Integer.parseInt(in.readLine()) - 1; //Nombre entre 1 et 6 reçus
                    b.jouer(move); //à modifer avec fonction équivalente
                }
            }

            out.println("-1");
            out.flush();

            out.close();
            in.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

