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
        while(i<questions.length && weights[i]!=0){
            
            dummyRegisters = pseudoRegisters.clone();
            int operator = questions[i][0];
            int src = questions[i][1];
            int dest = questions[i][2];
            //System.out.println("Operator, src, dest:" + operator +"," + src+ "," + dest +  "weight:" + weights[i]);
            //FloatMath.printFloatArr( dummyRegisters);
            
            
            dummyRegisters = operators[operator].doOperation(dummyRegisters, src, dest);
            //System.out.println("result:");
            //FloatMath.printFloatArr(dummyRegisters);
            for(int a = 0; a<cumulationRegisters.length; a++){
                if(dummyRegisters[a]!=Float.NaN && dummyRegisters[a]!=Float.NEGATIVE_INFINITY && dummyRegisters[a]!=Float.POSITIVE_INFINITY)
                 cumulationRegisters[a] += weights[i] * dummyRegisters[a];
            }
            i++;
        }
        return cumulationRegisters;
    }
}
