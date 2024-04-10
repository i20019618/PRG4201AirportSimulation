/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package airportsimulation2;
import java.util.List;
/**
 *
 * @author user
 */
public class Airplane extends Thread {
    private Runway runway;
    private String operation;
    //private List<Runway> allRunways;
    
    public Airplane(Runway runway, String name, String operation){
        super(name);
        this.runway = runway;
        this.operation = operation;
        //this.allRunways = allRunways;
    }
    
    public String getOperation(){
        return operation;
    }
    
    public Runway getRunway(){
        return runway;
    }
    
    @Override
    public void run(){
        try{
            if(operation.equals("landing")){
                runway.land(getName());
            }else if(operation.equals("takeoff")){
                runway.takeOff(getName());
            }
        }catch(InterruptedException e){
            e.printStackTrace();
    }
        
        /*
        try{
            if(operation.equals("landing")||operation.equals("emergency")){
                runway.land(getName(), allRunways);
            }else if(operation.equals("takeoff")){
                runway.takeOff(getName(), allRunways);
            }
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        */
    }
}
