/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parkingproblem;

/**
 *
 * @author silvi
 */
public class Employee extends Thread {

    private Parking parking;

    public Employee(Parking parking, String name) {
        super(name);
        this.parking = parking;
    }

    @Override
    public void run() {

        do {
            parking.checkClientsInQueue();
        } while (parking.hasMoreClientes());

    }

}
