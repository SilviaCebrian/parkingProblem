/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingproblem;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author silvi
 */
public class Parking {

    private int totalPlaces;
    private ArrayList<Car> carStationaryInParking = new ArrayList<>();
    private int currentTakenPlaces;
    private int totalMoney;
    private int priceTicket;

    public Parking(int totalPlaces, Car[] totalCars) {
        this.totalPlaces = totalPlaces;
    }

    public synchronized boolean checkSlot() {
        if (currentTakenPlaces < totalPlaces) {
            currentTakenPlaces++;

            return true;
        }
        return false;
    }

    public synchronized void putInQueue() {
        carStationaryInParking.add((Car) Thread.currentThread());

    }

    public void exit() {
        synchronized (this) {
            while (!Thread.currentThread().equals(carStationaryInParking.get(0))) {
                try {
                    wait();
                } catch (InterruptedException ex) {
                    Logger.getLogger(Parking.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }
        //Waiting for employee
        System.out.println("\t\t\t"+Thread.currentThread().getName() + " request the ticket from employee");

        synchronized (this) {
            //wake up employee
            notifyAll();
            try {
                wait();
                carStationaryInParking.remove(0);
                currentTakenPlaces--;
                notifyAll();
            } catch (InterruptedException ex) {
                Logger.getLogger(Parking.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        System.out.println("\t\t\t"+Thread.currentThread().getName() + " pays " + priceTicket + "$ and leaves");
        System.out.println("----------------------------------------------------------------------------------");
        if (!hasMoreClientes()) {
            System.out.println(Colores.VERDE+"************************** \n Total sales today: " + totalMoney+ "$ \n**************************"+Colores.VERDE+"");
        }

    }

    public boolean hasMoreClientes() {
        if (carStationaryInParking.isEmpty() && currentTakenPlaces == 0) {
            return false;
        }
        return true;
    }

    private int calculatePrice(Car car) {
        long totalTime = System.currentTimeMillis() - car.getArrivingHour();
        if (totalTime < 100) {
            return 30;
        } else {
            return 50;
        }
    }

    public synchronized void checkClientsInQueue() {
        if (carStationaryInParking.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ex) {
                Logger.getLogger(Parking.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            priceTicket = calculatePrice(carStationaryInParking.get(0));
            try {
                wait(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(Parking.class.getName()).log(Level.SEVERE, null, ex);
            }
            //Quit the first car in queue
            totalMoney += priceTicket;
            notifyAll();
        }
    }
}
