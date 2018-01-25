package org.provotum.backend.ethereum.accessor;

import org.provotum.backend.communication.message.base.Status;
import org.provotum.backend.communication.socket.message.deployment.ZeroKnowledgeDeploymentResponse;
import org.provotum.backend.communication.socket.message.event.ProofEventResponse;
import org.provotum.backend.communication.message.partial.Contract;
import org.provotum.backend.communication.socket.message.removal.ZeroKnowledgeRemovalResponse;
import org.provotum.backend.communication.socket.publisher.TopicPublisher;
import org.provotum.backend.config.EthereumConfiguration;
import org.provotum.backend.ethereum.config.ZeroKnowledgeContractConfig;
import org.provotum.backend.ethereum.wrappers.Ballot;
import org.provotum.backend.ethereum.wrappers.ZeroKnowledgeVerificator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import rx.Observer;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Component
public class ZeroKnowledgeContractAccessor extends AContractAccessor<ZeroKnowledgeVerificator, ZeroKnowledgeContractConfig> {

    private static final Logger logger = Logger.getLogger(ZeroKnowledgeContractAccessor.class.getName());

    private Web3j web3j;
    private TopicPublisher topicPublisher;
    private EthereumConfiguration ethereumConfiguration;

    private ExecutorService executorService;
    private Scheduler scheduler;

    @Autowired
    public ZeroKnowledgeContractAccessor(Web3j web3j, EthereumConfiguration ethereumConfiguration, TopicPublisher topicPublisher) {
        this.web3j = web3j;
        this.ethereumConfiguration = ethereumConfiguration;
        this.topicPublisher = topicPublisher;

        ExecutorService executor = Executors.newCachedThreadPool();
        this.scheduler = Schedulers.from(executor);

        // executor for async tasks
        this.executorService = Executors.newCachedThreadPool();
    }

    @Override
    public void deploy(ZeroKnowledgeContractConfig config) {
        // asynchronously deploy the contract and notify any clients
        // over a websocket connection
        logger.info("Starting zero-knowledge contract deployment in a new thread.");

        this.executorService.submit(() -> {
            logger.info("Zero-knowledge deployment in new thread started.");

            ZeroKnowledgeDeploymentResponse response;

            try {
                ZeroKnowledgeVerificator zkVerificator = ZeroKnowledgeVerificator.deploy(
                    this.web3j,
                    this.ethereumConfiguration.getWalletCredentials(),
                    Ballot.GAS_PRICE,
                    Ballot.GAS_LIMIT
                ).send();

                // TODO: we might have to check that we do not get events duplicated times if we deploy multiple zkVerificators
                this.subscribeToProofEvent(zkVerificator);

                logger.info("Zero-knowledge deployment was successful. Contract address is: " + zkVerificator.getContractAddress());
                response = new ZeroKnowledgeDeploymentResponse(Status.SUCCESS, "Deployment successful", new Contract("zero-knowledge", zkVerificator.getContractAddress()));
            } catch (Exception e) {
                logger.severe("Failed to deploy zero-knowledge verificator: " + e.getMessage());
                e.printStackTrace();

                response = new ZeroKnowledgeDeploymentResponse(Status.ERROR, "Deployment failed: " + e.getMessage(), new Contract("zero-knowledge", null));
            }

            logger.info("Sending zero-knowledge deployment response to subscribers at topic " + TopicPublisher.DEPLOYMENT_TOPIC);
            // notify any subscribers about the new ballot
            this.topicPublisher.send(
                TopicPublisher.DEPLOYMENT_TOPIC,
                response
            );
        });

    }

    @Override
    public ZeroKnowledgeVerificator load(String contractAddress) {
        return ZeroKnowledgeVerificator.load(
            contractAddress,
            this.web3j,
            this.ethereumConfiguration.getWalletCredentials(),
            Ballot.GAS_PRICE,
            Ballot.GAS_LIMIT
        );
    }

    @Override
    public void remove(String contractAddress) {
        logger.info("Starting zero-knowledge contract removal in new thread.");

        // starting execution in a new thread to avoid blocking.
        this.executorService.submit(() -> {
            logger.info("Zero-knowledge contract removal in new thread started.");

            ZeroKnowledgeRemovalResponse response;

            try {
                String trx = ZeroKnowledgeVerificator.load(
                    contractAddress,
                    this.web3j,
                    this.ethereumConfiguration.getWalletCredentials(),
                    ZeroKnowledgeVerificator.GAS_PRICE,
                    ZeroKnowledgeVerificator.GAS_LIMIT
                ).destroy().send().getTransactionHash();

                logger.info("Zero-knowledge contract removed. Transaction hash is: " + trx);
                response = new ZeroKnowledgeRemovalResponse(Status.SUCCESS, "Successfully removed zero-knowledge contract.", trx);
            } catch (Exception e) {
                logger.severe("Failed to remove zero-knowledge contract: " + e.getMessage());
                e.printStackTrace();

                response = new ZeroKnowledgeRemovalResponse(Status.ERROR, "Failed to remove zero-knowledge contract at " + contractAddress + ": " + e.getMessage(), null);
            }

            logger.info("Sending zero-knowledge removal response to subscribers at topic " + TopicPublisher.REMOVAL_TOPIC);
            this.topicPublisher.send(
                TopicPublisher.REMOVAL_TOPIC,
                response
            );
        });

    }

    private void subscribeToProofEvent(ZeroKnowledgeVerificator zkVerificator) {
        zkVerificator.proofEventEventObservable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
            .subscribeOn(this.scheduler)
            .subscribe(new Observer<ZeroKnowledgeVerificator.ProofEventEventResponse>() {
                @Override
                public void onCompleted() {
                    logger.info("Stopped receiving proof events for ballot at " + zkVerificator.getContractAddress());
                }

                @Override
                public void onError(Throwable throwable) {
                    logger.severe("Got an error while observing proof events for ballot at " + zkVerificator.getContractAddress() + ": " + throwable.getMessage());
                    throwable.printStackTrace();
                }

                @Override
                public void onNext(ZeroKnowledgeVerificator.ProofEventEventResponse changeEventEventResponse) {
                    logger.info("Sending proof event response to topic '/topic/zero-knowledge/proof-event");

                    Status status = changeEventEventResponse.wasSuccessful ? Status.SUCCESS : Status.ERROR;

                    topicPublisher.send(
                        "/topic/zero-knowledge/proof-event",
                        new ProofEventResponse(status, changeEventEventResponse.reason, changeEventEventResponse._from)
                    );
                }
            });
    }
}
