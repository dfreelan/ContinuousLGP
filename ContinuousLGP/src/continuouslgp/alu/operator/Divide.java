/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package continuouslgp.alu.operator;

/**
 *
 * @author dfreelan
 */
public class Divide implements Operator {

    @Override
    public float[] doOperation(float[] registers, int src, int dest) {
        if(registers[src] != 0.0f){
            registers[dest] = registers[dest]/registers[src];
        }else{
            registers[dest] = registers[dest]/Float.MIN_NORMAL;
        }
        return registers;
    }
    
}
