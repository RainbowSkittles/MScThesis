protected LogisticFunctionBase initialize_logistic_function() throws InternalErrorException {
    if (has_delay)
        return new LogisticFunctionDelay(this.K, this.pop, this.demands, this.delays, epsilon, MC);
    if(has_multi)
        return new LogisticFunctionMSBD(this.K - 1, this.pop, this.demands, serv, epsilon, MC);
    return new LogisticFunction(this.K - 1, this.pop, this.demands, epsilon, MC);
}