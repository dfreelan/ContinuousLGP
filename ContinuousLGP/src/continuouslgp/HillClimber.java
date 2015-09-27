/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continuouslgp;

import static continuouslgp.ContinuousLGP.getOperators;
import continuouslgp.Engine.Engine;
import continuouslgp.machine.ContinuousMachine;
import continuouslgp.machine.RegisterProfile;
import continuouslgp.program.ContinuousProgram;
import java.util.Random;

/**
 *
 * @author dfreelan
 */
public class HillClimber {
    static Random generator = new Random();
    public static void main(String[] args){
         ContinuousProgram prog = new ContinuousProgram(5,4,7,7,0,0,0);
        // public ContinuousMachine(ContinuousProgram p, RegisterProfile profile, int maxPCs, int numRegisters, int execType){
        Engine engine = new Engine(getOperators(),null);
        RegisterProfile prof = new RegisterProfile(engine);
        ContinuousMachine machine = new ContinuousMachine(prog,prof,1,7,0);
        
        float[][] trainingData = getTrainingData(20);
        float[][] testingData = getTrainingData(20);
        for(int iterations = 0; iterations<1000; iterations++){
            System.err.println("epoch:" +iterations);
            for(int pair = 0; pair<trainingData.length; pair++){
                int tries = 0;
               

                do{
                    float mutateAmount = .0001f;
                    float mutateIncrement = .00001f;
                    float currentErr = getError(trainingData[pair], machine);
                    if(currentErr<.0001) break;
                    if(Float.isInfinite(currentErr)){
                            machine.mutate(1);
                            System.err.println("i mutated it good");
                            break;
                        }
                    machine.mutate(mutateAmount);
                    float newErr = getError(trainingData[pair],machine);
                    boolean fail = false;
                    float prevError = newErr;
                    float minChange = .98f;
                    if(currentErr>.01f){
                       // minChange =.95f;
                    }
                    while(!(newErr/currentErr >.90 && newErr/currentErr <minChange || newErr/currentErr<.1)){
                         
                        if(prevError < newErr){
                            fail = true;
                            //System.err.println("worse in a different way");
                            machine.changeStrength(0);
                            break;
                        }
                        if(Float.isInfinite(newErr)){
                            System.err.println("error was infinite");
                            fail = true;
                            machine.changeStrength(0);
                            machine.mutate(1);
                            break;
                        }
                        if(newErr/currentErr<1){
                           mutateAmount+=mutateIncrement;
                           machine.changeStrength(mutateAmount);
                           //System.err.println("getting better:" + newErr/currentErr);
                        }else{
                           fail = true;
                           //System.err.println("was getting worse");
                           machine.changeStrength(0);
                           break;
                        }
                        prevError = newErr;
                        newErr = getError(trainingData[pair],machine);
                        
                    }
                    if(!fail){
                        System.err.println(trainingData[pair][1]);
                        System.err.println("current err " + currentErr);
                        System.err.println("change in eror5:" + newErr/currentErr);
                    }
                    if(!fail)
                        break;
                }while(tries<1000);
            }
        }
        float sum = 0;
        for(int pair = 0; pair<trainingData.length; pair++){
            sum+= Math.sqrt(getError(testingData[pair],machine))/20;
        }
        System.err.println(sum);
    }
    static float[][] getTrainingData(int number){
        float[][] data = new float[20][2];
        for(int i = 0; i<data.length; i++){
            data[i][0] = generator.nextFloat()+1;
            data[i][1] = (float)Math.sqrt(data[i][0]);
        }
        return data;
    }
    static float getError(float[] inputOutputPair, ContinuousMachine machine){
       machine.hardRestart();
        machine.pcs[0].registers[0] = inputOutputPair[0];
       for(int i = 0; i<100; i++)
            machine.doStep();
       float answer = machine.pcs[0].registers[3];
       
       return (answer-inputOutputPair[1])*(answer-inputOutputPair[1]);
    }
}
