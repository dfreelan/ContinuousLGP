/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp;

import continuouslgp.Engine.Engine;
import continuouslgp.alu.operator.*;
import continuouslgp.machine.ContinuousMachine;
import continuouslgp.machine.RegisterProfile;
import continuouslgp.program.ContinuousProgram;

/**
 *
 * @author dfreelan
 */
public class ContinuousLGP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //public ContinuousProgram(int length, int lineLength, int srcLength, int destLength, int instrType, int srcType, int destType){

        ContinuousProgram prog = new ContinuousProgram(1000,4,7,7,0,0,0);
        // public ContinuousMachine(ContinuousProgram p, RegisterProfile profile, int maxPCs, int numRegisters, int execType){
        Engine engine = new Engine(getOperators(),null);
        RegisterProfile prof = new RegisterProfile(engine,prog.lines.length);
        ContinuousMachine machine = new ContinuousMachine(prog,prof,1,7,0);
        machine.pcs[0].registers[0] = 2.0f;
        for(int i = 0; i<1000; i++)
            machine.doStep();
        System.out.println(machine.pcs[0].registers[0]);
    }
    static Operator[] getOperators(){
        Operator ops[] = new Operator[5];
        ops[0] = new Add();
        ops[1] = new Divide();
        ops[2] = new Subtract();
        ops[3] = new Multiply();
        ops[4] = new NoOp();
        return ops;
    }
}
