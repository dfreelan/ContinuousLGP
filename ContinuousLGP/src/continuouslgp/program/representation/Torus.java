/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package continuouslgp.program.representation;

/**
 *
 * @author dfreelan
 */
public class Torus extends WeightVector {

    public Torus(int length) {
        super(length);
    }

    @Override
    public void Initialize() {
        float pseudoIndex = generator.nextFloat()*(length+1);
        float decimalPart = pseudoIndex - (float)Math.floor(pseudoIndex);
        int integerPart = (int)pseudoIndex;
        weights[integerPart] = 1 - decimalPart;
        weights[(integerPart+1)%length] = decimalPart;
        mutateVector = new float[1];
    }
    
    @Override
    public float[] mutate(float strength) {
        mutateVector[0] = generator.nextFloat()*(length+1);
        oldWeights = weights.clone();
        applyMutation(strength);
        return weights;
    }

    @Override
    public float[] changeStrength(float strength) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float[] getWeights() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setWeights(float[] weights) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
}
