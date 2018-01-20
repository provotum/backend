package org.provotum.backend.ethereum.accessor;

import org.provotum.backend.config.EthereumConfiguration;
import org.provotum.backend.ethereum.config.ZeroKnowledgeContractConfig;
import org.provotum.backend.ethereum.wrappers.Ballot;
import org.provotum.backend.ethereum.wrappers.ZeroKnowledgeVerificator;
import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.event.ProofEventResponse;
import org.provotum.backend.socket.publisher.TopicPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameterName;
import rx.Observer;
import rx.Scheduler;
import rx.schedulers.Schedulers;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

@Component
public class ZeroKnowledgeContractAccessor extends AContractAccessor<ZeroKnowledgeVerificator, ZeroKnowledgeContractConfig> {

    private static final Logger logger = Logger.getLogger(ZeroKnowledgeContractAccessor.class.getName());

    private Web3j web3j;
    private TopicPublisher topicPublisher;
    private EthereumConfiguration ethereumConfiguration;

    private Scheduler scheduler;

    @Autowired
    public ZeroKnowledgeContractAccessor(Web3j web3j, EthereumConfiguration ethereumConfiguration, TopicPublisher topicPublisher) {
        this.web3j = web3j;
        this.ethereumConfiguration = ethereumConfiguration;
        this.topicPublisher = topicPublisher;

        ExecutorService executor = Executors.newCachedThreadPool();
        this.scheduler = Schedulers.from(executor);
    }

    @Override
    public ZeroKnowledgeVerificator deploy(ZeroKnowledgeContractConfig config) throws Exception {
        ZeroKnowledgeVerificator zkVerificator = ZeroKnowledgeVerificator.deploy(
            this.web3j,
            this.ethereumConfiguration.getWalletCredentials(),
            Ballot.GAS_PRICE,
            Ballot.GAS_LIMIT
        ).send();

        // TODO: we might have to check that we do not get events duplicated times if we deploy multiple ballots
        this.subscribeToProofEvent(zkVerificator);

        return zkVerificator;
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
    public String remove(String contractAddress) throws Exception {
        return ZeroKnowledgeVerificator.load(
            contractAddress,
            this.web3j,
            this.ethereumConfiguration.getWalletCredentials(),
            ZeroKnowledgeVerificator.GAS_PRICE,
            ZeroKnowledgeVerificator.GAS_LIMIT
        ).destroy().send().getTransactionHash();
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
