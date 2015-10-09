/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continuouslgp;

import static continuouslgp.ContinuousLGP.getOperators;
import continuouslgp.Engine.Engine;
import continuouslgp.alu.controlflow.ControlFlow;
import continuouslgp.alu.controlflow.Goto;
import continuouslgp.alu.controlflow.IfLessSigmoidGeometric;
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
         ContinuousProgram prog = new ContinuousProgram(16,2,4,4,0,0,0);
        // public ContinuousMachine(ContinuousProgram p, RegisterProfile profile, int maxPCs, int numRegisters, int execType){
        Engine engine = new Engine(getOperators(),getControlFlow());
        RegisterProfile prof = new RegisterProfile(engine,prog.lines.length);
        ContinuousMachine machine = new ContinuousMachine(prog,prof,1,4,0);
        int evaluations = 0;
        float[][] trainingData = getTrainingData(20);
        float[][] testingData = getTrainingData(20);
        for(int iterations = 0; iterations<1000; iterations++){
            //System.err.println("epoch:" +iterations);
            if(evaluations>=50000*200) break;
            for(int pair = 0; pair<trainingData.length; pair++){
                int tries = 0;
               

                do{
                    float mutateAmount = .001f;
                    float mutateIncrement = .001f;
                    float currentErr = getError(trainingData[pair], machine);
                    if(currentErr<.0001) break;
                    if(Float.isInfinite(currentErr)){
                        machine.mutate(1);
                        //System.err.println("i mutated it good");
                        prog = new ContinuousProgram(16,2,4,4,0,0,0);
                        machine = new ContinuousMachine(prog,prof,1,4,0);
                        break;
                    }
                    machine.mutate(mutateAmount);
                    float newErr = getError(trainingData[pair],machine);
                    boolean fail = false;
                    float prevError = newErr;
                    float minChange = .99f;
                    evaluations++;
                    if(currentErr>.01f){
                       // minChange =.95f;
                    }
                    int tries2 = 0;
                    if(newErr<currentErr){
                    while( (!(newErr/currentErr >.80 && newErr/currentErr <minChange ))){
                        tries2++;
                        if(tries2%100==99){
                            System.err.println("tries2:" + tries2 + ", " + newErr/currentErr);
                        }
                        if(tries2>1000){
                            //fail = true;
                            System.err.println("tried too many times " + newErr/currentErr);
                            //machine.changeStrength(0);
                            break;
                        }
                        if(prevError < newErr){
                            //fail = true;
                            System.err.println("was about to be worse " +  newErr/currentErr);
                            machine.changeStrength(mutateAmount-mutateIncrement);
                            break;
                        }
                        if(Float.isInfinite(newErr)){
                            //System.err.println("error was infinite");
                            prog =  new ContinuousProgram(16,2,4,4,0,0,0);
                            machine = new ContinuousMachine(prog,prof,1,4,0);
                            fail = true;
                            
                            //machine.mutate(1);
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
                     //System.err.println("exit condition met:" + newErr/currentErr + " " + fail);
                    evaluations+=5;
                    }else{
                        fail = true;
                        machine.changeStrength(0);
                    }
                    if(!fail){
                         //System.err.println("exit condition met, fail false:" + newErr/currentErr + " " + fail);
                        float err1 = getTestingErr(machine,testingData);
                        if(Float.isInfinite(err1)){
                            err1 = -1;
                        }
                        float err2 = getTestingErr(machine, trainingData);
                        if(Float.isInfinite(err2)){
                            err2 = -1;
                        }
                        System.err.println(iterations + "," +evaluations +", " +(newErr/currentErr)+ ", " + mutateAmount + ", " + currentErr + ", " + err1 + ", "  + err2);
                        
                        //System.err.println(trainingData[pair][1]); 
                        //System.err.println("current err " + currentErr);
                        //System.err.println("change in eror10:" + newErr/currentErr);
                    }
                    if(!fail)
                        break;
                }while(tries<2);
            }
        }
        float sum = 0;
        for(int pair = 0; pair<trainingData.length; pair++){
            sum+= Math.sqrt(getError(testingData[pair],machine))/20;
        }
        machine.printProg();
        
        for(int i = 0; i<200; i++){
            float k = (float)(i+.01);
            float input = k*(9.0f/200.0f);
            System.err.println(input + " " + getOutput(machine,input));
        }
        //System.err.println(sum);
    }
    
    static float[][] getTrainingData(int number){
        float[][] data = new float[20][2];
        for(int i = 0; i<data.length; i++){
            data[i][0] = 1;//generator.nextFloat()*5;
            data[i][1] = (float)data[i][0]*5 +2 + data[i][0]*data[i][0]/2 ;
        }
        return data;
    }
    static float getTestingErr(ContinuousMachine machine, float[][] testingData){
        float sum = 0;
        for(int pair = 0; pair<testingData.length; pair++){
            sum+= Math.sqrt(getError(testingData[pair],machine))/20;
        }
        return sum;
        
    }
    static float getError(float[] inputOutputPair, ContinuousMachine machine){
       machine.hardRestart();
       machine.registers[0] = inputOutputPair[0];
       for(int i = 0; i<machine.program.lines.length; i++){
            machine.doStep();
            machine.registers[0] = inputOutputPair[0];
       }
       float answer = machine.registers[1];
       
       return (answer-inputOutputPair[1])*(answer-inputOutputPair[1]);
    }
    static float getOutput(ContinuousMachine machine, float input){
       machine.hardRestart();
       machine.registers[0] = input;
       for(int i = 0; i<machine.program.lines.length; i++){
            machine.doStep();
            machine.registers[0] = input;
       }
       float answer = machine.registers[1];
       
       return answer;
    }
     static ControlFlow[] getControlFlow(){
        ControlFlow cfs[] = new ControlFlow[1];
        cfs[0] = new IfLessSigmoidGeometric();
        //cfs[1] = new Goto();
        return cfs;
    }
}
