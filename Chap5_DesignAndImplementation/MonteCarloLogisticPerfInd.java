    public void computePerformanceMeasures() throws InternalErrorException {
        ...
        for (int r = 0; r < qnm.R; r++) { // calculate Q_kr
            for (int k = 0; k < qnm.M; k++) {
                ...
                int[] pop_minus = pop.clone(); // compute N - 1_r
                pop_minus[r]--;
                int[] serv_plus;
                // compute model augmented with station k
                demands_plus = augmentDemandsAtServer(qnm.M, qnm.R, demands, k);
                serv_plus = augmentSeverCountAtServer(qnm.M, qnm.R, serv, k);
                // Instantiate additive LogisticCore to compute modified normalizing constant
                LogisticCore NCS_plus = new LogisticCoreAdd(demands_plus, serv_plus, delays, pop_minus, epsilon, max_samples, nthread, MC);
                // Calculate queue length
                BigDecimal NC_plus = NCS_plus.calculateNC();
                NC_plus = NC_plus.multiply(new BigDecimal(demands[k][r]));
                Q[k][r] = new BigRational(NC_plus.divide(qnm.getNormalisingConstant().asBigDecimal(), MC));
            }
        }
        qnm.setPerformanceMeasures(Q,X);// cache performance measures
        perf_timer.pause();
    }