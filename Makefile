# Note: The error
# "Makefile:9: *** missing separator.  Stop."
# indicates that the file is wrongly indented with spaces instead of tabs...

SHELL := /bin/bash

.PHONY: compile wrappers clean

all: compile wrappers

# Generates .abi and .bin files for all
# contracts located in the folder resources.
compile:
	for filename in eth-contracts/contracts/*.sol; do echo "Compiling $$filename"; solc "$$filename" --bin --abi --optimize --overwrite -o eth-contracts-build/; done;

# Based on the compiled files, we then can generate
# the Java wrappers using web3.
# Note: Generated files starting with "Abstract" are skipped since such
#       files will only hold contract interface definitions.
wrappers: compile
	for fullFile in eth-contracts/contracts/*.sol; do filename=$$(basename "$$fullFile"); filename="$${filename%.*}"; if [[ $$filename == Abstract* ]]; then continue; fi; web3j solidity generate eth-contracts-build/"$$filename".bin eth-contracts-build/"$$filename".abi -o src/main/java -p org.provotum.backend.ethereum.wrappers; done;

# Clean the compiled contract codes.
# Note, that generated Java code is not removed.
clean:
	for filename in eth-contracts-build/*.{abi,bin}; do rm "$$filename"; done;
