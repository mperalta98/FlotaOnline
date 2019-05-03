package com.company;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.util.Scanner;

public class UDPClient {

        private int portDesti;
        private int result;
        private String Nom, ipSrv;
        private int intents;
        private InetAddress adrecaDesti;

        Tablero tablero;
        Jugada jugada = new Jugada();
        Scanner scanner = new Scanner(System.in);

    public UDPClient(String ip, int port) {
            this.portDesti = port;
            result = -1;
            intents = 0;
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

        public int getIntents () {
            return intents;
        }

        public void runClient() throws IOException {
            byte [] receivedData = new byte[1024];

            //Missatge de benvinguda
            System.out.println("Hola " + Nom + "! Comencem!\n Indica la posición X y la Y: ");

            //Bucle de joc
            while(true) {

                int posX = scanner.nextInt();
//                scanner.nextLine();

                int posY = scanner.nextInt();
//                scanner.nextLine();

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
                    tablero.imprimeTablero();

                    //processament de les dades rebudes i obtenció de la resposta
//                    result = getDataToRequest(packet.getData(), packet.getLength());
                }catch(SocketTimeoutException e) {
                    System.out.println("El servidor no respòn: " + e.getMessage());
                    result=-2;
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                socket.close();
            }


        }

//        private int getDataToRequest(byte[] data, int length) {
//            int nombre = ByteBuffer.wrap(data).getInt();
//            if(nombre==0) System.out.println("Correcte");
//            else if (nombre==1) System.out.println("Més petit");
//            else System.out.println("Més gran");
//            intents++;

//            return nombre;
//        }

        public int getResult() {
            return result;
        }

        public void setResult(int result) {
            this.result = result;
        }

        public static void main(String[] args) {
            String jugador, ipSrv;

            //Demanem la ip del servidor i nom del jugador
            System.out.println("IP del servidor?");
            Scanner sip = new Scanner(System.in);
            ipSrv = "192.168.22.104"; //sip.next();
            System.out.println("Nom jugador:");
            jugador = "yo"; //sip.next();

            UDPClient client = new UDPClient(ipSrv, 5556);

            client.setNom(jugador);
            try {
                client.runClient();
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(client.getResult() == 0) {
                System.out.println("Fi, ho has aconseguit amb "+ client.getIntents() +" intents");
            } else {
                System.out.println("Has perdut");
            }

        }

    }