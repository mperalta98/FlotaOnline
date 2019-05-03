package com.company;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class UDPClient {

        private int portDesti;
        private String Nom, ipSrv;
        private InetAddress adrecaDesti;
        boolean estadoPartida = true;
        int numJ;

        Tablero tablero;
        Jugada jugada = new Jugada();
        Scanner scanner = new Scanner(System.in);

    public UDPClient(String ip, int port) {
            this.portDesti = port;
            ipSrv = ip;

            try {
                adrecaDesti = InetAddress.getByName(ipSrv);
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        public void setNom(String n) {
            Nom=n;
        }

        public void runClient() throws IOException {
            byte [] receivedData = new byte[1024];

            //Missatge de benvinguda
            System.out.println("Num jugador (1 o 2):");
            numJ = scanner.nextInt();




            System.out.println("Hola " + Nom + "! Comencem! ");
            //Bucle de joc
            do {
                System.out.println("Indica la posición X y la Y: ");
                int posX = scanner.nextInt();
                int posY = scanner.nextInt();

                jugada.setNumJ(numJ);
                jugada.setX(posX);
                jugada.setY(posY);

                ByteArrayOutputStream os = new ByteArrayOutputStream();
                ObjectOutputStream oos = null;
                try {
                    oos = new ObjectOutputStream(os);
                    oos.writeObject(jugada);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                byte[] missatge = os.toByteArray();

                //creació del paquet a enviar
                DatagramPacket packet = new DatagramPacket(missatge,missatge.length,adrecaDesti,portDesti);
                //creació d'un sòcol temporal amb el qual realitzar l'enviament
                DatagramSocket socket = new DatagramSocket();
                //Enviament del missatge
                System.out.println("ENVIANDO....");
                socket.send(packet);

                //creació del paquet per rebre les dades
                packet = new DatagramPacket(receivedData, 1024);
                //espera de les dades
                socket.setSoTimeout(5000);

                ByteArrayInputStream in = new ByteArrayInputStream(packet.getData());
                try {
                     socket.receive(packet);

                     ObjectInputStream ois = new ObjectInputStream(in);
                     tablero = (Tablero) ois.readObject();
                     if(tablero.responseCode == 3){
                         System.out.println("No es tu turno");
                     } else {
                         estadoPartida = tablero.estadoJuego();
                         tablero.imprimeTablero();
                     }

                //processament de les dades rebudes i obtenció de la resposta
                } catch (SocketTimeoutException e) {
                     System.out.println("El servidor no respòn: " + e.getMessage());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                socket.close();
            } while (tablero.estadoJuego());
        }

        public static void main(String[] args) {
            String jugador, ipSrv;

            //Demanem la ip del servidor i nom del jugador
            System.out.println("IP del servidor?");
            Scanner sc = new Scanner(System.in);
            ipSrv = "192.168.22.104"; //sc.next();
            System.out.println("Nom jugador:");
            jugador = sc.next();

            UDPClient client = new UDPClient(ipSrv, 5556);

            client.setNom(jugador);
            try {
                client.runClient();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Victoria");
        }
    }