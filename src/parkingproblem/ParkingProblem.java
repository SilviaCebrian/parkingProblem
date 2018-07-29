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
public class ParkingProblem {

    private static final int NUMBEROFPLACES = 10;
    private static int numberOfCars = 20;
    private static Car[] cars = new Car[numberOfCars];
    private static Parking parking;
    private static Employee employee;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        parking = new Parking(NUMBEROFPLACES, cars);
        createCars();
        StartCars();
    }

    private static void createCars() {
        for (int i = 0; i < cars.length; i++) {
            cars[i] = new Car(parking, "Car:" + i);
        }
       employee = new Employee(parking, "Employee");
       employee.start();
        System.out.println(Colores.VERDE+"Silvia's Parking its open!");
        System.out.println(Colores.VERDE+"--------------------------\n");

    }

    private static void StartCars() {
        
        for (int i = 0; i < cars.length; i++) {
            cars[i].start();
        }
       
    }

}
