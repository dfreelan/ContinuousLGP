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
public class Add implements Operator {

    @Override
    public float[] doOperation(float[] registers, int src, int dest) {
        registers[dest] = registers[src] + registers[dest];
        if(Float.isInfinite(registers[dest])){
            registers[dest] = 1.0f;
        }
        return registers;
    }
    
}
