package com.company;
import java.io.*;
import java.net.*;

public class UDPServer {

    DatagramSocket socket;
    int port;
    Tablero tablero, tablero2;
    Barco barco;

    int turno = -1;

    public UDPServer(int port) throws IOException {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.port = port;
        barco = new Barco();
        tablero = new Tablero(5, '-', 'O');
        tablero2 = new Tablero(5, '-', 'O');
    }

    public void runServer() throws IOException{
        byte [] receivingData = new byte[1024];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        barco.imprimeBarco(tablero, tablero2);
        System.out.println("Barcos añadidos");

        //el servidor atén el port indefinidament
        while(barco.derrota){

            //creació del paquet per rebre les dades
            DatagramPacket packet = new DatagramPacket(receivingData, 1024);
            //espera de les dades
            socket.receive(packet);
            //processament de les dades rebudes i obtenció de la resposta
            sendingData = processData(packet.getData(), packet.getLength());
            //obtenció de l'adreça del client
            clientIP = packet.getAddress();
            //obtenció del port del client
            clientPort = packet.getPort();
            //creació del paquet per enviar la resposta
            packet = new DatagramPacket(sendingData, sendingData.length,
                    clientIP, clientPort);
            //enviament de la resposta
            System.out.println("ENVIANDO...");
            socket.send(packet);
        }
        socket.close();
    }

    private byte[] processData(byte[] data, int length) {
        Jugada jugada = null;
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try {
            ObjectInputStream ois = new ObjectInputStream(in);
            jugada = (Jugada) ois.readObject();

            if(turno == -1 || turno == jugada.numJ) {
                System.out.println("jugada:" + jugada.Nom + " " + jugada.getX());
                System.out.println("jugada:" + jugada.Nom + " " + jugada.getY());
                //Si no existeix el jugador a la llista és un jugador nou
                //per tant l'afegim i inicialitzem les tirades
                if (jugada.numJ == 1) {
                    barco.disparar(tablero2, jugada.x, jugada.y);
                    turno = 2;
                    tablero2.responseCode = 1;
                } else {
                    barco.disparar(tablero, jugada.x, jugada.y);
                    turno = 1;
                    tablero.responseCode = 1;
                }
            } else {
                if(jugada.numJ == 1) {
                    tablero2.responseCode = 3;
                } else {
                    tablero.responseCode = 3;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        //La resposta és el tauler amb les dades de tots els jugadors
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(os);
            if(jugada.numJ == 1) {
                oos.writeObject(tablero2);
            } else {
                oos.writeObject(tablero);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] resposta = os.toByteArray();
        return resposta;
    }

    public static void main(String[] args) throws SocketException, IOException {
        UDPServer server = new UDPServer(5556);

        try {
            server.runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Fi Servidor");
    }
}