package com.company;
//TODO: Enviar la información de los tableros al servidor UDP.
//TODO:Los clientes tienen que enviar las posiciones, el servidor recibirlas y devolver el tablero con los nuevos valores.
import java.io.*;
import java.net.*;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;

public class UDPServer {


    DatagramSocket socket;
    int port, fi;
    Tablero tablero;
    Barco barco;
    Jugada jugada;
    boolean acabat;
    int numJugadores;

    public UDPServer(int port) throws IOException {
        try {
            socket = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.port = port;
        barco = new Barco();
        tablero = new Tablero(5, '-', 'O');
        acabat = false;
        fi = -1;
    }

    public void runServer() throws IOException{
        byte [] receivingData = new byte[1024];
        byte [] sendingData;
        InetAddress clientIP;
        int clientPort;

        barco.imprimeBarco(tablero);
        System.out.println("Barcos añadidos");

        //el servidor atén el port indefinidament
        while(!acabat){

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
            System.out.println("jugada:" + jugada.Nom + " " + jugada.getX());
            System.out.println("jugada:" + jugada.Nom + " " + jugada.getY());
            //Si no existeix el jugador a la llista és un jugador nou
            //per tant l'afegim i inicialitzem les tirades
            barco.disparar(tablero,jugada.x,jugada.y);
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
            oos.writeObject(tablero);
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