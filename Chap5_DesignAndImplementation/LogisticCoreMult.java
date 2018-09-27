protected LogisticFunctionBase initialize_logistic_function() throws InternalErrorException {
    if (has_delay)
        return new LogisticFunctionMultDelay(this.K, this.pop, this.demands, this.delays, epsilon, MC);
    if(has_multi) // TODO: (future work) implement multiplicative multi-server logistic function
        throw new InternalErrorException("Multiserver not supported for multiplicative transform!");
    return new LogisticFunctionMult(this.K - 1, this.pop, this.demands, epsilon, MC);
}