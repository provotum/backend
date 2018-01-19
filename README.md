Provotum Backend
================

# Installation
Follow the steps below for setting up your development environment.

* Init submodule: `git submodule init && git submodule update --recursive`.
* Install `web3j` for contract to Java wrapper compilation: `brew install web3j`.


# Development

## Compile wrapper for contracts

Within the project directory use the `Makefile` to compile smart 
contracts and generate their wrappers using Web3j:

* `make compile`: Will take all `*.sol` files from `eth-contracts/contracts`
   and compile them to `eth-contracts-build`.
* `make wrappers`: Will the build files and generate Java wrappers for them in `src/main/java/org.provotum/ethereum/wrappers`
* `make clean`: Will remove all compiled contracts from `eth-contracts-build`

