/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continuouslgp;



import continuouslgp.Engine.Engine;
import continuouslgp.alu.controlflow.ControlFlow;
import continuouslgp.alu.controlflow.Goto;
import continuouslgp.alu.controlflow.IfLessSigmoidGeometric;
import continuouslgp.alu.operator.Add;
import continuouslgp.alu.operator.Divide;
import continuouslgp.alu.operator.Multiply;
import continuouslgp.alu.operator.NoOp;
import continuouslgp.alu.operator.Operator;
import continuouslgp.alu.operator.Subtract;
import continuouslgp.machine.ContinuousMachine;
import continuouslgp.machine.RegisterProfile;
import continuouslgp.program.ContinuousProgram;
import java.util.Random;

/**
 *
 * @author dfreelan
 */
public class TournamentSelect {
    public static void main(String[] args){
       //for(int i = 0; i<100; i++){
       float[] primes = new float[1000000];
       getPrimes(primes);
       int generations = 100;
       int numInds = 200;
       Random generator = new Random();
       ContinuousMachine population[] = new ContinuousMachine[200];
       float[] fitness = new float[200];
       for(int i = 0; i<population.length; i++){
           population[i] = getIndividual();
       }
       for(int i = 0; i<generations; i++){
           float bestErr = Float.MAX_VALUE;
           for(int a = 0; a<population.length; a++){
               int index = generator.nextInt(primes.length);
               float input = (float) index;
               float desiredOut = primes[index];
               fitness[a] = Math.abs(getOutput(population[a],input) - desiredOut);
               if(bestErr > fitness[a])
                   bestErr = fitness[a];
           }
           System.err.println("best err:" + bestErr);
           ContinuousMachine[] newPop = new ContinuousMachine[200];
           
           for(int a = 0; a<newPop.length; a++){
               int index = generator.nextInt(newPop.length);
               int index2 = generator.nextInt(newPop.length);
               
               if(fitness[index2] < fitness[index] )
                   index = index2;
               
               newPop[a] = cloneMachine(population[index]);
               newPop[a].mutate(.01f);
           }
           
           population = newPop;
       }
          
            ContinuousMachine machine = getIndividual();
            
            //machine.printProg();
            machine.hardRestart();
            machine.pcs[0].registers[0] = 2.0f;
            
            for(float k = 0; k<10;k+=.1f){
               System.err.println(getOutput(machine,k));
            }
            
            // System.out.println(machine.pcs[1].registers[1]);
       //}
        
       
    }
    public static ContinuousMachine cloneMachine(ContinuousMachine proto){
        ContinuousMachine machine = getIndividual();
        for(int i = 0; i<machine.program.lines.length; i++){
            for(int a  = 0; a<machine.program.lines[i].getLineValues().length; a++){
                machine.program.lines[i].setLineValues(cloneArr(proto.program.lines[i].getLineValues()));
            }
            
            
        }
        return machine;
    }
    public static float[][] cloneArr(float[][] arr){
        float clone[][] = new float[3][];
        for(int i = 0; i<arr.length; i++){
            clone[i] = arr[i].clone();
        }
        return clone;
    }
    //machine.program.lines[0].getLineValues();
    public static ContinuousMachine getIndividual(){
            ContinuousProgram prog = new ContinuousProgram(100,5,4,4,0,0,0);

            // public ContinuousMachine(ContinuousProgram p, RegisterProfile profile, int maxPCs, int numRegisters, int execType){
            Engine engine = new Engine(getOperators(),getControlFlow());
            RegisterProfile prof = new RegisterProfile(engine,prog.lines.length);
            ContinuousMachine machine = new ContinuousMachine(prog,prof,1,5,0);
            return machine;

    }
     public static float getPrime(int range){
        int num = 0;
        Random rand = new Random(); // generate a random number
        num = rand.nextInt(range) + 1;
        while (!isPrime(num)) {    
            num = rand.nextInt(range) + 1;
        }
        return (float) num;
    }
     public static void getPrimes(float[] list){
         int primeCount = 2;
         for(int i = 0; i<list.length; i++){
             while(!isPrime(primeCount)){
                 primeCount++;
             }
             list[i] = (float)primeCount;
             primeCount++;
         }
     }
    /**
     * Checks to see if the requested value is prime.
     */
    
     
    private static boolean isPrime(int inputNum){
        if (inputNum <= 3 || inputNum % 2 == 0) 
            return inputNum == 2 || inputNum == 3; //this returns false if number is <=1 & true if number = 2 or 3
        int divisor = 3;
        while ((divisor <= Math.sqrt(inputNum)) && (inputNum % divisor != 0)) 
            divisor += 2; //iterates through all possible divisors
        return inputNum % divisor != 0; //returns true/false
    }
    static float getOutput(ContinuousMachine machine, float input){
       machine.hardRestart();
       machine.registers[0] = input;
       for(int i = 0; i<200; i++){
           machine.doStep();
           machine.registers[0] = input;
             
       }
       float answer = machine.registers[1];
       
       return answer;
    }
    
    static ControlFlow[] getControlFlow(){
        ControlFlow cfs[] = new ControlFlow[2];
        cfs[0] = new IfLessSigmoidGeometric();
        cfs[1] = new Goto();
        return cfs;
    }
    static Operator[] getOperators(){
      
        Operator ops[] = new Operator[3];
        ops[0] = new Add();
        ops[1] = new Subtract();
        ops[2] = new NoOp();
        
        return ops;
    }
       
     
    
}
