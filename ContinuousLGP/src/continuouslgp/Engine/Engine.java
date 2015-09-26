/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.Engine;

import continuouslgp.alu.controlflow.ControlFlow;
import continuouslgp.alu.operator.Operator;

/**
 *
 * @author dfreelan
 */
public class Engine {
    Operator operators[];
    ControlFlow controlFlow[];
    
    public Engine(Operator[] operators, ControlFlow[] controlFlow){
        this.operators = operators;
        this.controlFlow = controlFlow;
       
    }
    public float[] getResult(int[][] questions, float[] pseudoRegisters, float[] weights){
        float[] cumulationRegisters = new float[pseudoRegisters.length];
        float[] dummyRegisters;
        int i = 0;
        while(i<questions.length && questions[i]!=null){
            
            dummyRegisters = pseudoRegisters.clone();
            dummyRegisters = operators[questions[i][0]].doOperation(dummyRegisters, questions[i][1], questions[i][2]);
           
            for(int a = 0; a<cumulationRegisters.length; a++){
               // System.out.println("Operator:" + questions[i][0] + " weight:" + weights[i]);
                //FloatMath.printFloatArr( dummyRegisters);
                int k = 0;
                int b = 5/k;
                cumulationRegisters[a] += weights[i] * dummyRegisters[a];
            }
            i++;
        }
        return cumulationRegisters;
    }
}
