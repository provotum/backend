package org.provotum.backend.ethereum.accessor;

import org.web3j.tx.Contract;

public abstract class AContractAccessor<T extends Contract, C> {

    /**
     * Deploy a contract using the specified configuration.
     *
     * @param configuration A configuration specific to the contract to deploy.
     * @return The deployed contract.
     * @throws Exception If deploying the contract failed.
     */
    public abstract T deploy(C configuration) throws Exception;

    /**
     * Load the contract at the specified address.
     *
     * @param contractAddress The contract's address.
     * @return The referenced contract.
     */
    public abstract T load(String contractAddress);

    /**
     * Remove the contract from the given address.
     *
     * @param contractAddress The contract's address.
     * @return The transaction hash.
     * @throws Exception If removing the contract failed.
     */
    public abstract String remove(String contractAddress) throws Exception;
}
