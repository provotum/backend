Provotum Backend
================

# Endpoints
The different endpoints maintained by the Backen application are described in detail here:
* [RESTful Interface](https://github.com/provotum/specification/blob/master/specification/rest-interface/rest-interface-specification.md)
* [Websocket Interface](https://github.com/provotum/specification/blob/master/specification/websocket-interface/websocket-connection-specification.md)

# Installation
Follow the steps below for setting up your development environment.

:construction: **NOTE**: The web3j wrappers are currently only working with geth 1.7.3 and below :construction:

**Requirements**
* The solidity compiler `solc`. Obtainable using brew as described in [http://solidity.readthedocs.io/en/v0.4.21/installing-solidity.html](http://solidity.readthedocs.io/en/v0.4.21/installing-solidity.html):

```
   brew update
   brew upgrade
   brew tap ethereum/ethereum
   brew install solidity
   brew linkapps solidity
```

* Clone this repo: `git clone git@github.com:provotum/backend.git && cd backend`
* Init submodule: `git submodule init && git submodule update --recursive`.
* Install `web3j` for contract to Java wrapper compilation: `brew install web3j`.
* Adjust the application properties in `src/main/resources/application.properties`:

```bash
   # Evaluation
   evaluation.output=/tmp/evaluation
   # Ethereum and Web3J
   ethereum.web3j.rpchost=http://sealer01.provotum.ch:80
   ethereum.wallet.location=src/main/resources/wallets/local-net.json
   ethereum.wallet.password=password123
   # Encryption
   security.output.electionpublickey=src/main/resources/publickey.pub
   security.output.electionprivatekey=src/main/resources/privatekey.priv
   security.output.rsapublickey=src/main/resources/rsa-publickey.pub
   security.output.rsaprivatekey=src/main/resources/rsa-privatekey.priv
   # Spring Boot Configuration
   server.port=8080
```

* `evaluation.output`: Where the evaluation output should be stored. Contains time measurements of different operations.
* `ethereum.web3j.rpchost`: The host on which a geth node (with the RPC interface enabled) is running. MUST start with `http` resp. `https`.
* `ethereum.wallet.location`: The path to a pre-allocated wallet which is used to deploy the contracts
* `ethereum.wallet.password`: The password for the above wallet.
* `security.output.electionpublickey`: Where the election public key should be saved.
* `security.output.electionprivatekey`: Where the election private key should be saved.
* `security.output.rsapublickey`: The path to the RSA public key used for encrypting the random value parameter `R` of an ElGamal ciphertext.
* `security.output.rsaprivatekey`: The path to the RSA private key used for encrypting the random value parameter `R` of an ElGamal ciphertext.
* `server.port`: The port on which the backend should be accessible.

# Development

## Compile wrapper for contracts

Within the project directory use the `Makefile` to compile smart 
contracts and generate their wrappers using Web3j:

* `make compile`: Will take all `*.sol` files from `eth-contracts/contracts`
   and compile them to `eth-contracts-build`.
* `make wrappers`: Will the build files and generate Java wrappers for them in `src/main/java/org.provotum/ethereum/wrappers`
* `make clean`: Will remove all compiled contracts from `eth-contracts-build`

# Running the App
To run the app with the configured parameters, invoke 
```
   mvn spring-boot:run
```

# Known Issues
 **NOTE**: If it seems that the contract creations do not reach the sealer nodes, you should increase the maximum heap space of Java: 
 ```
 mvn spring-boot:run -Drun.jvmArguments="-Xmx768m" -Drun.profiles=dev
 ```
