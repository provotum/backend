package org.provotum.backend.ethereum.accessor;

import org.provotum.backend.config.EthereumConfiguration;
import org.provotum.backend.ethereum.config.BallotContractConfig;
import org.provotum.backend.ethereum.wrappers.Ballot;
import org.provotum.backend.socket.message.base.Status;
import org.provotum.backend.socket.message.event.ChangeEventResponse;
import org.provotum.backend.socket.message.event.VoteEventResponse;
import org.provotum.backend.socket.publisher.TopicPublisher;
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
public class BallotContractAccessor extends AContractAccessor<Ballot, BallotContractConfig> {

    private static final Logger logger = Logger.getLogger(BallotContractAccessor.class.getName());

    private Web3j web3j;
    private TopicPublisher topicPublisher;
    private EthereumConfiguration ethereumConfiguration;

    private Scheduler scheduler;

    @Autowired
    public BallotContractAccessor(Web3j web3j, EthereumConfiguration ethereumConfiguration, TopicPublisher topicPublisher) {
        this.web3j = web3j;
        this.ethereumConfiguration = ethereumConfiguration;
        this.topicPublisher = topicPublisher;

        ExecutorService executor = Executors.newCachedThreadPool();
        this.scheduler = Schedulers.from(executor);
    }

    @Override
    public Ballot deploy(BallotContractConfig config) throws Exception {
        Ballot ballot = Ballot.deploy(
            this.web3j,
            this.ethereumConfiguration.getWalletCredentials(),
            Ballot.GAS_PRICE,
            Ballot.GAS_LIMIT,
            config.getVotingQuestion(),
            config.getZeroKnowledgeContractAddress()
        ).send();

        // TODO: we might have to check that we do not get events duplicated times if we deploy multiple ballots
        this.subscribeToVoteEvent(ballot);
        this.subscribeToChangeEvent(ballot);

        return ballot;
    }

    @Override
    public Ballot load(String contractAddress) {
        return Ballot.load(
            contractAddress,
            this.web3j,
            this.ethereumConfiguration.getWalletCredentials(),
            Ballot.GAS_PRICE,
            Ballot.GAS_LIMIT
        );
    }

    private void subscribeToVoteEvent(Ballot ballot) {
        ballot.voteEventEventObservable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
            .subscribeOn(this.scheduler)
            .subscribe(new Observer<Ballot.VoteEventEventResponse>() {
                @Override
                public void onCompleted() {
                    logger.info("Stopped receiving vote events for ballot at " + ballot.getContractAddress());
                }

                @Override
                public void onError(Throwable throwable) {
                    logger.severe("Got an error while observing vote events for ballot at " + ballot.getContractAddress() + ": " + throwable.getMessage());
                    throwable.printStackTrace();
                }

                @Override
                public void onNext(Ballot.VoteEventEventResponse voteEventEventResponse) {
                    logger.info("Sending vote event response to topic '/topic/ballot/vote-event");

                    Status status = voteEventEventResponse.wasSuccessful ? Status.SUCCESS : Status.ERROR;

                    topicPublisher.send(
                        "/topic/ballot/vote-event",
                        new VoteEventResponse(status, voteEventEventResponse.reason, voteEventEventResponse._from)
                    );
                }
            });
    }

    private void subscribeToChangeEvent(Ballot ballot) {
        ballot.changeEventEventObservable(DefaultBlockParameterName.EARLIEST, DefaultBlockParameterName.LATEST)
            .subscribeOn(this.scheduler)
            .subscribe(new Observer<Ballot.ChangeEventEventResponse>() {
                @Override
                public void onCompleted() {
                    logger.info("Stopped receiving change events for ballot at " + ballot.getContractAddress());
                }

                @Override
                public void onError(Throwable throwable) {
                    logger.severe("Got an error while observing change events for ballot at " + ballot.getContractAddress() + ": " + throwable.getMessage());
                    throwable.printStackTrace();
                }

                @Override
                public void onNext(Ballot.ChangeEventEventResponse changeEventEventResponse) {
                    logger.info("Sending change event response to topic '/topic/ballot/change-event");

                    Status status = changeEventEventResponse.wasSuccessful ? Status.SUCCESS : Status.ERROR;

                    topicPublisher.send(
                        "/topic/ballot/change-event",
                        new ChangeEventResponse(status, changeEventEventResponse.reason, changeEventEventResponse._from)
                    );
                }
            });
    }
}
