/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program;

/**
 *
 * @author dfreelan
 */
public class ContinuousProgram {
    ContinuousLine[] lines = null;
    int length;
    int lineLength;
    public ContinuousProgram(int length, int lineLength, int srcLength, int destLength, int instrType, int srcType, int destType){
        lines = new ContinuousLine[length];
        this.length = length;
        this.lineLength = lineLength;
        for(int i = 0; i<lines.length; i++){
            lines[i] = new ContinuousLine(instrType, srcType, destType, lineLength, srcLength, destLength);
        }
    }
    public void mutate(float strength){
        for(int i = 0; i<lines.length; i++){
            lines[i].mutate(strength);
        }
    }
    public void changeStrength(float strength){
        for(int i = 0; i<lines.length; i++){
            lines[i].changeStrength(strength);
        }
    }
    public float[][] getLineValues(int line){
       return lines[line].getLineValues();
       
    }
}
