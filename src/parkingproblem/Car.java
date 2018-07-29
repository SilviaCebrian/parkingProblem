/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingproblem;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author silvi
 */
public class Car extends Thread {

    private Parking parking;
    private long arrivingHour;
    private Random r = new Random();

    public Car(Parking parking, String name) {
        super(name);
        this.parking = parking;
    }

    @Override
    public void run() {
        try {
            //Random time to go at parking
            sleep(randomTime());
            //Check if has places in a parking
            if(parking.checkSlot()){
                 System.out.println(Colores.AZUL+"*** "+this.getName()+" enter in a parking");
                arrivingHour=System.currentTimeMillis();
                int timeToSleep=randomTime();
                sleep(timeToSleep);
                System.out.println(Colores.MAGENTA+"\t\t\t"+this.getName()+" waiting for: " +timeToSleep+"ms to be served by the employee.");
                parking.putInQueue();
                parking.exit();
            
            }else{
                System.out.println(Colores.RED+this.getName()+" leaves the parking because not has a empty spot");
            }
        } catch (InterruptedException ex) {
            Logger.getLogger(Car.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private int randomTime() {
        return r.nextInt((10000 - 1000) + 1) + 1000;
    }

    public long getArrivingHour() {
        return arrivingHour;
    }
    
}
