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
    
    float locationOnTorus;
    public Torus(int length) {
        super(length);
    }

    @Override
    public void Initialize() {
        
        float pseudoIndex = generator.nextFloat()*(length);
        locationOnTorus = pseudoIndex;
        mutateVector = new float[1];
        applyMutation(0);//a trick to set the current weights to the current torus location
    }
    
    @Override
    public float[] mutate(float strength) {
        mutateVector[0] = generator.nextFloat()*(length);
        oldWeights = weights.clone();
        applyMutation(strength);
        return weights;
    }

    @Override
    public float[] changeStrength(float strength) {
        weights = oldWeights.clone();
        applyMutation(strength);
        return weights;
    }
    
    public void applyMutation(float strength) {
        locationOnTorus = mutateVector[0]*strength + (1-strength)*locationOnTorus;
        weights = generateWeightsFromLocation(locationOnTorus);
    }

    private float[] generateWeightsFromLocation(float location){
        float[] arr = new float[length];
        float decimalPart = location - (float)Math.floor(location);
        int integerPart = (int)location;
        arr[integerPart] = 1 - decimalPart;
        arr[(integerPart+1)%length] = decimalPart;
        return arr;
    }

    @Override
    public float[] mutate(float strength, float strength2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float[] changeStrength(float strength, float strength2) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
