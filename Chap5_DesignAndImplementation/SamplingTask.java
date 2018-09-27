private class SamplingTask implements Callable<BigDecimal>{
        ...
        //required interface method
        public BigDecimal call() throws Exception{
            return computeT();
        }

        private BigDecimal computeT() throws Exception{
            // method to perform the Monte Carlo Integration
            ...
            double[] x;
            for(int i=0; i<num_samplesT; i++) {
                // obtain a sample from our copy of the distribution and evaluate function value at sample point
                x = this.getSampleT(); 
                f = functionT.evaluate(x);
                phi = new BigDecimal(distTPrivate.pdf(x));
                f = f.divide(phi, MCT);
                I = I.add(f, MCT);

                J++;
            }
            return I.divide(new BigDecimal(J), MCT); //average to get final integral
        }

        synchronized
        private double[] getSampleT(){ return distTPrivate.getSample(); }

    }