public LogisticCore(double[][] demands, int[] serv, double[] delays, int[] population, double epsilon, int max_samples, MathContext mc, int nthreads) throws InternalErrorException {
    
    ...

    /* decide what the required computations are */
    if(this.sigma >0){
        /* non-zero delay */
        if(this.serv.zSum() > K) /* no support for both delay and multi-server */
            throw new InternalErrorException("Cannot support delays and multiservers! Use an N-server station instead!");
        this.has_delay = true;
    }else {
        if (this.serv.zSum() > K)
            this.has_multi = true;
        this.has_delay = false;
    }

    // Fetch the correct logistic function, and calculate stationary points and hessians
    F = this.initialize_logistic_function();
    F.calculate_stationary_point();
    F.calculate_hessian();
    //fetch the results
    this.x_stat = F.getX_stat();
    this.hess = F.getHess_stat();
    //calculate covariance
    this.cov = this.hess.copy();
    this.cov = this.A.inverse(this.cov.copy());
    // "symmetricize" the covariance
    this.cov.assign(this.A.transpose(this.cov.copy()), Functions.plus);
    this.cov.assign(Functions.div(2.));

    check_transformed_matrices();

    // initialize the Integrator stationary point and calculated covariance
    this.MCI = new MCIntegrator(
            F,
            new MultiVariateStudentT(this.x_stat.toArray(), cov.toArray(), 4),
            max_samples, MC, nthreads
    );
}